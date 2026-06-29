package com.emergency.translator

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.emergency.translator.data.repository.AudioRepository
import com.emergency.translator.data.repository.PhraseRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class EmergencyTranslatorApp : Application() {

    @Inject lateinit var phraseRepository: PhraseRepository
    @Inject lateinit var audioRepository: AudioRepository

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        // Disable system night-mode override so ThemeManager's explicit setTheme() wins.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
        appScope.launch {
            phraseRepository.initializeDefaultPhrases()
            // Pre-generate audio files for all phrases in all languages.
            // Runs only once; skipped on subsequent launches when cache exists.
            audioRepository.generateAudioCache()
        }
    }
}
