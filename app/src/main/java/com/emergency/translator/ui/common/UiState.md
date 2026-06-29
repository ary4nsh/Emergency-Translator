# UiState.kt

Generic sealed class representing asynchronous UI state.

## Sealed Class: `UiState<out T>`

| Variant | Description |
|---------|-------------|
| `Idle` | Initial or reset state; no data loaded. |
| `Loading` | Operation in progress. |
| `Success<T>(data)` | Operation succeeded with payload of type `T`. |
| `Error(message)` | Operation failed with an error message. |

Used by ViewModels to drive loading indicators, result cards, and error toasts in fragments.
