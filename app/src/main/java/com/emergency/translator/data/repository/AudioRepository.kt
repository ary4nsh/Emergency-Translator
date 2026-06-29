package com.emergency.translator.data.repository

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.emergency.translator.data.local.database.AudioCacheDao
import com.emergency.translator.data.local.database.EmergencyPhraseDao
import com.emergency.translator.data.local.entity.AudioCacheEntity
import com.emergency.translator.domain.model.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * Manages an offline audio database for emergency phrases.
 *
 * On first run, synthesizes every phrase in every language to WAV files in the
 * app's private storage using the device's TTS engine, then records each file's
 * path in the audio_cache Room table. Subsequent playbacks read from those files
 * directly without live TTS.
 */
@Singleton
class AudioRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioCacheDao: AudioCacheDao,
    private val phraseDao: EmergencyPhraseDao
) {
    private val audioDir = File(context.filesDir, "audio").also { it.mkdirs() }
    private var mediaPlayer: MediaPlayer? = null

    // ─── Public API ───────────────────────────────────────────────────────────

    /** Called at app start to pre-generate the audio database in the background. */
    suspend fun generateAudioCache() = withContext(Dispatchers.IO) {
        if (audioCacheDao.getCount() > 0) return@withContext
        val phrases = phraseDao.getAllPhrasesOnce()
        val tts = initTts() ?: return@withContext
        try {
            for (language in Language.values()) {
                val locale = language.toLocale()
                val supported = tts.setLanguage(locale).let {
                    it != TextToSpeech.LANG_MISSING_DATA && it != TextToSpeech.LANG_NOT_SUPPORTED
                }
                for (phrase in phrases) {
                    val text = if (supported) phrase.getTextForLanguage(language) else phrase.englishText
                    val file = File(audioDir, "phrase_${phrase.id}_${language.code}.wav")
                    if (!file.exists()) {
                        if (!supported) tts.setLanguage(Locale.ENGLISH)
                        synthesizeToFile(tts, text, file)
                    }
                    if (file.exists()) {
                        audioCacheDao.insert(
                            AudioCacheEntity(
                                phraseId = phrase.id,
                                languageCode = language.code,
                                filePath = file.absolutePath
                            )
                        )
                    }
                }
            }
        } finally {
            tts.shutdown()
        }
    }

    /** Returns the cached WAV file for a phrase+language, or null if not yet generated. */
    suspend fun getCachedFile(phraseId: Long, language: Language): File? {
        val entry = audioCacheDao.getAudioEntry(phraseId, language.code) ?: return null
        val file = File(entry.filePath)
        return if (file.exists()) file else null
    }

    /** Plays a WAV file via MediaPlayer. Call from the main thread. */
    fun playFile(file: File) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            prepare()
            start()
            setOnCompletionListener { release(); mediaPlayer = null }
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // ─── Internals ────────────────────────────────────────────────────────────

    private suspend fun initTts(): TextToSpeech? = suspendCancellableCoroutine { cont ->
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            if (!cont.isCompleted) {
                cont.resume(if (status == TextToSpeech.SUCCESS) engine else null)
            }
        }
        cont.invokeOnCancellation { engine?.shutdown() }
    }

    private suspend fun synthesizeToFile(tts: TextToSpeech, text: String, file: File): Boolean =
        suspendCancellableCoroutine { cont ->
            val uid = "synth_${file.name}"
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(id: String?) {}
                override fun onDone(id: String?) {
                    if (!cont.isCompleted) cont.resume(true)
                }
                @Suppress("DEPRECATION")
                override fun onError(id: String?) {
                    if (!cont.isCompleted) cont.resume(false)
                }
                override fun onError(id: String?, errorCode: Int) {
                    if (!cont.isCompleted) cont.resume(false)
                }
            })
            val result = tts.synthesizeToFile(text, null, file, uid)
            if (result != TextToSpeech.SUCCESS && !cont.isCompleted) {
                cont.resume(false)
            }
        }

    private fun Language.toLocale(): Locale = when (this) {
        Language.ARABIC    -> Locale("ar")
        Language.PERSIAN   -> Locale("fa")
        Language.TURKISH   -> Locale("tr")
        Language.RUSSIAN   -> Locale("ru")
        Language.UKRAINIAN -> Locale("uk")
        else               -> Locale.ENGLISH
    }
}
