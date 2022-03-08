package com.frommetoyou.modulesapp2.data.repository

import com.frommetoyou.modulesapp2.data.extensions.getResponseError
import com.frommetoyou.modulesapp2.data.extensions.parseResponse
import com.frommetoyou.modulesapp2.data.model.Facts
import com.frommetoyou.modulesapp2.data.services.api.FactsApiService
import com.frommetoyou.modulesapp2.data.util.Result
import com.frommetoyou.modulesapp2.domain.repository.FactsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FactsRepositoryImpl @Inject constructor(
    private val factsApiService: FactsApiService,
) : FactsRepository {
    override fun getFacts(): Flow<Result<List<Facts>>> = flow {
        emit(factsApiService.getFacts())
    }
        .catch { error -> emit(error.getResponseError()) }
        .map { result -> result.parseResponse() }
}
