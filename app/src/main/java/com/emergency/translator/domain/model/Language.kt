package com.emergency.translator.domain.model

enum class Language(
    val code: String,
    val displayName: String,
    val nativeName: String,
    val isRtl: Boolean = false
) {
    ENGLISH("en", "English", "English"),
    ARABIC("ar", "Arabic", "العربية", isRtl = true),
    PERSIAN("fa", "Persian", "فارسی", isRtl = true),
    TURKISH("tr", "Turkish", "Türkçe"),
    RUSSIAN("ru", "Russian", "Русский"),
    UKRAINIAN("uk", "Ukrainian", "Українська");

    companion object {
        fun fromCode(code: String): Language =
            values().firstOrNull { it.code.equals(code, ignoreCase = true) } ?: ENGLISH
    }
}
