package com.emergency.translator.ui.common

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import com.emergency.translator.domain.model.Language
import java.util.Locale

/**
 * Wrapper around Android [TextToSpeech] that handles:
 *  - Async initialization (queues pending speech until engine is ready)
 *  - Offline voice selection via the Voices API (API 21+)
 *  - Fallback chain: primary locale → alternate locale → English
 *  - Returns [SpeakOutcome] so callers can show install prompts
 */
class TtsManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var ready = false
    private var pending: (() -> Unit)? = null

    fun initialize() {
        tts = TextToSpeech(context) { status ->
            ready = (status == TextToSpeech.SUCCESS)
            if (ready) {
                Log.d(TAG, "TTS engine ready")
                pending?.invoke()
                pending = null
            } else {
                Log.e(TAG, "TTS engine failed to initialize, status=$status")
            }
        }
    }

    /**
     * Speak [text] in [language]. If the engine is not yet ready, the call is queued.
     *
     * Fallback chain:
     * 1. Try an offline [Voice] matching the language (Voices API).
     * 2. Try [Language.primaryLocale].
     * 3. Try [Language.altLocale].
     * 4. Fall back to English and speak [englishFallback].
     */
    fun speak(
        text: String,
        language: Language,
        englishFallback: String = text
    ): SpeakOutcome {
        if (text.isBlank()) return SpeakOutcome.SILENT

        return if (ready) {
            doSpeak(text, language, englishFallback)
        } else {
            pending = { doSpeak(text, language, englishFallback) }
            SpeakOutcome.QUEUED
        }
    }

    private fun doSpeak(text: String, language: Language, fallback: String): SpeakOutcome {
        val engine = tts ?: return SpeakOutcome.SILENT

        // ── Step 1: look for an offline voice for this language ───────────────
        val offlineVoice: Voice? = engine.voices
            ?.filter { v ->
                v.locale.language == language.bcp47() && !v.isNetworkConnectionRequired
            }
            ?.maxByOrNull { v -> v.quality }

        if (offlineVoice != null) {
            engine.voice = offlineVoice
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, UID)
            Log.d(TAG, "Speaking via offline voice: ${offlineVoice.name}")
            return SpeakOutcome.SPEAKING
        }

        // ── Step 2: try setLanguage with primary locale ───────────────────────
        var langResult = engine.setLanguage(language.primaryLocale())

        // ── Step 3: try alternate locale if primary failed ────────────────────
        if (langResult < TextToSpeech.LANG_AVAILABLE) {
            val alt = language.altLocale()
            if (alt != null) {
                langResult = engine.setLanguage(alt)
                Log.d(TAG, "Primary locale failed, trying alt locale for ${language.displayName}: $langResult")
            }
        }

        return when {
            langResult >= TextToSpeech.LANG_AVAILABLE -> {
                engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, UID)
                Log.d(TAG, "Speaking via setLanguage for ${language.displayName}")
                SpeakOutcome.SPEAKING
            }
            langResult == TextToSpeech.LANG_MISSING_DATA -> {
                // Speak English fallback so the user gets some audio
                engine.setLanguage(Locale.ENGLISH)
                engine.speak(fallback, TextToSpeech.QUEUE_FLUSH, null, UID)
                Log.w(TAG, "Language data missing for ${language.displayName}, speaking English fallback")
                SpeakOutcome.MISSING_DATA
            }
            else -> {
                engine.setLanguage(Locale.ENGLISH)
                engine.speak(fallback, TextToSpeech.QUEUE_FLUSH, null, UID)
                Log.w(TAG, "Language not supported: ${language.displayName}, speaking English fallback")
                SpeakOutcome.NOT_SUPPORTED
            }
        }
    }

    /** Intent that opens the system TTS data installation screen. */
    fun installDataIntent(): Intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)

    fun shutdown() {
        pending = null
        tts?.stop()
        tts?.shutdown()
        tts = null
        ready = false
    }

    enum class SpeakOutcome {
        /** Audio is playing. */
        SPEAKING,
        /** TTS not ready yet; call was queued and will play when engine initialises. */
        QUEUED,
        /** Language data not installed on device; English fallback spoken instead. */
        MISSING_DATA,
        /** Language not supported by the TTS engine at all; English fallback spoken. */
        NOT_SUPPORTED,
        /** [text] was blank; nothing to speak. */
        SILENT
    }

    private companion object {
        const val TAG = "TtsManager"
        const val UID = "ET_TTS"
    }
}

// ── Locale helpers ────────────────────────────────────────────────────────────

fun Language.bcp47(): String = when (this) {
    Language.ARABIC    -> "ar"
    Language.PERSIAN   -> "fa"
    Language.TURKISH   -> "tr"
    Language.RUSSIAN   -> "ru"
    Language.UKRAINIAN -> "uk"
    Language.ENGLISH   -> "en"
}

fun Language.primaryLocale(): Locale = when (this) {
    Language.ARABIC    -> Locale("ar", "SA")
    Language.PERSIAN   -> Locale("fa", "IR")
    Language.TURKISH   -> Locale("tr", "TR")
    Language.RUSSIAN   -> Locale("ru", "RU")
    Language.UKRAINIAN -> Locale("uk", "UA")
    Language.ENGLISH   -> Locale.ENGLISH
}

fun Language.altLocale(): Locale? = when (this) {
    Language.ARABIC    -> Locale("ar")
    Language.PERSIAN   -> Locale("fa")
    Language.TURKISH   -> Locale("tr")
    Language.RUSSIAN   -> Locale("ru")
    Language.UKRAINIAN -> Locale("uk")
    Language.ENGLISH   -> null
}
