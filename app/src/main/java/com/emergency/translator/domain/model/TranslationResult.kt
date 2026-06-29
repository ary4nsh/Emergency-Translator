package com.emergency.translator.domain.model

data class TranslationResult(
    val originalText: String,
    val translatedText: String,
    val detectedSourceLanguage: String,
    val targetLanguage: Language,
    val phraseId: Long = 0   // non-zero when result comes from a known emergency phrase
)
