package com.frommetoyou.modulesapp2.data.extensions

import com.frommetoyou.modulesapp2.data.util.ErrorResponse
import com.frommetoyou.modulesapp2.data.util.Result
import com.google.gson.Gson
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

private const val UNKNOWN_ERROR = "Unknown API error"
private const val DEFAULT_ERROR = "{ detail: \"Something has gone wrong on API call\"}"
private const val DEFAULT_CODE_ERROR = 500

fun <T> Response<T>.parseResponse(): Result<T> {
    val responseBody = body()
    return if (responseBody != null)
        try {
            Result.Success(responseBody)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Response body parsing error")
        }
    else getErrorResponse(errorBody())
}

fun getErrorResponse(errorBody: ResponseBody?): Result.Error {
    try {
        val errorMessage =
            if (errorBody != null)
                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java).message
            else UNKNOWN_ERROR
        return Result.Error(errorMessage)
    } catch (e: java.lang.Exception) {
        return Result.Error(e.message ?: UNKNOWN_ERROR)
    }
}

fun <T> Throwable.getResponseError(): Response<T> {
    return Response.error(DEFAULT_CODE_ERROR, DEFAULT_ERROR.toResponseBody(null))
}
