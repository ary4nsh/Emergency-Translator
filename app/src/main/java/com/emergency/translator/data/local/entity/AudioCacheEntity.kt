package com.emergency.translator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_cache")
data class AudioCacheEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phraseId: Long,
    val languageCode: String,
    val filePath: String
)
