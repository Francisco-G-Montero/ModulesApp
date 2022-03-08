package com.frommetoyou.modulesapp2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.modulesapp2.presentation.redux.Store
import com.frommetoyou.modulesapp2.presentation.ui.action.TDDAction
import com.frommetoyou.modulesapp2.presentation.ui.reducer.TDDReducer
import com.frommetoyou.modulesapp2.presentation.ui.state.TDDViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TDDViewModel @Inject constructor(
    private val tddReducer: TDDReducer
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
}
