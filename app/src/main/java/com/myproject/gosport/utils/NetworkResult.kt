package com.myproject.gosport.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Loading<T>(data: T? = null) : NetworkResult<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : NetworkResult<T>(data, throwable)
}
