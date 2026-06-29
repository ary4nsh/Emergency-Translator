# MainActivity.kt

Main activity hosting the navigation graph and bottom navigation bar.

## Class: `MainActivity`

`@AndroidEntryPoint` `AppCompatActivity` that serves as the single-activity host.

### Properties

| Property | Description |
|----------|-------------|
| `binding` | View binding for `activity_main` layout. |

### Methods

#### `onCreate(savedInstanceState: Bundle?)`
- Applies the user-selected theme via `ThemeManager.getThemeResId()` before `super.onCreate()`.
- Inflates the main layout and wires the bottom navigation to the `NavHostFragment`.
- Hides the bottom navigation bar on the splash screen; shows it on all other destinations.
