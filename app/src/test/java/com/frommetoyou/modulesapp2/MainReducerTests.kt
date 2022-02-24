package com.frommetoyou.modulesapp2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.modulesapp2.domain.usecases.*
import com.frommetoyou.modulesapp2.presentation.redux.Store
import com.frommetoyou.modulesapp2.presentation.ui.action.MainAction
import com.frommetoyou.modulesapp2.presentation.ui.reducer.MainReducer
import com.frommetoyou.modulesapp2.presentation.ui.state.MainViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainReducerTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mainReducer: MainReducer
    private lateinit var viewState: MainViewState
    private lateinit var coroutinesDispatchers: CoroutinesDispatcherProvider
    private lateinit var saveEncryptedTextUseCase: SaveEncryptedTextUseCase
    private lateinit var getEncryptedTextUseCase: GetEncryptedTextUseCase
    private lateinit var checkForUpdatesUseCase: CheckForUpdatesUseCase
    private lateinit var generateLinkUseCase: GenerateLinkUseCase
    private lateinit var getDynamicLinkDataUseCase: GetDynamicLinkDataUseCase
    private lateinit var jetpackSaveUseCase: JetpackSaveUseCase

    @Before
    fun setUp() {
        coroutinesDispatchers = provideFakeCoroutinesDispatcherProvider()
        Dispatchers.setMain(coroutinesDispatchers.io)
        saveEncryptedTextUseCase = mock()
        getEncryptedTextUseCase = mock()
        checkForUpdatesUseCase = mock()
        generateLinkUseCase = mock()
        getDynamicLinkDataUseCase = mock()
        jetpackSaveUseCase = mock()
        viewState = MainViewState()
        mainReducer = MainReducer(
            saveEncryptedTextUseCase,
            getEncryptedTextUseCase,
            checkForUpdatesUseCase,
            generateLinkUseCase,
            getDynamicLinkDataUseCase,
            jetpackSaveUseCase,
            coroutinesDispatchers
        )
    }

    @Test
    fun checkReducerGetClickActionStateEmition() = runBlockingTest {
        val text = "Test text returned!"
        val action = MainAction.OnGetClicked
        whenever(getEncryptedTextUseCase.getEncryptedText()).thenReturn(text)
        mainReducer.reduce(viewState, action).collect {
            Assert.assertEquals(
                viewState.copy(
                    storedText = text
                ),
                it
            )
        }
    }
}