package com.frommetoyou.modulesapp2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.modulesapp2.domain.usecases.CheckForUpdatesUseCase
import com.frommetoyou.modulesapp2.domain.usecases.GenerateLinkUseCase
import com.frommetoyou.modulesapp2.domain.usecases.GetDynamicLinkDataUseCase
import com.frommetoyou.modulesapp2.domain.usecases.GetEncryptedTextUseCase
import com.frommetoyou.modulesapp2.domain.usecases.JetpackSaveUseCase
import com.frommetoyou.modulesapp2.domain.usecases.SaveEncryptedTextUseCase
import com.frommetoyou.modulesapp2.presentation.redux.Store
import com.frommetoyou.modulesapp2.presentation.ui.action.MainAction
import com.frommetoyou.modulesapp2.presentation.ui.reducer.MainReducer
import com.frommetoyou.modulesapp2.presentation.ui.state.MainViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class MainViewModelStateTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    //  private lateinit var viewModel: MainViewModel
    private lateinit var store: Store<MainViewState, MainAction>
    private lateinit var mainReducer: MainReducer
    private lateinit var coroutinesDispatchers: CoroutinesDispatcherProvider
    private lateinit var saveEncryptedTextUseCase: SaveEncryptedTextUseCase
    private lateinit var getEncryptedTextUseCase: GetEncryptedTextUseCase
    private lateinit var checkForUpdatesUseCase: CheckForUpdatesUseCase
    private lateinit var generateLinkUseCase: GenerateLinkUseCase
    private lateinit var getDynamicLinkDataUseCase: GetDynamicLinkDataUseCase
    private lateinit var jetpackSaveUseCase: JetpackSaveUseCase
    private lateinit var viewState: MainViewState

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
        store = Store(viewState, mainReducer)
        // viewModel = MainViewModel()
    }

    // end to end scope chico?
    @Test
    fun checkRandomStateForChosenAction() = runBlockingTest {
        val text = "Test text returned!"
        val action = MainAction.OnGetClicked
        whenever(getEncryptedTextUseCase.getEncryptedText()).thenReturn(text)
        store.dispatch(action)
        store.state.test {
            Assert.assertEquals(awaitItem(), viewState.copy(storedText = text))
            cancelAndConsumeRemainingEvents()
        }
    }
}
