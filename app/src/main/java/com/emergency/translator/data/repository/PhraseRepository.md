# PhraseRepository.kt

Repository bridging Room phrase/dictionary data to the domain layer.

## Class: `PhraseRepository`

`@Singleton` class injected with `AppDatabase` and `TranslationEntryDao`.

### Properties

| Property | Description |
|----------|-------------|
| `allPhrases` | `Flow<List<EmergencyPhrase>>` of all phrases mapped from entities to domain models. |

### Methods

#### `initializeDefaultPhrases()`
Seeds default phrases and dictionary entries if the database tables are empty.

#### `findPhraseByEnglish(text: String): EmergencyPhrase?`
Looks up a phrase by exact English text and returns the domain model.

#### `toggleFavorite(id: Long, isFavorite: Boolean)`
Updates the favorite status of a phrase in the database.

### Private Methods

#### `EmergencyPhraseEntity.toDomain()`
Maps a Room entity to `EmergencyPhrase`, parsing the category string into `PhraseCategory` with a `SAFETY` fallback.
