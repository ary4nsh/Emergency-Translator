package com.emergency.translator.data.repository

import com.emergency.translator.data.local.database.AppDatabase
import com.emergency.translator.data.local.database.TranslationEntryDao
import com.emergency.translator.data.local.entity.EmergencyPhraseEntity
import com.emergency.translator.domain.model.EmergencyPhrase
import com.emergency.translator.domain.model.PhraseCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhraseRepository @Inject constructor(
    private val database: AppDatabase,
    private val translationEntryDao: TranslationEntryDao
) {

    val allPhrases: Flow<List<EmergencyPhrase>> = database.emergencyPhraseDao()
        .getAllPhrases()
        .map { it.map { e -> e.toDomain() } }

    suspend fun initializeDefaultPhrases() {
        val dao = database.emergencyPhraseDao()
        if (dao.getCount() == 0) dao.insertAll(AppDatabase.getDefaultPhrases())
        if (translationEntryDao.getCount() == 0) translationEntryDao.insertAll(AppDatabase.getDefaultDictionary())
    }

    suspend fun findPhraseByEnglish(text: String): EmergencyPhrase? =
        database.emergencyPhraseDao().findByEnglishText(text)?.toDomain()

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        database.emergencyPhraseDao().updateFavorite(id, isFavorite)
    }

    private fun EmergencyPhraseEntity.toDomain() = EmergencyPhrase(
        id = id,
        englishText = englishText,
        arabicText = arabicText,
        persianText = persianText,
        turkishText = turkishText,
        russianText = russianText,
        ukrainianText = ukrainianText,
        category = runCatching { PhraseCategory.valueOf(category) }.getOrDefault(PhraseCategory.SAFETY),
        isFavorite = isFavorite
    )
}
