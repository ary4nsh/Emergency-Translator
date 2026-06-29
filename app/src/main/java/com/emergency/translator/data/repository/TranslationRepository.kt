package com.emergency.translator.data.repository

import android.util.Log
import com.emergency.translator.data.local.database.EmergencyPhraseDao
import com.emergency.translator.data.local.database.TranslationEntryDao
import com.emergency.translator.data.local.entity.EmergencyPhraseEntity
import com.emergency.translator.data.local.entity.TranslationEntryEntity
import com.emergency.translator.data.translation.MLKitTranslationSource
import com.emergency.translator.domain.model.Language
import com.emergency.translator.domain.model.TranslationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationRepository @Inject constructor(
    private val mlKit: MLKitTranslationSource,
    private val phraseDao: EmergencyPhraseDao,
    private val entryDao: TranslationEntryDao
) {

    suspend fun translate(
        text: String,
        targetLanguage: Language,
        sourceLanguage: Language? = null
    ): Result<TranslationResult> = runCatching {
        // ── Primary: ML Kit (real sentence translation, downloads model on first use) ──
        try {
            val detectedCode = sourceLanguage?.code ?: mlKit.detectLanguage(text)
            val translated = mlKit.translate(text, detectedCode, targetLanguage.code)
            return@runCatching TranslationResult(
                originalText = text,
                translatedText = translated,
                detectedSourceLanguage = Language.fromCode(detectedCode).displayName,
                targetLanguage = targetLanguage
            )
        } catch (e: Exception) {
            Log.d(TAG, "ML Kit unavailable (${e.message}), using offline fallback")
        }

        // ── Fallback: local phrase + word-dictionary matching (always offline) ──
        offlineFallback(text, targetLanguage)
    }

    // ─── Offline fallback ─────────────────────────────────────────────────────

    private suspend fun offlineFallback(text: String, targetLanguage: Language): TranslationResult {
        val phrases = phraseDao.getAllPhrasesOnce()
        val entries = entryDao.getAllEntries()
        val qWords = normalize(text).split(" ").filter { it.length >= 2 }

        val (bestPhrase, detectedLang, phraseScore) = findBestPhrase(text, phrases)
        val wordTranslations = findWordTranslations(qWords, entries, targetLanguage)

        return when {
            bestPhrase != null && phraseScore >= 0.4f ->
                TranslationResult(
                    originalText = text,
                    translatedText = bestPhrase.getTextForLanguage(targetLanguage),
                    detectedSourceLanguage = detectedLang,
                    targetLanguage = targetLanguage,
                    phraseId = bestPhrase.id
                )
            wordTranslations.isNotEmpty() ->
                TranslationResult(
                    originalText = text,
                    translatedText = wordTranslations.joinToString("  "),
                    detectedSourceLanguage = "Unknown",
                    targetLanguage = targetLanguage
                )
            bestPhrase != null && phraseScore > 0f ->
                TranslationResult(
                    originalText = text,
                    translatedText = bestPhrase.getTextForLanguage(targetLanguage),
                    detectedSourceLanguage = detectedLang,
                    targetLanguage = targetLanguage,
                    phraseId = bestPhrase.id
                )
            else -> throw Exception(
                "No translation found. Connect to the internet to download translation models, or try emergency keywords: help, water, food, hospital."
            )
        }
    }

    // ─── Phrase matching (same queryCoverage algorithm as before) ─────────────

    private data class PhraseMatch(val phrase: EmergencyPhraseEntity?, val lang: String, val score: Float)

    private fun findBestPhrase(input: String, phrases: List<EmergencyPhraseEntity>): Triple<EmergencyPhraseEntity?, String, Float> {
        val qNorm = normalize(input)
        if (qNorm.isEmpty()) return Triple(null, "Unknown", 0f)

        var best = PhraseMatch(null, "Unknown", 0f)
        for (phrase in phrases) {
            listOf(
                Language.ENGLISH.displayName  to phrase.englishText,
                Language.ARABIC.displayName   to phrase.arabicText,
                Language.PERSIAN.displayName  to phrase.persianText,
                Language.TURKISH.displayName  to phrase.turkishText,
                Language.RUSSIAN.displayName  to phrase.russianText,
                Language.UKRAINIAN.displayName to phrase.ukrainianText
            ).forEach { (lang, txt) ->
                val s = queryCoverage(qNorm, normalize(txt))
                if (s > best.score) best = PhraseMatch(phrase, lang, s)
            }
        }
        return Triple(best.phrase, best.lang, best.score)
    }

    // ─── Word dictionary lookup ───────────────────────────────────────────────

    private fun findWordTranslations(
        queryWords: List<String>,
        entries: List<TranslationEntryEntity>,
        targetLanguage: Language
    ): List<String> = queryWords.mapNotNull { word ->
        entries.firstOrNull { entry ->
            normalize(entry.englishWord) == word || normalize(entry.arabicWord) == word ||
            normalize(entry.persianWord) == word || normalize(entry.turkishWord) == word ||
            normalize(entry.russianWord) == word || normalize(entry.ukrainianWord) == word
        }?.getTranslation(targetLanguage)
    }

    // ─── Utilities ────────────────────────────────────────────────────────────

    private fun queryCoverage(q: String, p: String): Float {
        if (q == p) return 1f
        val qw = q.split(" ").filter { it.isNotEmpty() }
        if (qw.isEmpty()) return 0f
        val pw = p.split(" ").filter { it.isNotEmpty() }.toSet()
        return qw.count { it in pw }.toFloat() / qw.size
    }

    private fun normalize(text: String) =
        text.lowercase().replace(Regex("[^\\p{L}\\p{N}\\s]"), " ").replace(Regex("\\s+"), " ").trim()

    private companion object { const val TAG = "TranslationRepo" }
}
