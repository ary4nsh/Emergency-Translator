# AudioCacheDao.kt

Room DAO interface for pre-generated TTS audio file metadata.

## Interface: `AudioCacheDao`

### Methods

| Method | Description |
|--------|-------------|
| `insert(entry)` | Inserts or replaces an `AudioCacheEntity` record. |
| `getAudioEntry(phraseId, languageCode)` | Looks up cached audio path for a phrase and language. |
| `getCount()` | Returns total cache entry count (skips regeneration when > 0). |
