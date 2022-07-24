package com.frommetoyou.modulesapp2.presentation.ui.reducer

import com.example.core.data.util.NetResult
import com.example.facts_usecase_module.domain.usecases.CallFactsApiUseCase
import com.frommetoyou.modulesapp2.presentation.redux.Reducer
import com.frommetoyou.modulesapp2.presentation.ui.action.TDDAction
import com.frommetoyou.modulesapp2.presentation.ui.state.TDDViewState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TDDReducer @Inject constructor(
    val callApiUseCase: CallFactsApiUseCase
) : Reducer<TDDViewState, TDDAction> {
    @OptIn(InternalCoroutinesApi::class)
    override suspend fun reduce(
        currentState: TDDViewState,
        action: TDDAction
    ): Flow<TDDViewState> = flow {
        //    Log.v(MainReducer::class.simpleName, "Processing action: $action")
        when (action) {
            is TDDAction.OnCallApiClicked -> {
                emit(currentState.copy(loading = true))
                callApiUseCase().collect { result ->
                    when (result) {
                        is NetResult.Success -> {
                            emit(
                                currentState.copy(
                                    loading = false,
                                    factsList = result.data
                                )
                            )
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
