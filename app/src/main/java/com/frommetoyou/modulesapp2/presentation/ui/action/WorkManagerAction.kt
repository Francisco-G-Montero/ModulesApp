package com.frommetoyou.modulesapp2.presentation.ui.action

import com.frommetoyou.modulesapp2.presentation.redux.Action

/**
 * Todas las acciones posibles
 * */
sealed class WorkManagerAction : Action {
    object OnDownloadClicked : WorkManagerAction()
    object OnCheckStoredImgAction : WorkManagerAction()
    data class OnError(val message: String) : WorkManagerAction()
}
