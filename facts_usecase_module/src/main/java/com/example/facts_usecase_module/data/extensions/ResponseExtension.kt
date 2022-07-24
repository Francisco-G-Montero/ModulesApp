package com.example.facts_usecase_module.data.extensions

import com.google.gson.Gson
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import com.example.core.data.util.NetResult
import com.example.facts_usecase_module.data.util.ErrorResponse

private const val UNKNOWN_ERROR = "Unknown API error"
private const val DEFAULT_ERROR = "{ detail: \"Something has gone wrong on API call\"}"
private const val DEFAULT_CODE_ERROR = 500

fun <T> Response<T>.parseResponse(): NetResult<T> {
    val responseBody = body()
    return if (responseBody != null)
        try {
            NetResult.Success(responseBody)
        } catch (e: Exception) {
            NetResult.Error(e.message ?: "Response body parsing error")
        }
    else getErrorResponse(errorBody())
}

fun getErrorResponse(errorBody: ResponseBody?): NetResult.Error {
    try {
        val errorMessage =
            if (errorBody != null)
                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java).message
            else UNKNOWN_ERROR
        return NetResult.Error(errorMessage)
    } catch (e: java.lang.Exception) {
        return NetResult.Error(e.message ?: UNKNOWN_ERROR)
    }
}

fun <T> Throwable.getResponseError(): Response<T> {
    return Response.error(DEFAULT_CODE_ERROR, DEFAULT_ERROR.toResponseBody(null))
}
