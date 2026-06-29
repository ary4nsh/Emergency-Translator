# DatabaseModule.kt

Hilt module providing Room database and DAO dependencies.

## Object: `DatabaseModule`

`@Module` installed in `SingletonComponent`.

### Provider Methods

| Method | Scope | Description |
|--------|-------|-------------|
| `provideAppDatabase(context)` | `@Singleton` | Creates the `AppDatabase` instance via `AppDatabase.create()`. |
| `provideEmergencyPhraseDao(database)` | — | Provides `EmergencyPhraseDao`. |
| `provideTranslationEntryDao(database)` | — | Provides `TranslationEntryDao`. |
| `provideAudioCacheDao(database)` | — | Provides `AudioCacheDao`. |
