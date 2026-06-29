# EmergencyPhraseDao.kt

Room DAO interface for CRUD and query operations on emergency phrases.

## Interface: `EmergencyPhraseDao`

### Methods

| Method | Description |
|--------|-------------|
| `getAllPhrases()` | Returns all phrases as a `Flow`, ordered by category and id. |
| `getAllPhrasesOnce()` | Suspending one-shot fetch of all phrases. |
| `getPhrasesByCategory(category)` | Returns phrases filtered by category as a `Flow`. |
| `getFavorites()` | Returns favorite phrases (`isFavorite = 1`) as a `Flow`. |
| `searchPhrases(query)` | Multi-language partial text search across all six language columns. |
| `findByEnglishText(text)` | Finds a single phrase by exact English text match. |
| `insertAll(phrases)` | Bulk insert with `IGNORE` on conflict. |
| `updateFavorite(id, isFavorite)` | Toggles the favorite flag for a phrase by id. |
| `getCount()` | Returns total phrase count (used to detect empty database). |
