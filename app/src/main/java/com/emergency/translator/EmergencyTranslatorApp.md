# EmergencyTranslatorApp.kt

Application entry point annotated with `@HiltAndroidApp` for dependency injection.

## Class: `EmergencyTranslatorApp`

Extends `Application` and bootstraps the app on startup.

### Properties

| Property | Description |
|----------|-------------|
| `phraseRepository` | Injected repository for emergency phrase data. |
| `audioRepository` | Injected repository for TTS audio cache generation. |
| `appScope` | IO coroutine scope with `SupervisorJob` for background initialization. |

### Methods

#### `onCreate()`
- Disables system night-mode override via `AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)` so `ThemeManager` controls theming.
- Launches background tasks to seed default phrases and pre-generate audio cache files for all phrases and languages.
