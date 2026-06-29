# PhrasesViewModel.kt

ViewModel for browsing, filtering, and translating emergency phrases.

## Class: `PhrasesViewModel`

`@HiltViewModel` injected with `PhraseRepository`.

### State Flows

| Property | Type | Description |
|----------|------|-------------|
| `translationState` | `StateFlow<UiState<TranslationResult>>` | Selected phrase translation result. |
| `selectedLanguage` | `StateFlow<Language>` | Target language (default: Arabic). |
| `currentCategory` | `StateFlow<PhraseCategory?>` | Active category filter (`null` = all). |
| `searchQuery` | `StateFlow<String>` | Current search text. |
| `showFavoritesOnly` | `StateFlow<Boolean>` | Whether to show only favorites. |
| `phrases` | `StateFlow<List<EmergencyPhrase>>` | Filtered phrase list derived from DB + filters. |

### Methods

#### `setLanguage(language: Language)`
Sets target language and resets translation state.

#### `setCategory(category: PhraseCategory?)`
Filters by category; clears favorites-only mode.

#### `setShowFavoritesOnly(show: Boolean)`
Shows only favorites; clears category filter when enabled.

#### `setSearchQuery(query: String)`
Updates the in-memory search filter.

#### `translatePhrase(phrase: EmergencyPhrase)`
Immediately produces a `Success` translation result from stored phrase text.

#### `toggleFavorite(phrase: EmergencyPhrase)`
Toggles favorite status in the database.

#### `dismissTranslation()`
Hides the translation result card.
