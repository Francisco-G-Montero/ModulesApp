package com.example.core.data.util

sealed class NetResult<out T> {
    data class Success<out T>(val data: T) : NetResult<T>()
    data class Error(val errorMessage: String) : NetResult<Nothing>()
}
