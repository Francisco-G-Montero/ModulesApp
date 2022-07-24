package com.example.facts_usecase_module.domain.usecases

import com.example.core.data.model.Facts
import com.example.facts_usecase_module.data.repository.FactsRepositoryImpl
import com.example.core.data.util.NetResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CallFactsApiUseCase @Inject constructor(
    val repository: FactsRepositoryImpl
) {
    suspend operator fun invoke(): Flow<NetResult<Facts>> = repository.getFacts()
}
