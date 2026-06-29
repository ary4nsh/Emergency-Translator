# PhrasesFragment.kt

Emergency phrases browser with category filtering, search, favorites, and audio playback.

## Class: `PhrasesFragment`

`@AndroidEntryPoint` fragment for the phrases tab.

### Properties

| Property | Description |
|----------|-------------|
| `viewModel` | `PhrasesViewModel` via `viewModels()`. |
| `audioRepository` | Injected repository for cached WAV playback. |
| `ttsManager` | Fallback TTS when cached audio is unavailable. |
| `phraseAdapter` | `RecyclerView` adapter for the phrase list. |
| `chipCategoryMap` | Maps dynamically created chip IDs to `PhraseCategory` values. |

### Setup Methods

| Method | Description |
|--------|-------------|
| `setupRecyclerView()` | Configures `PhraseAdapter` with click and favorite callbacks. |
| `setupCategoryChips()` | Dynamically adds category chips and wires the chip group listener. |
| `setupSearch()` | Filters phrases as the user types in the search field. |
| `setupLanguageDropdown()` | Populates the target language dropdown. |
| `setupButtons()` | Wires close and speak buttons on the result card. |
| `restoreUiState()` | Restores language, search text, and chip selection after rotation. |

### Observer / UI Methods

| Method | Description |
|--------|-------------|
| `observeViewModel()` | Collects `phrases` and `translationState` flows. |
| `updatePhraseList(phrases)` | Submits list to adapter; toggles empty state view. |
| `renderTranslation(state)` | Shows translation result card and auto-plays audio on success. |
| `speakResult(result)` | Plays cached WAV if available, otherwise uses TTS. |
| `speakWithTts(result)` | Speaks via `TtsManager` with install prompt on missing data. |
| `showTtsInstallPrompt(language)` | Snackbar prompting TTS language data installation. |
