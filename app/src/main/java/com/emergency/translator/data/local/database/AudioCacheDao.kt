package com.emergency.translator.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emergency.translator.data.local.entity.AudioCacheEntity

@Dao
interface AudioCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: AudioCacheEntity)

    @Query("SELECT * FROM audio_cache WHERE phraseId = :phraseId AND languageCode = :languageCode LIMIT 1")
    suspend fun getAudioEntry(phraseId: Long, languageCode: String): AudioCacheEntity?

    @Query("SELECT COUNT(*) FROM audio_cache")
    suspend fun getCount(): Int
}
