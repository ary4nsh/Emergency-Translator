package com.emergency.translator.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emergency.translator.data.local.entity.EmergencyPhraseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyPhraseDao {

    @Query("SELECT * FROM emergency_phrases ORDER BY category, id")
    fun getAllPhrases(): Flow<List<EmergencyPhraseEntity>>

    @Query("SELECT * FROM emergency_phrases ORDER BY category, id")
    suspend fun getAllPhrasesOnce(): List<EmergencyPhraseEntity>

    @Query("SELECT * FROM emergency_phrases WHERE category = :category ORDER BY id")
    fun getPhrasesByCategory(category: String): Flow<List<EmergencyPhraseEntity>>

    @Query("SELECT * FROM emergency_phrases WHERE isFavorite = 1 ORDER BY category, id")
    fun getFavorites(): Flow<List<EmergencyPhraseEntity>>

    // Multi-language partial match; SQLite LOWER() handles ASCII case-folding
    @Query("""SELECT * FROM emergency_phrases
              WHERE LOWER(englishText)   LIKE '%' || LOWER(:query) || '%'
                 OR arabicText           LIKE '%' || :query || '%'
                 OR persianText          LIKE '%' || :query || '%'
                 OR LOWER(turkishText)   LIKE '%' || LOWER(:query) || '%'
                 OR LOWER(russianText)   LIKE '%' || LOWER(:query) || '%'
                 OR LOWER(ukrainianText) LIKE '%' || LOWER(:query) || '%'
              ORDER BY category, id""")
    fun searchPhrases(query: String): Flow<List<EmergencyPhraseEntity>>

    @Query("SELECT * FROM emergency_phrases WHERE englishText = :text LIMIT 1")
    suspend fun findByEnglishText(text: String): EmergencyPhraseEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(phrases: List<EmergencyPhraseEntity>)

    @Query("UPDATE emergency_phrases SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM emergency_phrases")
    suspend fun getCount(): Int
}
