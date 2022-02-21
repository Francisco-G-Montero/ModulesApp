package com.frommetoyou.modulesapp2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frommetoyou.modulesapp2.domain.utils.ImageFlowUtil
import com.frommetoyou.modulesapp2.presentation.redux.Store
import com.frommetoyou.modulesapp2.presentation.ui.action.MainAction
import com.frommetoyou.modulesapp2.presentation.ui.action.WorkManagerAction
import com.frommetoyou.modulesapp2.presentation.ui.reducer.WorkManagerReducer
import com.frommetoyou.modulesapp2.presentation.ui.state.WorkManagerViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WorkManagerViewModel @Inject constructor(
    private val workManagerReducer: WorkManagerReducer,
    private val imageFlowUtil: ImageFlowUtil
) : ViewModel() {
    private val store = Store(
        initialState = WorkManagerViewState(),
        reducer = workManagerReducer
    )

    val imgFilePathList: LiveData<List<String>>
        get() = imageFlowUtil.getImgPathList()

    val viewState: StateFlow<WorkManagerViewState> = store.state

    fun checkForStoredImgs() {
        val action = WorkManagerAction.OnCheckStoredImgAction
        store.dispatch(action)
    }

    fun onDownloadClicked() {
        val action = WorkManagerAction.OnDownloadClicked
        store.dispatch(action)
    }
}