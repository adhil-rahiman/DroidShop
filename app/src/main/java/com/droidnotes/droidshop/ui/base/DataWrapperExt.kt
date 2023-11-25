package com.droidnotes.droidshop.ui.base

import com.droidnotes.droidshop.common.kotlin.DataWrapper

fun <T : Any, K : Any> DataWrapper<T>.mapToUiState(
    success: (data: T) -> K,
    loading: () -> UiState<K> = {
        UiState.Loading(LoadingType.FULL_LOADER)
    },
    error: (code: Int?, errorMessage: String?) -> UiState.Error = { code, error ->
        UiState.Error(error)
    }
): UiState<K> {
    return when (this) {
        is DataWrapper.Success -> {
            UiState.Success(success(data))
        }

        DataWrapper.Loading -> {
            loading()
        }

        is DataWrapper.GenericError -> {
            error(code, errorMessage)
        }

        DataWrapper.NetworkError -> {
            error(null, null)
        }
    }
}