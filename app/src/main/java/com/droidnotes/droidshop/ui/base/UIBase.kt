package com.droidnotes.droidshop.ui.base

interface UiEvent

sealed class UiState<out T> {
    data class Loading(val loadingType: LoadingType) : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String? = null) : UiState<Nothing>()
}

enum class LoadingType {
    NO_LOADER,
    FULL_LOADER
}