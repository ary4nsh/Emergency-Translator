# AppDatabase.kt

Room database definition for emergency phrases, translation dictionary entries, and audio cache metadata.

## Class: `AppDatabase`

Abstract `RoomDatabase` (version 5) managing three tables via DAOs.

### Abstract Methods

| Method | Returns | Description |
|--------|---------|-------------|
| `emergencyPhraseDao()` | `EmergencyPhraseDao` | Access to the emergency phrases table. |
| `translationEntryDao()` | `TranslationEntryDao` | Access to the word dictionary table. |
| `audioCacheDao()` | `AudioCacheDao` | Access to the audio cache metadata table. |

## Companion Object

### Seed Data

Pre-built lists of default content inserted on first launch:

| Constant | Description |
|----------|-------------|
| `MEDICAL` | 35 medical emergency phrases. |
| `FOOD_WATER` | 20 food and water phrases. |
| `SHELTER` | 20 shelter-related phrases. |
| `FAMILY` | 20 family/tracing phrases. |
| `SAFETY` | 20 safety and danger phrases. |
| `TRANSPORTATION` | 20 transportation phrases. |
| `COMMUNICATION` | 20 communication phrases. |
| `DEFAULT_PHRASES` | Combined list of all 155 phrases. |
| `DEFAULT_DICTIONARY` | ~70 multilingual word entries for offline lookup. |

### Private Helpers

#### `p(en, ar, fa, tr, ru, uk, cat)`
Factory for creating `EmergencyPhraseEntity` seed records from seven language strings and a category.

#### `entry(en, ar, fa, tr, ru, uk, cat)`
Factory for creating `TranslationEntryEntity` dictionary seed records.

### Public Methods

#### `create(context: Context): AppDatabase`
Builds the Room database named `emergency_translator.db` with destructive migration fallback.

#### `getDefaultPhrases()`
Returns the full default phrase seed list.

#### `getDefaultDictionary()`
Returns the default word dictionary seed list.
