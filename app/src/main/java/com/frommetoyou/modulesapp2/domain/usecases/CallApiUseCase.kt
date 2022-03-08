package com.frommetoyou.modulesapp2.domain.usecases

import com.frommetoyou.modulesapp2.domain.repository.FactsRepository
import javax.inject.Inject

class CallApiUseCase @Inject constructor(
    val repository: FactsRepository
) {
    suspend operator fun invoke() = repository.getFacts()
}
