package com.emergency.translator.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emergency.translator.data.repository.PhraseRepository
import com.emergency.translator.data.repository.TranslationRepository
import com.emergency.translator.domain.model.Language
import com.emergency.translator.domain.model.TranslationResult
import com.emergency.translator.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val translationRepository: TranslationRepository,
    private val phraseRepository: PhraseRepository
) : ViewModel() {

    private val _translationState = MutableStateFlow<UiState<TranslationResult>>(UiState.Idle)
    val translationState: StateFlow<UiState<TranslationResult>> = _translationState.asStateFlow()

    private val _selectedTargetLanguage = MutableStateFlow(Language.ENGLISH)
    val selectedTargetLanguage: StateFlow<Language> = _selectedTargetLanguage.asStateFlow()

    fun setTargetLanguage(language: Language) {
        _selectedTargetLanguage.value = language
    }

    fun translate(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            _translationState.value = UiState.Loading
            val result = translationRepository.translate(
                text = text.trim(),
                targetLanguage = _selectedTargetLanguage.value
            )
            _translationState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Translation failed.") }
            )
        }
    }

    fun quickAction(englishPhrase: String) {
        viewModelScope.launch {
            _translationState.value = UiState.Loading
            val target = _selectedTargetLanguage.value
            val phrase = phraseRepository.findPhraseByEnglish(englishPhrase)
            if (phrase != null) {
                _translationState.value = UiState.Success(
                    TranslationResult(
                        originalText = phrase.englishText,
                        translatedText = phrase.getTextForLanguage(target),
                        detectedSourceLanguage = Language.ENGLISH.displayName,
                        targetLanguage = target,
                        phraseId = phrase.id
                    )
                )
            } else {
                val result = translationRepository.translate(
                    text = englishPhrase,
                    targetLanguage = target,
                    sourceLanguage = Language.ENGLISH
                )
                _translationState.value = result.fold(
                    onSuccess = { UiState.Success(it) },
                    onFailure = { UiState.Error(it.message ?: "Translation failed.") }
                )
            }
        }
    }

    fun reset() {
        _translationState.value = UiState.Idle
    }
}
