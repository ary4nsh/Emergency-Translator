package com.emergency.translator.data.translation

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Wraps the Google ML Kit Translation and Language Identification APIs.
 *
 * On first use for a given language pair the ML Kit model (~30 MB) is downloaded
 * automatically. After that, all translation is fully offline.
 * Translators are cached to avoid repeated initialisation for the same pair.
 */
@Singleton
class MLKitTranslationSource @Inject constructor() {

    private val languageIdentifier = LanguageIdentification.getClient()
    private val translatorCache = mutableMapOf<String, Translator>()

    /**
     * Identifies the language of [text].
     * Returns a BCP-47 code (e.g. "en", "ar") or "en" if detection fails or is uncertain.
     */
    suspend fun detectLanguage(text: String): String = try {
        val code = languageIdentifier.identifyLanguage(text).await()
        if (code == "und") "en" else code
    } catch (e: Exception) {
        Log.w(TAG, "Language detection failed: ${e.message}")
        "en"
    }

    /**
     * Translates [text] from [sourceLang] to [targetLang] (BCP-47 codes).
     * Downloads the model pair if not already cached on device.
     * Throws an exception if the download fails (no internet) and the model
     * is not already present — callers should handle this for an offline fallback.
     */
    suspend fun translate(text: String, sourceLang: String, targetLang: String): String {
        if (sourceLang == targetLang || text.isBlank()) return text
        val translator = getOrCreate(sourceLang, targetLang)
        translator.downloadModelIfNeeded().await()
        return translator.translate(text).await()
    }

    fun close() {
        translatorCache.values.forEach { runCatching { it.close() } }
        translatorCache.clear()
        languageIdentifier.close()
    }

    private fun getOrCreate(source: String, target: String): Translator =
        translatorCache.getOrPut("${source}_$target") {
            Translation.getClient(
                TranslatorOptions.Builder()
                    .setSourceLanguage(source)
                    .setTargetLanguage(target)
                    .build()
            )
        }

    private companion object {
        const val TAG = "MLKitTranslation"
    }
}

// Coroutine bridge for Google Play Services Task<T>
private suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { cont ->
    addOnSuccessListener { result -> if (!cont.isCompleted) cont.resume(result) }
    addOnFailureListener { e -> if (!cont.isCompleted) cont.resumeWithException(e) }
}
