package com.example.facts_usecase_module.data.repository

import android.util.Log
import com.example.facts_usecase_module.data.coms.AppComs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AppComsImpl @Inject constructor(

) : AppComs {
    private lateinit var _stringFlow : Flow<String>

    override suspend fun startCommunication(myFlow: Flow<String>) {
        Log.v("AppComsImpl", "AppComsImpl start")
        delay(2000)
        _stringFlow = myFlow
        delay(2000)
        getResultFromApp()
    }

    override suspend fun getResultFromApp() {
        _stringFlow.collect {
            Log.v("AppComsImpl", "AppComsImpl result: $it")
        }
    }

    override fun sendResultToApp() {
        TODO("Not yet implemented")
    }
}