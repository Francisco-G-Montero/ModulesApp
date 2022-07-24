package com.example.facts_usecase_module.data.coms

import kotlinx.coroutines.flow.Flow

interface AppComs {
    suspend fun startCommunication(myFlow: Flow<String>)
    suspend fun getResultFromApp()
    fun sendResultToApp()
}