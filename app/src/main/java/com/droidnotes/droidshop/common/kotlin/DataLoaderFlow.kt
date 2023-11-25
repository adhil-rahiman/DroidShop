package com.droidnotes.droidshop.common.kotlin

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> dataLoaderFlow(
    dispatcher: CoroutineDispatcher,
    executable: suspend () -> DataWrapper<T>
): Flow<DataWrapper<T>> {
    return flow {
        emit(DataWrapper.Loading)
        emit(executable())
    }.flowOn(dispatcher)
}

suspend fun <T : Any> loadData(
    dispatcher: CoroutineDispatcher,
    getData: suspend () -> DataWrapper<T>,
    processData: suspend (DataWrapper<T>) -> Unit
) {
    dataLoaderFlow(dispatcher) {
        getData()
    }.collect {
        processData(it)
    }
}