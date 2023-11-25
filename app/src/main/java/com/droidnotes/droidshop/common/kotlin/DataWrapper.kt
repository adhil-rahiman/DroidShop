package com.droidnotes.droidshop.common.kotlin

sealed class DataWrapper<out T> {
    data class Success<out T>(val data: T) : DataWrapper<T>()
    data class GenericError(val code: Int? = null, val errorMessage: String? = null) :
        DataWrapper<Nothing>()
    object NetworkError : DataWrapper<Nothing>()
    object Loading : DataWrapper<Nothing>()
}