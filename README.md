# Emergency Translator

An Android application for offline emergency communication across language barriers. Designed for use by displaced persons, refugees, and disaster victims who need to communicate with emergency responders, medical personnel, and aid workers when no internet connection is available.

---

## Overview

Emergency Translator provides a curated library of 155 emergency phrases across 7 life-critical categories, translated into 6 languages. All core functionality operates without an internet connection after initial setup. The app targets common humanitarian scenarios where language barriers create life-threatening delays in emergency response.

**Version:** 1.8 (versionCode 7)  
**Platform:** Android 9+ (API 28+)  
**Language:** Kotlin  

---

## Supported Languages

- English
- Arabic
- Persian (Farsi)
- Turkish
- Russian
- Ukrainian

---

## Features

| Feature | Status |
|---------|--------|
| Offline phrase library (155 phrases, 7 categories) | ✅ Complete |
| Phrase search across all 6 languages simultaneously | ✅ Complete |
| Category filtering (Medical, Food & Water, Shelter, Family, Safety, Transportation, Communication) | ✅ Complete |
| Favourites system | ✅ Complete |
| Quick emergency action buttons (home screen) | ✅ Complete |
| ML Kit sentence translation (offline after first download) | ✅ Complete |
| Word-level dictionary fallback (70+ entries) | ✅ Complete |
| Language auto-detection | ✅ Complete |
| Theme system (6 themes: 3 accent colours x light/dark) | ✅ Complete |
| Dark mode with navigation bar theming | ✅ Complete |
| State preservation across screen rotation | ✅ Complete |
| Text-to-speech playback | ⏳ In Progress |
| TTS language fallback chain | ⏳ In Progress |
| Pre-generated audio cache | ⏳ In Progress |
| Speech-to-text input | ⏳ In Progress |

> Voice features (text-to-speech and speech-to-text) are present in the codebase but are not fully implemented. TTS behaviour varies significantly across device manufacturers and language pack availability. STT is unavailable on emulators and unreliable in noisy environments. These features are under active development.

---

## Phrase Library

Phrases cover 7 categories:

| Category | Phrase Count |
|----------|-------------|
| Medical | 35 |
| Food & Water | 20 |
| Shelter | 20 |
| Family & Missing Persons | 20 |
| Safety & Emergency | 20 |
| Transportation | 20 |
| Communication | 20 |
| **Total** | **155** |

Each phrase is stored with translations in all 6 supported languages. Phrases are searchable in any language column, including right-to-left scripts.

---

## Architecture

The project follows MVVM (Model-View-ViewModel) with the Repository pattern, as recommended by Google for Android development.

```
UI Layer        — Fragments + ViewModels (StateFlow)
     |
Domain Layer    — EmergencyPhrase, Language, TranslationResult, PhraseCategory
     |
Data Layer      — Repositories
     |
     +-- Room Database (SQLite)
     |       emergency_phrases (155 rows)
     |       translation_entries (70 rows)
     |       audio_cache
     |
     +-- ML Kit Translation Source
             Language Identification
             Neural Machine Translation (on-device)
```

**Single Activity** with Jetpack Navigation Component and a bottom navigation bar.  
**Dependency Injection** via Hilt (Dagger).  
**Async** via Kotlin Coroutines and StateFlow.  
**No data leaves the device** — no analytics, no telemetry, no cloud sync.

---

## Translation Logic

Translation follows a prioritised fallback chain:

1. **ML Kit** — Neural machine translation. Models (~30 MB per language pair) are downloaded once on first use and cached on-device for all subsequent offline use.
2. **Phrase matching** — The input is scored against all 155 stored phrases using a word-coverage algorithm. A match above the threshold returns the verified phrase translation instantly.
3. **Word dictionary** — Individual words are looked up in the 70-entry dictionary for single-word or partial inputs.
4. **Error state** — If all three fail, the UI shows a clear error message.

---

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin 1.9 |
| UI | Android Views + Material 3 |
| Architecture | MVVM |
| Dependency Injection | Hilt |
| Navigation | Jetpack Navigation Component |
| Database | Room (SQLite), version 5 |
| Async | Kotlin Coroutines + StateFlow |
| Translation | Google ML Kit Translation |
| Language Detection | Google ML Kit Language ID |
| Text-to-Speech | Android TextToSpeech API (in progress) |
| Speech-to-Text | Android SpeechRecognizer (in progress) |
| Build | Gradle with Kotlin DSL |
| Min SDK | 28 (Android 9) |
| Target SDK | 34 (Android 14) |
| Java Version | 17 |

