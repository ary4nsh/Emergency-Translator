# Emergency Translator — Documentation Index

All documentation files live next to their corresponding source files. This index links every file for quick navigation.

---

## App Entry Points

| Source File | Documentation |
|-------------|---------------|
| `EmergencyTranslatorApp.kt` | [EmergencyTranslatorApp.md](../app/src/main/java/com/emergency/translator/EmergencyTranslatorApp.md) |
| `MainActivity.kt` | [MainActivity.md](../app/src/main/java/com/emergency/translator/MainActivity.md) |

---

## Dependency Injection (`di/`)

| Source File | Documentation |
|-------------|---------------|
| `AppModule.kt` | [AppModule.md](../app/src/main/java/com/emergency/translator/di/AppModule.md) |
| `DatabaseModule.kt` | [DatabaseModule.md](../app/src/main/java/com/emergency/translator/di/DatabaseModule.md) |

---

## Domain Layer (`domain/model/`)

| Source File | Documentation |
|-------------|---------------|
| `Language.kt` | [Language.md](../app/src/main/java/com/emergency/translator/domain/model/Language.md) |
| `EmergencyPhrase.kt` | [EmergencyPhrase.md](../app/src/main/java/com/emergency/translator/domain/model/EmergencyPhrase.md) |
| `TranslationResult.kt` | [TranslationResult.md](../app/src/main/java/com/emergency/translator/domain/model/TranslationResult.md) |

---

## Data Layer

### Room Entities (`data/local/entity/`)

| Source File | Documentation |
|-------------|---------------|
| `EmergencyPhraseEntity.kt` | [EmergencyPhraseEntity.md](../app/src/main/java/com/emergency/translator/data/local/entity/EmergencyPhraseEntity.md) |
| `TranslationEntryEntity.kt` | [TranslationEntryEntity.md](../app/src/main/java/com/emergency/translator/data/local/entity/TranslationEntryEntity.md) |
| `AudioCacheEntity.kt` | [AudioCacheEntity.md](../app/src/main/java/com/emergency/translator/data/local/entity/AudioCacheEntity.md) |

### Room DAOs (`data/local/database/`)

| Source File | Documentation |
|-------------|---------------|
| `AppDatabase.kt` | [AppDatabase.md](../app/src/main/java/com/emergency/translator/data/local/database/AppDatabase.md) |
| `EmergencyPhraseDao.kt` | [EmergencyPhraseDao.md](../app/src/main/java/com/emergency/translator/data/local/database/EmergencyPhraseDao.md) |
| `TranslationEntryDao.kt` | [TranslationEntryDao.md](../app/src/main/java/com/emergency/translator/data/local/database/TranslationEntryDao.md) |
| `AudioCacheDao.kt` | [AudioCacheDao.md](../app/src/main/java/com/emergency/translator/data/local/database/AudioCacheDao.md) |

### Repositories (`data/repository/`)

| Source File | Documentation |
|-------------|---------------|
| `PhraseRepository.kt` | [PhraseRepository.md](../app/src/main/java/com/emergency/translator/data/repository/PhraseRepository.md) |
| `TranslationRepository.kt` | [TranslationRepository.md](../app/src/main/java/com/emergency/translator/data/repository/TranslationRepository.md) |
| `AudioRepository.kt` | [AudioRepository.md](../app/src/main/java/com/emergency/translator/data/repository/AudioRepository.md) |

### Translation Source (`data/translation/`)

| Source File | Documentation |
|-------------|---------------|
| `MLKitTranslationSource.kt` | [MLKitTranslationSource.md](../app/src/main/java/com/emergency/translator/data/translation/MLKitTranslationSource.md) |

---

## UI Layer

### Common (`ui/common/`)

| Source File | Documentation |
|-------------|---------------|
| `UiState.kt` | [UiState.md](../app/src/main/java/com/emergency/translator/ui/common/UiState.md) |
| `TtsManager.kt` | [TtsManager.md](../app/src/main/java/com/emergency/translator/ui/common/TtsManager.md) |
| `ThemeManager.kt` | [ThemeManager.md](../app/src/main/java/com/emergency/translator/ui/common/ThemeManager.md) |

### Splash (`ui/splash/`)

| Source File | Documentation |
|-------------|---------------|
| `SplashFragment.kt` | [SplashFragment.md](../app/src/main/java/com/emergency/translator/ui/splash/SplashFragment.md) |

### Home (`ui/home/`)

| Source File | Documentation |
|-------------|---------------|
| `HomeViewModel.kt` | [HomeViewModel.md](../app/src/main/java/com/emergency/translator/ui/home/HomeViewModel.md) |
| `HomeFragment.kt` | [HomeFragment.md](../app/src/main/java/com/emergency/translator/ui/home/HomeFragment.md) |

### Phrases (`ui/phrases/`)

| Source File | Documentation |
|-------------|---------------|
| `PhrasesViewModel.kt` | [PhrasesViewModel.md](../app/src/main/java/com/emergency/translator/ui/phrases/PhrasesViewModel.md) |
| `PhrasesFragment.kt` | [PhrasesFragment.md](../app/src/main/java/com/emergency/translator/ui/phrases/PhrasesFragment.md) |
| `PhraseAdapter.kt` | [PhraseAdapter.md](../app/src/main/java/com/emergency/translator/ui/phrases/PhraseAdapter.md) |

### Settings (`ui/settings/`)

| Source File | Documentation |
|-------------|---------------|
| `SettingsViewModel.kt` | [SettingsViewModel.md](../app/src/main/java/com/emergency/translator/ui/settings/SettingsViewModel.md) |
| `SettingsFragment.kt` | [SettingsFragment.md](../app/src/main/java/com/emergency/translator/ui/settings/SettingsFragment.md) |

---

## Architecture Overview

```
App
├── di/               Hilt modules wiring the dependency graph
├── domain/model/     Pure Kotlin models shared across layers
├── data/
│   ├── local/        Room database, DAOs, and entities
│   ├── repository/   Single source of truth for phrases, translation, audio
│   └── translation/  ML Kit wrapper for online/offline sentence translation
└── ui/
    ├── common/       Shared utilities (UiState, TtsManager, ThemeManager)
    ├── splash/       Splash screen
    ├── home/         Free-text translation screen
    ├── phrases/      Browsable emergency phrase library
    └── settings/     Theme, dark mode, and app info
```

### Key Design Decisions

- **Single Activity** (`MainActivity`) with Navigation Component and a `BottomNavigationView`.
- **MVVM** — ViewModels hold `StateFlow` state; Fragments observe and render without business logic.
- **Fully offline** — Room DB seeds 155 phrases + ~70 dictionary words; ML Kit models (~30 MB) cache on-device after first download.
- **TTS fallback chain** — Offline voice → primary locale → alternate locale → English, with install prompt on missing data.
- **Theme system** — 6 explicit Material 3 themes (3 light + 3 dark accents) selected via `ThemeManager` + `SharedPreferences`; `Activity.setTheme()` applied before `super.onCreate()`.
