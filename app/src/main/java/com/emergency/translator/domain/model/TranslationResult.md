# TranslationResult.kt

Domain model representing the outcome of a translation operation.

## Class: `TranslationResult`

Data class holding translation output metadata.

### Properties

| Property | Description |
|----------|-------------|
| `originalText` | User input or source phrase text. |
| `translatedText` | Result text in the target language. |
| `detectedSourceLanguage` | Human-readable name of the detected or assumed source language. |
| `targetLanguage` | `Language` enum for the translation target. |
| `phraseId` | Non-zero when the result came from a known emergency phrase (enables cached audio playback). |
