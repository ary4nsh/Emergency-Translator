# TranslationEntryDao.kt

Room DAO interface for the offline word dictionary.

## Interface: `TranslationEntryDao`

### Methods

| Method | Description |
|--------|-------------|
| `insertAll(entries)` | Bulk insert with `IGNORE` on conflict. |
| `getCount()` | Returns total dictionary entry count. |
| `getAllEntries()` | Fetches all dictionary entries for offline word matching. |
