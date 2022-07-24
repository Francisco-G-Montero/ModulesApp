package com.example.facts_usecase_module.data.repository

import com.example.core.data.model.Facts
import com.example.facts_usecase_module.data.extensions.getResponseError
import com.example.facts_usecase_module.data.extensions.parseResponse
import com.example.facts_usecase_module.data.services.api.FactsApiService
import com.example.core.data.util.NetResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FactsRepositoryImpl @Inject constructor(
    private val factsApiService: FactsApiService,
) {
    suspend fun getFacts(): Flow<NetResult<Facts>> = flow {
        emit(factsApiService.getFacts())
    }
        .catch { error -> emit(error.getResponseError()) }
        .map { result -> result.parseResponse() }
}
