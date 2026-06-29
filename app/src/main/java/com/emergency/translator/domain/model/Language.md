# Language.kt

Enum defining supported languages with metadata.

## Enum: `Language`

| Value | Code | Display Name | RTL |
|-------|------|--------------|-----|
| `ENGLISH` | `en` | English | No |
| `ARABIC` | `ar` | Arabic | Yes |
| `PERSIAN` | `fa` | Persian | Yes |
| `TURKISH` | `tr` | Turkish | No |
| `RUSSIAN` | `ru` | Russian | No |
| `UKRAINIAN` | `uk` | Ukrainian | No |

### Properties

| Property | Description |
|----------|-------------|
| `code` | BCP-47 language code. |
| `displayName` | English display name. |
| `nativeName` | Name in the language's own script. |
| `isRtl` | Whether the language uses right-to-left text. |

## Companion Object

#### `fromCode(code: String): Language`
Resolves a BCP-47 code to a `Language` enum value; defaults to `ENGLISH` if unknown.
