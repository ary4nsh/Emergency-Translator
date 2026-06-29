package com.emergency.translator.ui.phrases

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.emergency.translator.R
import com.emergency.translator.data.repository.AudioRepository
import com.emergency.translator.databinding.FragmentPhrasesBinding
import com.emergency.translator.domain.model.EmergencyPhrase
import com.emergency.translator.domain.model.Language
import com.emergency.translator.domain.model.PhraseCategory
import com.emergency.translator.domain.model.TranslationResult
import com.emergency.translator.ui.common.TtsManager
import com.emergency.translator.ui.common.UiState
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhrasesFragment : Fragment() {

    private var _binding: FragmentPhrasesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhrasesViewModel by viewModels()

    @Inject lateinit var audioRepository: AudioRepository

    private lateinit var ttsManager: TtsManager
    private lateinit var phraseAdapter: PhraseAdapter

    // Maps each dynamically created category chip ID → PhraseCategory for the group listener
    private val chipCategoryMap = mutableMapOf<Int, PhraseCategory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhrasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ttsManager = TtsManager(requireContext()).also { it.initialize() }
        setupRecyclerView()
        setupCategoryChips()
        setupSearch()
        setupLanguageDropdown()
        setupButtons()
        observeViewModel()
        // After all UI is ready, restore ViewModel state (handles rotation)
        restoreUiState()
    }

    private fun setupRecyclerView() {
        phraseAdapter = PhraseAdapter(
            onPhraseClick = { phrase -> viewModel.translatePhrase(phrase) },
            onFavoriteClick = { phrase -> viewModel.toggleFavorite(phrase) }
        )
        binding.phrasesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = phraseAdapter
        }
    }

    private fun setupCategoryChips() {
        chipCategoryMap.clear()
        val group = binding.categoryChipGroup

        // Dynamically insert one chip per PhraseCategory, just before the Favorites chip
        PhraseCategory.values().forEach { category ->
            val chip = Chip(requireContext()).apply {
                text = category.displayName
                isCheckable = true
                tag = category
                id = View.generateViewId()
            }
            chipCategoryMap[chip.id] = category
            group.addView(chip, group.childCount - 1)  // before Favorites chip
        }

        // Single listener on the group handles all selection changes
        @Suppress("DEPRECATION")
        group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.chipAll.id -> viewModel.setCategory(null)
                binding.chipFavorites.id -> viewModel.setShowFavoritesOnly(true)
                else -> chipCategoryMap[checkedId]?.let { viewModel.setCategory(it) }
            }
        }
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupLanguageDropdown() {
        val languages = Language.values()
        val adp = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            languages.map { "${it.displayName} – ${it.nativeName}" }
        )
        binding.languageDropdown.setAdapter(adp)
    }

    private fun setupButtons() {
        binding.closeResultButton.setOnClickListener { viewModel.dismissTranslation() }
        binding.speakResultButton.setOnClickListener {
            val result = (viewModel.translationState.value as? UiState.Success)?.data
            result?.let { speakResult(it) }
        }
    }

    /**
     * Restores UI widgets from the ViewModel state that survived rotation.
     * Called once after all views are set up.
     */
    private fun restoreUiState() {
        // Restore language dropdown
        val lang = viewModel.selectedLanguage.value
        binding.languageDropdown.setText(
            "${lang.displayName} – ${lang.nativeName}", false
        )
        binding.languageDropdown.setOnItemClickListener { _, _, position, _ ->
            viewModel.setLanguage(Language.values()[position])
        }

        // Restore search text
        val query = viewModel.searchQuery.value
        if (query.isNotBlank() && binding.searchEditText.text.isNullOrBlank()) {
            binding.searchEditText.setText(query)
        }

        // Restore chip selection
        when {
            viewModel.showFavoritesOnly.value -> binding.chipFavorites.isChecked = true
            viewModel.currentCategory.value != null -> {
                val cat = viewModel.currentCategory.value
                val entry = chipCategoryMap.entries.firstOrNull { it.value == cat }
                if (entry != null) {
                    binding.categoryChipGroup.findViewById<Chip>(entry.key)?.isChecked = true
                }
            }
            else -> binding.chipAll.isChecked = true
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.phrases.collect { updatePhraseList(it) } }
                launch { viewModel.translationState.collect { renderTranslation(it) } }
            }
        }
    }

    private fun updatePhraseList(phrases: List<EmergencyPhrase>) {
        phraseAdapter.submitList(phrases)
        binding.emptyView.visibility = if (phrases.isEmpty()) View.VISIBLE else View.GONE
        binding.phrasesRecyclerView.visibility = if (phrases.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun renderTranslation(state: UiState<TranslationResult>) {
        when (state) {
            is UiState.Idle -> {
                binding.translationResultCard.visibility = View.GONE
                binding.translationProgressBar.visibility = View.GONE
            }
            is UiState.Loading -> {
                binding.translationProgressBar.visibility = View.VISIBLE
                binding.translationResultCard.visibility = View.GONE
            }
            is UiState.Success -> {
                binding.translationProgressBar.visibility = View.GONE
                binding.translationResultCard.visibility = View.VISIBLE
                binding.originalPhraseText.text = state.data.originalText
                binding.translatedPhraseText.text = state.data.translatedText
                binding.translatedPhraseText.textDirection =
                    if (state.data.targetLanguage.isRtl) View.TEXT_DIRECTION_RTL
                    else View.TEXT_DIRECTION_LTR
                speakResult(state.data)
            }
            is UiState.Error -> {
                binding.translationProgressBar.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun speakResult(result: TranslationResult) {
        // Try cached audio file first (pre-generated WAV), then fall back to live TTS
        if (result.phraseId > 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                val file = audioRepository.getCachedFile(result.phraseId, result.targetLanguage)
                if (file != null) {
                    audioRepository.playFile(file)
                } else {
                    speakWithTts(result)
                }
            }
        } else {
            speakWithTts(result)
        }
    }

    private fun speakWithTts(result: TranslationResult) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        ttsManager.shutdown()
        audioRepository.release()
        _binding = null
    }
}
