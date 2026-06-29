package com.emergency.translator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emergency.translator.domain.model.Language

@Entity(tableName = "translation_entries")
data class TranslationEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val englishWord: String,
    val arabicWord: String,
    val persianWord: String,
    val turkishWord: String,
    val russianWord: String,
    val ukrainianWord: String,
    val category: String
) {
    fun getTranslation(language: Language): String = when (language) {
        Language.ENGLISH -> englishWord
        Language.ARABIC -> arabicWord
        Language.PERSIAN -> persianWord
        Language.TURKISH -> turkishWord
        Language.RUSSIAN -> russianWord
        Language.UKRAINIAN -> ukrainianWord
    }
}
