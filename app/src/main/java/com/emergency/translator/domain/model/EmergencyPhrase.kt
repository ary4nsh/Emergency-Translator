package com.emergency.translator.domain.model

data class EmergencyPhrase(
    val id: Long = 0,
    val englishText: String,
    val arabicText: String,
    val persianText: String,
    val turkishText: String,
    val russianText: String,
    val ukrainianText: String,
    val category: PhraseCategory,
    val isFavorite: Boolean = false
) {
    fun getTextForLanguage(language: Language): String = when (language) {
        Language.ENGLISH   -> englishText
        Language.ARABIC    -> arabicText
        Language.PERSIAN   -> persianText
        Language.TURKISH   -> turkishText
        Language.RUSSIAN   -> russianText
        Language.UKRAINIAN -> ukrainianText
    }

    fun matchesSearch(query: String): Boolean {
        val q = query.trim()
        if (q.isEmpty()) return true
        return englishText.contains(q, ignoreCase = true)
            || arabicText.contains(q)
            || persianText.contains(q)
            || turkishText.contains(q, ignoreCase = true)
            || russianText.contains(q, ignoreCase = true)
            || ukrainianText.contains(q, ignoreCase = true)
    }
}

enum class PhraseCategory(val displayName: String) {
    MEDICAL("Medical"),
    FOOD_WATER("Food & Water"),
    SHELTER("Shelter"),
    FAMILY("Family"),
    SAFETY("Safety"),
    TRANSPORTATION("Transportation"),
    COMMUNICATION("Communication")
}
