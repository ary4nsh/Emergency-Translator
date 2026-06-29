# HomeFragment.kt

Main translation screen with text input, voice input, quick actions, and TTS playback.

## Class: `HomeFragment`

`@AndroidEntryPoint` fragment for the home tab.

### Properties

| Property | Description |
|----------|-------------|
| `viewModel` | `HomeViewModel` via `viewModels()`. |
| `ttsManager` | Manages text-to-speech for translated output. |
| `speechRecognizer` | Android speech recognition instance. |
| `isListening` | Whether microphone input is active. |
| `requestMicPermission` | Activity result launcher for `RECORD_AUDIO` permission. |

### Lifecycle Methods

| Method | Description |
|--------|-------------|
| `onCreateView()` | Inflates `fragment_home` binding. |
| `onViewCreated()` | Initializes TTS, dropdown, listeners, quick actions, and observers. |
| `onDestroyView()` | Shuts down TTS, destroys speech recognizer, clears binding. |

### Setup Methods

| Method | Description |
|--------|-------------|
| `setupLanguageDropdown()` | Populates target language dropdown from `Language` enum. |
| `setupClickListeners()` | Wires translate, mic, speaker, copy, and clear buttons. |
| `setupQuickActions()` | Maps quick-action chips to common emergency phrases. |
| `observeViewModel()` | Collects `translationState` and renders UI updates. |

### UI Methods

| Method | Description |
|--------|-------------|
| `renderState(state)` | Shows/hides progress, result card, and buttons based on `UiState`. |
| `speakTranslation(result)` | Speaks translated text; prompts TTS install if data is missing. |
| `showTtsInstallPrompt(language)` | Shows a Snackbar with link to install TTS language data. |
| `copyToClipboard(text)` | Copies translated text to the system clipboard. |

### Speech Recognition

| Method | Description |
|--------|-------------|
| `checkMicAndStart()` | Requests mic permission if needed, then starts listening. |
| `startListening()` | Creates `SpeechRecognizer`, listens for speech, auto-translates result. |
| `stopListening()` | Stops recognition and resets mic button UI. |
| `stopListeningUi()` | Resets mic button icon and label. |
