package com.frommetoyou.modulesapp2.presentation.ui.reducer

import com.frommetoyou.modulesapp2.data.util.ActionResult
import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.modulesapp2.presentation.redux.Reducer
import com.frommetoyou.modulesapp2.presentation.ui.action.MainAction
import com.frommetoyou.modulesapp2.presentation.ui.state.MainViewState
import com.frommetoyou.modulesapp2.domain.usecases.*
import com.frommetoyou.modulesapp2.domain.usecases.GetEncryptedTextUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainReducer @Inject constructor(
    private val saveEncryptedTextUseCase: SaveEncryptedTextUseCase,
    private val getEncryptedTextUseCase: GetEncryptedTextUseCase,
    private val checkForUpdatesUseCase: CheckForUpdatesUseCase,
    private val generateLinkUseCase: GenerateLinkUseCase,
    private val getDynamicLinkUseCase: GetDynamicLinkDataUseCase,
    private val jetpackSaveUseCase: JetpackSaveUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : Reducer<MainViewState, MainAction> {
    override suspend fun reduce(
        currentState: MainViewState,
        action: MainAction
    ): Flow<MainViewState> = flow {
    //    Log.v(MainReducer::class.simpleName, "Processing action: $action")
        when (action) {
            is MainAction.OnSaveClicked -> {
                stateWithSaveClicked(action, currentState)
            }
            is MainAction.OnGetClicked -> {
                stateWithGetClicked(currentState)
            }
            is MainAction.OnJetpackSaveClicked -> {
                stateWithJetpackSaveClicked(action, currentState)
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
                generateLinkWithState(action, currentState)
            }
            is MainAction.GetBtnSelectedLinkData -> {
                getBtnSelectedLinkDataWithState(action, currentState)
            }
            else -> {}
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun FlowCollector<MainViewState>.getBtnSelectedLinkDataWithState(
        action: MainAction.GetBtnSelectedLinkData,
        currentState: MainViewState
    ) {
        getDynamicLinkUseCase.getSelectedBtnDataIfAvailable(action.activity).collect {
            emit(
                currentState.copy(
                    dynamicLinkBtnData = it
                )
            )
        }
    }

    private suspend fun FlowCollector<MainViewState>.generateLinkWithState(
        action: MainAction.GenerateLink,
        currentState: MainViewState
    ) {
        withContext(coroutinesDispatcherProvider.main){
            generateLinkUseCase.generateSelectedBtnLink(action.btnSelected, action.activity)
        }
        emit(
            currentState.copy(
                dynamicLinkCreated = true
            )
        )
    }

    private suspend fun FlowCollector<MainViewState>.stateWithJetpackGetClicked(
        currentState: MainViewState
    ) {
        jetpackSaveUseCase.getUserText().collect { result ->
            when (result) {
                is ActionResult.Success -> {
                    emit(
                        currentState.copy(
                            getFlowText = result.data
                        )
                    )
                }
            }
        }
    }

    private suspend fun FlowCollector<MainViewState>.stateWithJetpackSaveClicked(
        action: MainAction.OnJetpackSaveClicked,
        currentState: MainViewState
    ) {
        jetpackSaveUseCase.save(action.text).collect { result ->
            when (result) {
                is ActionResult.Success -> {
                    emit(
                        currentState.copy(
                            storedText = ""
                        )
                    )
                }
            }
        }
    }

    private suspend fun FlowCollector<MainViewState>.stateWithGetClicked(
        currentState: MainViewState
    ) {
        val text = getEncryptedTextUseCase.getEncryptedText()
        emit(
            currentState.copy(
                storedText = text
            )
        )
    }

    private suspend fun FlowCollector<MainViewState>.stateWithSaveClicked(
        action: MainAction.OnSaveClicked,
        currentState: MainViewState
    ) {
        saveEncryptedTextUseCase.save(action.text)
        emit(
            currentState.copy(
                storedText = ""
            )
        )
    }

    private fun stateWithCheckForUpdates(
        action: MainAction.CheckForUpdates,
        currentState: MainViewState
    ): MainViewState {
        checkForUpdatesUseCase.check(action.activity)
        return currentState
    }

    private fun stateWithOnError(
        currentState: MainViewState,
        action: MainAction.OnError
    ) = currentState.copy(
        storedText = action.message
    )
}