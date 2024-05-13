package com.example.demoapp.ui.state

import androidx.compose.runtime.Immutable


@Immutable
data class MainScreenState(
    val isLoading: Boolean,
    val isError: Boolean,
    val title: String,
    val engTitle:String
) {

    companion object {
        fun init() = MainScreenState(
            true,
            false,
                "MY App",
            "My app"
        )
    }

}
