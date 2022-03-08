package com.frommetoyou.modulesapp2.presentation.ui.reducer

import com.frommetoyou.modulesapp2.data.util.Result
import com.frommetoyou.modulesapp2.domain.usecases.CallApiUseCase
import com.frommetoyou.modulesapp2.presentation.redux.Reducer
import com.frommetoyou.modulesapp2.presentation.ui.action.TDDAction
import com.frommetoyou.modulesapp2.presentation.ui.state.TDDViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TDDReducer @Inject constructor(
    val callApiUseCase: CallApiUseCase
) : Reducer<TDDViewState, TDDAction> {
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
                        is Result.Success -> {
                            /*emit(
                                currentState.copy(
                                    loading = false,
                                    factsList = result.data
                                )
                            )*/
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
