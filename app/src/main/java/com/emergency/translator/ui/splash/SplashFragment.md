# SplashFragment.kt

Splash screen shown on app launch with optional skip after theme changes.

## Class: `SplashFragment`

Non-Hilt fragment displaying the splash animation before navigating to home.

### Methods

#### `onCreateView()`
Inflates `fragment_splash` binding.

#### `onViewCreated()`
Schedules navigation to `homeFragment`:
- **0 ms delay** if `ThemeManager.consumeSkipSplash()` returns true (after a theme change).
- **2200 ms delay** on a normal cold start.

Uses `Handler` on the main looper and checks `isAdded` / `!isDetached` before navigating.

#### `onDestroyView()`
Clears the view binding reference.
