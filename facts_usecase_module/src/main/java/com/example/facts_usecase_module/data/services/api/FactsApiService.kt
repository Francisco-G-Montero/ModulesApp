package com.example.facts_usecase_module.data.services.api

import com.example.core.data.model.Facts
import retrofit2.Response
import retrofit2.http.GET

interface FactsApiService {
    @GET("entries")
    suspend fun getFacts(): Response<Facts>
}
