package com.frommetoyou.modulesapp2.domain.repository

import com.frommetoyou.modulesapp2.data.model.Facts
import com.frommetoyou.modulesapp2.data.util.Result
import kotlinx.coroutines.flow.Flow

interface FactsRepository {
    fun getFacts(): Flow<Result<List<Facts>>>
}
