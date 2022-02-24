package com.frommetoyou.modulesapp2.presentation.redux

import androidx.lifecycle.liveData
import com.frommetoyou.modulesapp2.presentation.ui.state.MainViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

/**
 * Es nuestro State container para una screen dada
 * @param [initialState] es el estado inicial de la screen en onCreate
 * @param [reducer] toma un state actial con una NUEVA action y devuelve un nuevo state
 * */
class Store<S : State, A : Action>(
    initialState: S,
    private val reducer: Reducer<S, A>
) {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    suspend fun dispatch(action: A) {
        val currentState = _state.value
        reducer.reduce(currentState, action).collect {
            _state.value = it
        }
    }
}