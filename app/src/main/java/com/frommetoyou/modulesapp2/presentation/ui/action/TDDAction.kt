package com.frommetoyou.modulesapp2.presentation.ui.action

import android.app.Activity
import com.frommetoyou.modulesapp2.presentation.redux.Action

/**
 * Todas las acciones posibles
 * */
sealed class MainAction : Action {
    data class OnSaveClicked(val text: String) : MainAction()
    object OnGetClicked : MainAction()
    data class OnJetpackSaveClicked(val text: String) : MainAction()
    object OnJetpackGetClicked : MainAction()
    data class CheckForUpdates(val activity: Activity) : MainAction()
    data class GenerateLink(val btnSelected: String, val activity: Activity) : MainAction()
    data class GetBtnSelectedLinkData(val activity: Activity) : MainAction()
    data class ShowLoading(val show: Boolean) : MainAction()
    data class OnError(val message: String) : MainAction()
}