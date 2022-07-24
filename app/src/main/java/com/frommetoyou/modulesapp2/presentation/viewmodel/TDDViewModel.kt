package com.frommetoyou.modulesapp2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facts_usecase_module.data.coms.AppComs
import com.frommetoyou.modulesapp2.presentation.redux.Store
import com.frommetoyou.modulesapp2.presentation.ui.action.TDDAction
import com.frommetoyou.modulesapp2.presentation.ui.reducer.TDDReducer
import com.frommetoyou.modulesapp2.presentation.ui.state.TDDViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TDDViewModel @Inject constructor(
    private val tddReducer: TDDReducer,
    private val appComs: AppComs
) : ViewModel() {
    private val store = Store(
        initialState = TDDViewState(),
        reducer = tddReducer
    )

    val viewState: StateFlow<TDDViewState> = store.state

    fun callAPI() = viewModelScope.launch {
        val action = TDDAction.OnCallApiClicked
        store.dispatch(action)
    }

    fun communicationWithCore() = viewModelScope.launch {
        val myFlow = flow {
            Log.v("AppComsImpl", "TDDViewModel is going to send a message")
            delay(2000)
            emit("Custom String message")
            delay(2000)
            emit("Custom String message 2")
            delay(2000)
            emit("Custom String message 3")
            delay(2000)
        }
        appComs.startCommunication(myFlow)
    }
}
