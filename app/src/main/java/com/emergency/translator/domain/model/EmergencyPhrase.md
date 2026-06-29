# EmergencyPhrase.kt

Domain model for an emergency phrase and its category enum.

## Class: `EmergencyPhrase`

Data class representing a phrase with translations in six languages.

### Properties

| Property | Description |
|----------|-------------|
| `id` | Unique phrase identifier. |
| `englishText` … `ukrainianText` | Translations in six languages. |
| `category` | `PhraseCategory` enum value. |
| `isFavorite` | User favorite flag. |

### Methods

#### `getTextForLanguage(language: Language): String`
Returns the phrase text for the specified language.

#### `matchesSearch(query: String): Boolean`
Returns `true` if the query matches any language column (case-insensitive for Latin scripts).

## Enum: `PhraseCategory`

Emergency phrase categories with display names:

| Value | Display Name |
|-------|--------------|
| `MEDICAL` | Medical |
| `FOOD_WATER` | Food & Water |
| `SHELTER` | Shelter |
| `FAMILY` | Family |
| `SAFETY` | Safety |
| `TRANSPORTATION` | Transportation |
| `COMMUNICATION` | Communication |
