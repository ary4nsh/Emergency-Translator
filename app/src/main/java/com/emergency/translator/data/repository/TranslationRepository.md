# TranslationRepository.kt

Repository orchestrating ML Kit translation with offline phrase and dictionary fallback.

## Class: `TranslationRepository`

`@Singleton` class injected with `MLKitTranslationSource`, `EmergencyPhraseDao`, and `TranslationEntryDao`.

### Methods

#### `translate(text, targetLanguage, sourceLanguage?): Result<TranslationResult>`
Primary translation entry point:
1. Attempts ML Kit language detection and translation.
2. On failure, falls back to offline phrase matching and word dictionary lookup.

### Private Methods

| Method | Description |
|--------|-------------|
| `offlineFallback(text, targetLanguage)` | Matches input against stored phrases and dictionary words. |
| `findBestPhrase(input, phrases)` | Scores all phrases across six languages using word-coverage algorithm. |
| `findWordTranslations(queryWords, entries, targetLanguage)` | Translates individual words found in the dictionary. |
| `queryCoverage(q, p)` | Returns fraction of query words present in a phrase (0.0–1.0). |
| `normalize(text)` | Lowercases and strips punctuation for matching. |

### Private Data Class

#### `PhraseMatch`
Holds the best-matching phrase, detected language name, and match score.
