# AudioRepository.kt

Repository managing offline TTS audio cache generation and playback.

## Class: `AudioRepository`

`@Singleton` class that pre-synthesizes WAV files for every phrase in every language using the device TTS engine.

### Properties

| Property | Description |
|----------|-------------|
| `audioDir` | Private `filesDir/audio` directory for cached WAV files. |
| `mediaPlayer` | Reusable `MediaPlayer` instance for playback. |

### Methods

#### `generateAudioCache()`
Background task called at app start. Skips if cache already exists. Synthesizes missing WAV files and records paths in Room.

#### `getCachedFile(phraseId, language): File?`
Returns the cached WAV file for a phrase and language, or `null` if missing.

#### `playFile(file: File)`
Plays a WAV file via `MediaPlayer` on the main thread. Releases player on completion.

#### `release()`
Releases the active `MediaPlayer` instance.

### Private Methods

| Method | Description |
|--------|-------------|
| `initTts()` | Suspends until a `TextToSpeech` engine is ready or fails. |
| `synthesizeToFile(tts, text, file)` | Writes TTS output to a WAV file using `UtteranceProgressListener`. |
| `Language.toLocale()` | Maps `Language` enum to `java.util.Locale`. |
