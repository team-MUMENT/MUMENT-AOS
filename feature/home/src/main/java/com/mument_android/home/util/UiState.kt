package com.mument_android.home.util

sealed class UiState {
    object Init : UiState()
    data class Success<T>(val data: T) : UiState()
    data class Loading(val isLoading: Boolean) : UiState()
    data class Empty(val isEmpty: Boolean) : UiState()
    data class Error(val error: String) : UiState()
}