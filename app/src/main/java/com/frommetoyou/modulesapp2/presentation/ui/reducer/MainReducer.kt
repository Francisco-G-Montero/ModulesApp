package com.frommetoyou.modulesapp2.presentation.ui.reducer

import android.util.Log
import com.frommetoyou.modulesapp2.presentation.redux.Reducer
import com.frommetoyou.modulesapp2.presentation.ui.action.MainAction
import com.frommetoyou.modulesapp2.presentation.ui.state.MainViewState
import com.frommetoyou.modulesapp2.domain.usecases.*
import com.frommetoyou.modulesapp2.domain.usecases.GetEncryptedTextUseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainReducer @Inject constructor(
    private val saveEncryptedTextUseCase: SaveEncryptedTextUseCase,
    private val getEncryptedTextUseCase: GetEncryptedTextUseCase,
    private val checkForUpdatesUseCase: CheckForUpdatesUseCase,
    private val generateLinkUseCase: GenerateLinkUseCase,
    private val getDynamicLinkUseCase: GetDynamicLinkUseCase,
    private val jetpackSaveUseCase: JetpackSaveUseCase
) : Reducer<MainViewState, MainAction> {
    override fun reduce(currentState: MainViewState, action: MainAction): MainViewState {
        Log.v(MainReducer::class.simpleName, "Processing action: $action")

        return when (action) {
            is MainAction.OnSaveClicked -> {
                stateWithSaveClicked(currentState, action)
            }
            is MainAction.OnGetClicked -> {
                stateWithGetClicked(currentState)
            }
            is MainAction.OnJetpackSaveClicked -> {
                stateWithJetpackSaveClicked(currentState, action)
            }
            is MainAction.OnJetpackGetClicked -> {
                stateWithJetpackGetClicked(currentState)
            }
            is MainAction.OnError -> {
                stateWithOnError(currentState, action)
            }
            is MainAction.CheckForUpdates -> {
                stateWithCheckForUpdates(action, currentState)
            }
            is MainAction.GenerateLink -> {
                generateLinkUseCase.generateSelectedBtnLink(action.btnSelected, action.activity)
                currentState.copy(
                    dynamicLinkCreated = true
                )
            }
            is MainAction.GetBtnSelectedLinkData -> {
                getDynamicLinkUseCase.getSelectedBtnIfAvailable(action.activity, action.callback)
                currentState
            }
            else -> currentState
        }
    }

    private fun stateWithCheckForUpdates(
        action: MainAction.CheckForUpdates,
        currentState: MainViewState
    ): MainViewState {
        checkForUpdatesUseCase.check(action.activity)
        return currentState
    }

    private fun stateWithSaveClicked(
        currentState: MainViewState,
        action: MainAction.OnSaveClicked
    ): MainViewState {
        saveEncryptedTextUseCase.save(action.text)
        return currentState.copy(
            storedText = ""
        )
    }

    private fun stateWithJetpackSaveClicked(
        currentState: MainViewState,
        action: MainAction.OnJetpackSaveClicked
    ): MainViewState {
        runBlocking {
            jetpackSaveUseCase.save(action.text)
        }
        return currentState.copy(
            storedText = ""
        )
    }

    private fun stateWithOnError(
        currentState: MainViewState,
        action: MainAction.OnError
    ) = currentState.copy(
        storedText = action.message
    )

    private fun stateWithGetClicked(
        currentState: MainViewState,
    ): MainViewState {
        val text = getEncryptedTextUseCase.getEncryptedText()
        return currentState.copy(
            storedText = text
        )
    }

    private fun stateWithJetpackGetClicked(
        currentState: MainViewState,
    ): MainViewState {
        return currentState.copy(
            getFlowText = jetpackSaveUseCase.getUserText()
        )
    }
}