package com.example.demoapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.demoapp.domain.usecase.GetUsersUseCase
import com.example.demoapp.ui.state.MainScreenEvent
import com.example.demoapp.ui.state.MainScreenState
import com.example.demoapp.util.Language
import com.example.demoapp.util.TranslateUtil
import com.example.demoapp.util.TranslationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsers: GetUsersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState.init())
    val uiState = _uiState.asStateFlow()
    private var selectedLanguage by mutableStateOf(Language.ENGLISH)

    private val translateUtil = TranslateUtil()

    private val users = getUsers().cachedIn(viewModelScope)

    val translateUser = snapshotFlow { selectedLanguage }.combine(users) { lang, userDate ->
        when(val text =translateUtil.translateText(uiState.value.engTitle,lang)){
            is TranslationResult.Error -> {updateState { copy(title = engTitle) }}
            is TranslationResult.Success -> { updateState { copy(title = text.translated ) } }
        }
        userDate.map { originalUser ->
            updateState { copy(isLoading = true) }
            when (val translate = translateUtil.translateUser(originalUser, lang)) {
                is TranslationResult.Error -> {
                    updateState { copy(isLoading = false) }
                    originalUser
                }

                is TranslationResult.Success -> {
                    updateState { copy(isLoading = false) }
                    translate.translated
                }
            }
        }
    }

    fun onEvent(event: MainScreenEvent) = viewModelScope.launch {
        when (event) {
            is MainScreenEvent.OnLanguageCnage -> selectedLanguage = event.lang
        }
    }


    private fun updateState(reducer: MainScreenState.() -> MainScreenState) =
        viewModelScope.launch {
            _uiState.update(reducer)
        }


}