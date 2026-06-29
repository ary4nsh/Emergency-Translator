# PhraseAdapter.kt

RecyclerView adapter for displaying emergency phrases in a list.

## Class: `PhraseAdapter`

`ListAdapter` with `DiffUtil` for efficient list updates.

### Constructor Parameters

| Parameter | Description |
|-----------|-------------|
| `onPhraseClick` | Callback when a phrase row is tapped. |
| `onFavoriteClick` | Callback when the favorite button is tapped. |

### Methods

| Method | Description |
|--------|-------------|
| `onCreateViewHolder()` | Inflates `item_phrase` binding. |
| `onBindViewHolder()` | Binds phrase data to the view holder. |

## Inner Class: `PhraseViewHolder`

Binds a single `EmergencyPhrase` to the row layout.

#### `bind(phrase: EmergencyPhrase)`
Sets English text, category chip label/color, favorite icon, and click listeners.

## Private Extension

#### `PhraseCategory.chipColor(): Int`
Returns a distinct ARGB color for each phrase category chip.

## Companion: `DiffCallback`

`DiffUtil.ItemCallback` comparing phrases by `id` (items) and full equality (contents).
