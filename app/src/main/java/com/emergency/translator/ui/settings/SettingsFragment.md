# SettingsFragment.kt

Settings screen for dark mode, accent color, and app info.

## Class: `SettingsFragment`

`@AndroidEntryPoint` fragment for the settings tab.

### Properties

| Property | Description |
|----------|-------------|
| `viewModel` | `SettingsViewModel` via `viewModels()` (currently unused). |
| `COLOR_BLUE`, `COLOR_GREEN`, `COLOR_RED` | ARGB values for accent color swatches. |

### Methods

| Method | Description |
|--------|-------------|
| `setupDarkModeSwitch()` | Binds dark mode toggle to `ThemeManager`. |
| `setupColorPicker()` | Renders and wires blue/green/red accent swatches. |
| `renderSwatch(container, color, selected, dark, enabled)` | Draws a color circle with optional selection ring. |
| `oval(fillColor)` | Creates a filled oval `GradientDrawable`. |
| `ringOval(strokeColor)` | Creates a transparent oval with stroke ring. |
| `dpToPx(dp)` | Converts density-independent pixels to pixels. |
| `applyThemeChange()` | Marks splash skip flag and recreates the activity. |
| `setupAboutSection()` | Displays app version and offline mode status. |
