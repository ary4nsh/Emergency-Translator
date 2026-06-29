# TtsManager.kt

Wrapper around Android Text-to-Speech with offline voice selection and fallback chain.

## Class: `TtsManager`

Handles async TTS initialization, queued speech, and locale/voice fallback.

### Properties

| Property | Description |
|----------|-------------|
| `tts` | Underlying `TextToSpeech` engine instance. |
| `ready` | Whether the engine has finished initializing. |
| `pending` | Queued speak action to run once the engine is ready. |

### Methods

#### `initialize()`
Creates and initializes the TTS engine; runs any pending speak call on success.

#### `speak(text, language, englishFallback): SpeakOutcome`
Speaks text in the target language. Queues the call if the engine is not ready. Returns a `SpeakOutcome` indicating the result.

#### `installDataIntent(): Intent`
Returns an intent to open the system TTS data installation screen.

#### `shutdown()`
Stops and releases the TTS engine.

### Private Methods

#### `doSpeak(text, language, fallback): SpeakOutcome`
Executes the fallback chain: offline voice → primary locale → alternate locale → English fallback.

## Enum: `SpeakOutcome`

| Value | Description |
|-------|-------------|
| `SPEAKING` | Audio is playing. |
| `QUEUED` | Call queued until engine initializes. |
| `MISSING_DATA` | Language data missing; English fallback spoken. |
| `NOT_SUPPORTED` | Language unsupported; English fallback spoken. |
| `SILENT` | Blank text; nothing spoken. |

## Extension Functions

| Function | Description |
|----------|-------------|
| `Language.bcp47()` | Returns the BCP-47 code for the language. |
| `Language.primaryLocale()` | Returns the primary `Locale` with country code. |
| `Language.altLocale()` | Returns an alternate `Locale` without country, or `null` for English. |
