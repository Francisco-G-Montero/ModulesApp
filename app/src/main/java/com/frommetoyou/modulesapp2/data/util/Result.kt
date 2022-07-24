package com.frommetoyou.modulesapp2.data.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
}
