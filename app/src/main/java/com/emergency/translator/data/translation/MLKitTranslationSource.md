# MLKitTranslationSource.kt

Wrapper around Google ML Kit Translation and Language Identification APIs.

## Class: `MLKitTranslationSource`

`@Singleton` class that caches translators per language pair.

### Properties

| Property | Description |
|----------|-------------|
| `languageIdentifier` | ML Kit language identification client. |
| `translatorCache` | Map of cached `Translator` instances keyed by `source_target`. |

### Methods

#### `detectLanguage(text: String): String`
Identifies the BCP-47 language code of the input text. Returns `"en"` on failure or uncertain (`"und"`) results.

#### `translate(text, sourceLang, targetLang): String`
Translates text between two languages. Downloads the model pair on first use (~30 MB). Returns original text if source equals target or text is blank.

#### `close()`
Closes all cached translators and the language identifier client.

### Private Methods

#### `getOrCreate(source, target): Translator`
Returns a cached or newly created ML Kit `Translator` for the language pair.

## Top-Level Function

#### `Task<T>.await(): T`
Coroutine bridge suspending until a Google Play Services `Task` completes or fails.