---

## Project Structure

```
app/src/main/java/com/emergency/translator/
|
+-- EmergencyTranslatorApp.kt       Application class; sets night mode override
+-- MainActivity.kt                 Single activity; applies theme before onCreate
|
+-- di/
|   +-- AppModule.kt
|   +-- DatabaseModule.kt           Provides Room DAOs as Hilt singletons
|
+-- domain/model/
|   +-- Language.kt                 Enum: 6 languages with BCP-47 codes and locale helpers
|   +-- EmergencyPhrase.kt          Domain model with search and translation helpers
|   +-- TranslationResult.kt        Result wrapper with offline/online source flag
|
+-- data/
|   +-- local/
|   |   +-- database/
|   |   |   +-- AppDatabase.kt      Room DB v5; seeds 155 phrases + 70 dictionary entries
|   |   |   +-- EmergencyPhraseDao.kt
|   |   |   +-- TranslationEntryDao.kt
|   |   |   +-- AudioCacheDao.kt
|   |   +-- entity/
|   |       +-- EmergencyPhraseEntity.kt
|   |       +-- TranslationEntryEntity.kt
|   |       +-- AudioCacheEntity.kt
|   +-- repository/
|   |   +-- PhraseRepository.kt
|   |   +-- TranslationRepository.kt
|   |   +-- AudioRepository.kt      (in progress)
|   +-- translation/
|       +-- MLKitTranslationSource.kt
|
+-- ui/
    +-- common/
    |   +-- UiState.kt              Sealed class: Idle / Loading / Success / Error
    |   +-- TtsManager.kt           (in progress)
    |   +-- ThemeManager.kt         SharedPreferences-backed theme singleton
    +-- splash/
    |   +-- SplashFragment.kt
    +-- home/
    |   +-- HomeViewModel.kt
    |   +-- HomeFragment.kt
    +-- phrases/
    |   +-- PhrasesViewModel.kt
    |   +-- PhrasesFragment.kt
    |   +-- PhraseAdapter.kt
    +-- settings/
        +-- SettingsViewModel.kt
        +-- SettingsFragment.kt
```

---

## Theme System

Six explicit Material 3 themes are available, selectable from the Settings screen:

| Mode | Accent |
|------|--------|
| Light | Blue |
| Light | Green |
| Light | Red |
| Dark | Blue |
| Dark | Green |
| Dark | Red |

Theme selection persists across restarts. The system navigation bar colour matches the active theme. Theme changes apply immediately without requiring a manual restart.

---

## Database

Room database version 5. The database is seeded automatically on first launch with all 155 phrases and 70 dictionary entries. The current build uses `fallbackToDestructiveMigration()`, which means user favourites are lost on schema version upgrades. Explicit migration scripts are planned for v1.9.

---

## Known Limitations

- **TTS voice quality** varies significantly by device manufacturer. Samsung and Xiaomi AOSP voices produce noticeably lower quality for Arabic and Persian than Google TTS. Installing Google TTS from the Play Store improves results.
- **STT is unreliable in noisy environments**, which are common in the disaster and displacement scenarios this app targets. Typing and phrase browsing remain the primary input methods.
- **RTL layout mirroring** is not yet implemented. Arabic and Persian translated text renders correctly right-to-left, but the overall UI chrome remains left-to-right.
- **ML Kit models** require an internet connection on first use per language pair (approximately 30 MB each). All subsequent use is fully offline.
- **Favourites are lost** on database schema version upgrades in the current development build.

---

## Privacy

No data leaves the device. The app does not include any analytics library, crash reporting service, or cloud synchronisation. The `INTERNET` permission exists solely for ML Kit translation model download on first use. All phrase data, translation history, and user favourites are stored in local SQLite only.

---

## Documentation

Source-level documentation is co-located with each Kotlin file as a `.md` file in the same directory. A master index is at [`Documentation/INDEX.md`](Documentation/INDEX.md).

Project management documentation (architecture, sprint records, decision log, risk register, testing notes) is in this link, structured as a Notion-compatible knowledge base: https://app.notion.com/p/Project-Overview-38e810b037e980629633dcf240fd4c31

---

## Building

Requirements: Android Studio Hedgehog (2023.1.1) or later, JDK 17.

```
git clone [GitHub Repository URL]
cd EmergencyTranslator
./gradlew assembleDebug
```

The debug APK is output to `app/build/outputs/apk/debug/`.

---

## License

[License]
