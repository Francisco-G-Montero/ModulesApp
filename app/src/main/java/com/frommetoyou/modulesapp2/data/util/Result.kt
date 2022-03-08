package com.frommetoyou.modulesapp2.data.util

sealed class ActionResult<out T>{
    data class Success<out T>(val data: T) : ActionResult<T>()
    data class Error(val errorMessage: String) : ActionResult<Nothing>()
}