# AudioCacheEntity.kt

Room entity mapping a phrase and language to a pre-generated WAV file path.

## Class: `AudioCacheEntity`

`@Entity(tableName = "audio_cache")`

### Properties

| Property | Description |
|----------|-------------|
| `id` | Auto-generated primary key. |
| `phraseId` | Foreign key to the emergency phrase. |
| `languageCode` | BCP-47 language code (e.g. `ar`, `fa`). |
| `filePath` | Absolute path to the cached WAV file on disk. |
