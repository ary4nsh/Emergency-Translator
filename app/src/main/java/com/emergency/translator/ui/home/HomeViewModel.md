# HomeViewModel.kt

ViewModel for the home translation screen.

## Class: `HomeViewModel`

`@HiltViewModel` injected with `TranslationRepository` and `PhraseRepository`.

### State Flows

| Property | Type | Description |
|----------|------|-------------|
| `translationState` | `StateFlow<UiState<TranslationResult>>` | Current translation operation state. |
| `selectedTargetLanguage` | `StateFlow<Language>` | User-selected target language (default: English). |

### Methods

#### `setTargetLanguage(language: Language)`
Updates the target language for translations.

#### `translate(text: String)`
Translates user input via `TranslationRepository`; emits `Loading` then `Success` or `Error`.

#### `quickAction(englishPhrase: String)`
Looks up a pre-stored phrase by English text for instant translation; falls back to ML Kit if not found.

#### `reset()`
Resets `translationState` to `Idle`.
