package com.emergency.translator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emergency.translator.domain.model.Language

@Entity(tableName = "emergency_phrases")
data class EmergencyPhraseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val englishText: String,
    val arabicText: String,
    val persianText: String,
    val turkishText: String,
    val russianText: String,
    val ukrainianText: String,
    val category: String,
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
}
