package com.emergency.translator.ui.home

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.emergency.translator.R
import com.emergency.translator.databinding.FragmentHomeBinding
import com.emergency.translator.domain.model.Language
import com.emergency.translator.domain.model.TranslationResult
import com.emergency.translator.ui.common.TtsManager
import com.emergency.translator.ui.common.UiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var ttsManager: TtsManager
    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    private val requestMicPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startListening()
        else Toast.makeText(requireContext(), R.string.mic_permission_denied, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ttsManager = TtsManager(requireContext()).also { it.initialize() }
        setupLanguageDropdown()
        setupClickListeners()
        setupQuickActions()
        observeViewModel()
    }

    private fun setupLanguageDropdown() {
        val languages = Language.values()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            languages.map { "${it.displayName} – ${it.nativeName}" }
        )
        binding.targetLanguageDropdown.setAdapter(adapter)

        // Restore current selection from ViewModel (survives rotation)
        val currentLang = viewModel.selectedTargetLanguage.value
        binding.targetLanguageDropdown.setText(
            "${currentLang.displayName} – ${currentLang.nativeName}", false
        )
        binding.targetLanguageDropdown.setOnItemClickListener { _, _, position, _ ->
            viewModel.setTargetLanguage(languages[position])
        }
    }

    private fun setupClickListeners() {
        binding.translateButton.setOnClickListener {
            val text = binding.inputEditText.text?.toString() ?: ""
            if (text.isBlank()) {
                Toast.makeText(requireContext(), R.string.no_input_error, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.translate(text)
            }
        }

        binding.micButton.setOnClickListener {
            if (isListening) stopListening() else checkMicAndStart()
        }

        binding.speakerButton.setOnClickListener {
            val result = (viewModel.translationState.value as? UiState.Success)?.data
            result?.let { speakTranslation(it) }
        }

        binding.copyButton.setOnClickListener {
            val result = (viewModel.translationState.value as? UiState.Success)?.data
            result?.let { copyToClipboard(it.translatedText) }
        }

        binding.clearButton.setOnClickListener {
            binding.inputEditText.text?.clear()
            viewModel.reset()
        }
    }

    private fun setupQuickActions() {
        binding.actionMedical.setOnClickListener {
            viewModel.quickAction(getString(R.string.phrase_medical))
        }
        binding.actionFood.setOnClickListener {
            viewModel.quickAction(getString(R.string.phrase_food))
        }
        binding.actionWater.setOnClickListener {
            viewModel.quickAction(getString(R.string.phrase_water))
        }
        binding.actionShelter.setOnClickListener {
            viewModel.quickAction(getString(R.string.phrase_shelter))
        }
        binding.actionEvacuation.setOnClickListener {
            viewModel.quickAction(getString(R.string.phrase_evacuation))
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.translationState.collect { renderState(it) }
            }
        }
    }

    private fun renderState(state: UiState<TranslationResult>) {
        when (state) {
            is UiState.Idle -> {
                binding.progressBar.visibility = View.GONE
                binding.translationCard.visibility = View.GONE
                binding.translateButton.isEnabled = true
                binding.speakerButton.isEnabled = false
                binding.copyButton.isEnabled = false
            }
            is UiState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.translationCard.visibility = View.GONE
                binding.translateButton.isEnabled = false
            }
            is UiState.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.translateButton.isEnabled = true
                binding.translationCard.visibility = View.VISIBLE
                binding.speakerButton.isEnabled = true
                binding.copyButton.isEnabled = true
                binding.detectedLanguageChip.text = getString(
                    R.string.detected_language_label, state.data.detectedSourceLanguage
                )
                binding.translatedTextView.text = state.data.translatedText
                binding.translatedTextView.textDirection =
                    if (state.data.targetLanguage.isRtl) View.TEXT_DIRECTION_RTL
                    else View.TEXT_DIRECTION_LTR
            }
            is UiState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.translateButton.isEnabled = true
                binding.translationCard.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun speakTranslation(result: TranslationResult) {
        when (ttsManager.speak(result.translatedText, result.targetLanguage, result.originalText)) {
            TtsManager.SpeakOutcome.MISSING_DATA -> showTtsInstallPrompt(result.targetLanguage)
            TtsManager.SpeakOutcome.NOT_SUPPORTED ->
                Toast.makeText(requireContext(), R.string.tts_not_supported, Toast.LENGTH_SHORT).show()
            else -> { /* SPEAKING or QUEUED — success */ }
        }
    }

    private fun showTtsInstallPrompt(language: Language) {
        Snackbar.make(
            binding.root,
            getString(R.string.tts_missing_data, language.displayName),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.tts_install_action) {
            startActivity(ttsManager.installDataIntent())
        }.show()
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("translation", text))
        Toast.makeText(requireContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
    }

    // ── Speech recognition ────────────────────────────────────────────────────

    private fun checkMicAndStart() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) startListening()
        else requestMicPermission.launch(Manifest.permission.RECORD_AUDIO)
    }

    private fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(requireContext())) {
            Toast.makeText(requireContext(), R.string.speech_not_available, Toast.LENGTH_SHORT).show()
            return
        }
        speechRecognizer?.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                isListening = true
                binding.micButton.setIconResource(R.drawable.ic_mic_active)
                binding.micButton.text = getString(R.string.listening)
            }
            override fun onResults(results: Bundle?) {
                stopListeningUi()
                val text = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull() ?: return
                binding.inputEditText.setText(text)
                viewModel.translate(text)
            }
            override fun onError(error: Int) {
                stopListeningUi()
                if (error != SpeechRecognizer.ERROR_CLIENT) {
                    Toast.makeText(requireContext(), R.string.speech_error, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partial: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }
        speechRecognizer?.startListening(intent)
    }

    private fun stopListening() {
        speechRecognizer?.stopListening()
        stopListeningUi()
    }

    private fun stopListeningUi() {
        isListening = false
        binding.micButton.setIconResource(R.drawable.ic_mic)
        binding.micButton.text = getString(R.string.speak)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ttsManager.shutdown()
        speechRecognizer?.destroy()
        _binding = null
    }
}
