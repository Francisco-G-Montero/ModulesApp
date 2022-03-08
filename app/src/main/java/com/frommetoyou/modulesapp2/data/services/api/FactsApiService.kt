package com.frommetoyou.modulesapp2.data.model.services.api

import com.frommetoyou.modulesapp2.data.model.Facts
import retrofit2.Response
import retrofit2.http.GET

interface FactsApiService {
    @GET("entries")
    suspend fun getFacts(): Response<List<Facts>>
}