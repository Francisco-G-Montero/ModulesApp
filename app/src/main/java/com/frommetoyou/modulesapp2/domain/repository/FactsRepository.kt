package com.frommetoyou.modulesapp2.domain.repository

import com.frommetoyou.modulesapp2.data.model.Facts
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getFacts(): Flow<List<Facts>>
}