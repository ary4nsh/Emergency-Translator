# TranslationEntryEntity.kt

Room entity for a single multilingual dictionary word entry.

## Class: `TranslationEntryEntity`

`@Entity(tableName = "translation_entries")`

### Properties

| Property | Description |
|----------|-------------|
| `id` | Auto-generated primary key. |
| `englishWord` | English word. |
| `arabicWord` | Arabic translation. |
| `persianWord` | Persian translation. |
| `turkishWord` | Turkish translation. |
| `russianWord` | Russian translation. |
| `ukrainianWord` | Ukrainian translation. |
| `category` | Word category (e.g. `MEDICAL`, `BASIC_NEEDS`). |

### Methods

#### `getTranslation(language: Language): String`
Returns the word translation for the given `Language`.
