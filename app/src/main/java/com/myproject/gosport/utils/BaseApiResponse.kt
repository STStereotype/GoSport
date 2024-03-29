package com.myproject.gosport.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(NetworkResult.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { NetworkResult.Success(it) }
        } catch (throwable: Throwable) {
            query().map { NetworkResult.Error(throwable, it) }
        }
    } else {
        query().map { NetworkResult.Success(it) }
    }

    emitAll(flow)
}