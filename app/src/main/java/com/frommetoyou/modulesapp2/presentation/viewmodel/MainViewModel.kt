package com.frommetoyou.modulesapp2.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.frommetoyou.modulesapp2.presentation.redux.Store
import com.frommetoyou.modulesapp2.presentation.ui.action.MainAction
import com.frommetoyou.modulesapp2.presentation.ui.reducer.MainReducer
import com.frommetoyou.modulesapp2.presentation.ui.state.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainReducer: MainReducer
) : ViewModel() {
    private val store = Store(
        initialState = MainViewState(),
        reducer = mainReducer
    )

    val viewState: StateFlow<MainViewState> = store.state

    fun onSaveClicked(text: String) {
        val action = MainAction.OnSaveClicked(text)
        store.dispatch(action)
    }

    fun onGetClicked() {
        val action = MainAction.OnGetClicked
        store.dispatch(action)
    }

    fun checkForUpdates(activity: Activity) {
        val action = MainAction.CheckForUpdates(activity)
        store.dispatch(action)
    }

    fun genLinkWithSelectedButton(btnSelected: String, activity: Activity) {
        val action = MainAction.GenerateLink(btnSelected, activity)
        store.dispatch(action)
    }

    fun getSelectedButtonLinkData(activity: Activity, callback: (String) -> Unit) {
        val action = MainAction.GetBtnSelectedLinkData(activity, callback)
        store.dispatch(action)
    }

    fun onJetpackSaveClicked(text: String) {
        val action = MainAction.OnJetpackSaveClicked(text)
        store.dispatch(action)
    }

    fun onJetpackGetClicked() {
        val action = MainAction.OnJetpackGetClicked
        store.dispatch(action)
    }
}