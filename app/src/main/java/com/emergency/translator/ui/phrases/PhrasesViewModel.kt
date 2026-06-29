package com.emergency.translator.ui.phrases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emergency.translator.data.repository.PhraseRepository
import com.emergency.translator.domain.model.EmergencyPhrase
import com.emergency.translator.domain.model.Language
import com.emergency.translator.domain.model.PhraseCategory
import com.emergency.translator.domain.model.TranslationResult
import com.emergency.translator.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhrasesViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository
) : ViewModel() {

    private val _translationState = MutableStateFlow<UiState<TranslationResult>>(UiState.Idle)
    val translationState: StateFlow<UiState<TranslationResult>> = _translationState.asStateFlow()

    private val _selectedLanguage = MutableStateFlow(Language.ARABIC)
    val selectedLanguage: StateFlow<Language> = _selectedLanguage.asStateFlow()

    private val _currentCategory = MutableStateFlow<PhraseCategory?>(null)
    val currentCategory: StateFlow<PhraseCategory?> = _currentCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _showFavoritesOnly = MutableStateFlow(false)
    val showFavoritesOnly: StateFlow<Boolean> = _showFavoritesOnly.asStateFlow()

    /**
     * Filtered phrase list — derived from the Room DB stream plus in-memory filters.
     * Survives configuration changes because it lives in the ViewModel.
     * WhileSubscribed(5_000) keeps the upstream alive across brief lifecycle gaps
     * (e.g. screen rotation), preventing a cold restart of the DB query.
     */
    val phrases: StateFlow<List<EmergencyPhrase>> = combine(
        phraseRepository.allPhrases,
        _currentCategory,
        _searchQuery,
        _showFavoritesOnly
    ) { all, category, query, favOnly ->
        all.filter { phrase ->
            val matchesCategory = category == null || phrase.category == category
            val matchesQuery   = phrase.matchesSearch(query)
            val matchesFav     = !favOnly || phrase.isFavorite
            matchesCategory && matchesQuery && matchesFav
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun setLanguage(language: Language) {
        _selectedLanguage.value = language
        _translationState.value = UiState.Idle
    }

    fun setCategory(category: PhraseCategory?) {
        _currentCategory.value = category
        _showFavoritesOnly.value = false
    }

    fun setShowFavoritesOnly(show: Boolean) {
        _showFavoritesOnly.value = show
        if (show) _currentCategory.value = null
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun translatePhrase(phrase: EmergencyPhrase) {
        val target = _selectedLanguage.value
        _translationState.value = UiState.Success(
            TranslationResult(
                originalText = phrase.englishText,
                translatedText = phrase.getTextForLanguage(target),
                detectedSourceLanguage = Language.ENGLISH.displayName,
                targetLanguage = target,
                phraseId = phrase.id
            )
        )
    }

    fun toggleFavorite(phrase: EmergencyPhrase) {
        viewModelScope.launch {
            phraseRepository.toggleFavorite(phrase.id, !phrase.isFavorite)
        }
    }

    fun dismissTranslation() {
        _translationState.value = UiState.Idle
    }

}
