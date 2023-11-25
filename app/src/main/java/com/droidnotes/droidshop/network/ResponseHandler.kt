package com.droidnotes.droidshop.network

import com.droidnotes.droidshop.common.kotlin.DataWrapper
import com.droidnotes.droidshop.common.kotlin.dataLoaderFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

private const val MESSAGE_KEY = "message"
private const val ERROR_KEY = "error"

suspend fun <T> apiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): DataWrapper<T> {
    return withContext(dispatcher) {
        try {
            DataWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DataWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorMsg = getErrorMessage(throwable)
                    DataWrapper.GenericError(code, errorMsg)
                }

                else -> {
                    DataWrapper.GenericError(null, null)
                }
            }
        }
    }
}

fun getErrorMessage(throwable: HttpException): String {
    return try {
        val responseBody = throwable.response()?.errorBody()
        val jsonObject = JSONObject(responseBody?.string() ?: "")
        when {
            jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
            jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
            else -> "Something went wrong"
        }
    } catch (e: Exception) {
        "Something went wrong"
    }
}