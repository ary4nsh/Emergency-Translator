# EmergencyPhraseEntity.kt

Room entity representing a stored emergency phrase with translations in six languages.

## Class: `EmergencyPhraseEntity`

`@Entity(tableName = "emergency_phrases")`

### Properties

| Property | Description |
|----------|-------------|
| `id` | Auto-generated primary key. |
| `englishText` | English phrase text. |
| `arabicText` | Arabic translation. |
| `persianText` | Persian translation. |
| `turkishText` | Turkish translation. |
| `russianText` | Russian translation. |
| `ukrainianText` | Ukrainian translation. |
| `category` | Category name string (e.g. `MEDICAL`). |
| `isFavorite` | Whether the user marked this phrase as a favorite. |

### Methods

#### `getTextForLanguage(language: Language): String`
Returns the phrase text for the given `Language` enum value.
