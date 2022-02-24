package com.frommetoyou.modulesapp2.presentation.ui.reducer

import com.frommetoyou.modulesapp2.domain.usecases.GetStoredImgUseCase
import com.frommetoyou.modulesapp2.domain.usecases.WorkManagerUseCase
import com.frommetoyou.modulesapp2.presentation.redux.Reducer
import com.frommetoyou.modulesapp2.presentation.ui.action.WorkManagerAction
import com.frommetoyou.modulesapp2.presentation.ui.state.WorkManagerViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkManagerReducer @Inject constructor(
    private val workManagerUseCase: WorkManagerUseCase,
    private val getStoredImgUseCase: GetStoredImgUseCase
) : Reducer<WorkManagerViewState, WorkManagerAction> {
    override suspend fun reduce(
        currentState: WorkManagerViewState,
        action: WorkManagerAction
    ): Flow<WorkManagerViewState> = flow{
         when (action) {
            is WorkManagerAction.OnCheckStoredImgAction -> {
                getStoredImgUseCase.fetchImages()
                emit(currentState)
            }
            is WorkManagerAction.OnDownloadClicked -> {
                workManagerUseCase.startWorker()
                emit(currentState)
            }
            else -> {}
        }
    }
}