package com.frommetoyou.modulesapp2.presentation.ui.reducer

import com.frommetoyou.modulesapp2.domain.usecases.GetStoredImgUseCase
import com.frommetoyou.modulesapp2.domain.usecases.WorkManagerUseCase
import com.frommetoyou.modulesapp2.presentation.redux.Reducer
import com.frommetoyou.modulesapp2.presentation.ui.action.WorkManagerAction
import com.frommetoyou.modulesapp2.presentation.ui.state.WorkManagerViewState
import javax.inject.Inject

class WorkManagerReducer @Inject constructor(
    private val workManagerUseCase: WorkManagerUseCase,
    private val getStoredImgUseCase: GetStoredImgUseCase
) : Reducer<WorkManagerViewState, WorkManagerAction> {
    override fun reduce(
        currentState: WorkManagerViewState,
        action: WorkManagerAction
    ): WorkManagerViewState {
        return when (action) {
            is WorkManagerAction.OnCheckStoredImgAction -> {
                getStoredImgUseCase.fetchImages()
                currentState
            }
            is WorkManagerAction.OnDownloadClicked -> {
                workManagerUseCase.startWorker()
                currentState
            }
            else -> currentState
        }
    }
}