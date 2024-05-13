package com.example.demoapp.ui.state

import com.example.demoapp.util.Language

sealed interface MainScreenEvent {

 data class OnLanguageCnage(val lang: Language) : MainScreenEvent


}