package com.emergency.translator.di

import android.content.Context
import com.emergency.translator.data.local.database.AppDatabase
import com.emergency.translator.data.local.database.AudioCacheDao
import com.emergency.translator.data.local.database.EmergencyPhraseDao
import com.emergency.translator.data.local.database.TranslationEntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.create(context)

    @Provides
    fun provideEmergencyPhraseDao(database: AppDatabase): EmergencyPhraseDao =
        database.emergencyPhraseDao()

    @Provides
    fun provideTranslationEntryDao(database: AppDatabase): TranslationEntryDao =
        database.translationEntryDao()

    @Provides
    fun provideAudioCacheDao(database: AppDatabase): AudioCacheDao =
        database.audioCacheDao()
}
