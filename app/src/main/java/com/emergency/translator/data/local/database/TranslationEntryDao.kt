package com.emergency.translator.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emergency.translator.data.local.entity.TranslationEntryEntity

@Dao
interface TranslationEntryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entries: List<TranslationEntryEntity>)

    @Query("SELECT COUNT(*) FROM translation_entries")
    suspend fun getCount(): Int

    @Query("SELECT * FROM translation_entries")
    suspend fun getAllEntries(): List<TranslationEntryEntity>
}
