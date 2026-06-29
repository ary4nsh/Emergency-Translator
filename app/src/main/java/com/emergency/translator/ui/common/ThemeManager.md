# ThemeManager.kt

Singleton utility for persisting and resolving app theme preferences.

## Object: `ThemeManager`

Manages accent color, dark mode, and splash-skip flags via `SharedPreferences`.

## Enum: `AccentColor`

| Value | Description |
|-------|-------------|
| `BLUE` | Default blue accent theme. |
| `GREEN` | Green accent theme. |
| `RED` | Red accent theme. |

### Methods

| Method | Description |
|--------|-------------|
| `getAccentColor(context)` | Returns the saved accent color (default: `BLUE`). |
| `isDarkMode(context)` | Returns whether dark mode is enabled. |
| `setAccentColor(context, accent)` | Persists the chosen accent color. |
| `setDarkMode(context, dark)` | Persists dark mode preference. |
| `markThemeChange(context)` | Sets a flag to skip splash animation after theme recreation. |
| `consumeSkipSplash(context)` | Reads and clears the skip-splash flag. |
| `getThemeResId(context)` | Resolves the correct `R.style` resource for the current accent and dark mode combination. |

### Private Methods

#### `prefs(context): SharedPreferences`
Returns the `theme_prefs` shared preferences instance.
