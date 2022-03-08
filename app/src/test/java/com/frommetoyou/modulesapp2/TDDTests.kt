package com.frommetoyou.modulesapp2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.frommetoyou.modulesapp2.data.repository.FactsRepositoryImpl
import com.frommetoyou.modulesapp2.data.services.api.FactsApiService
import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.modulesapp2.domain.repository.FactsRepository
import com.frommetoyou.modulesapp2.domain.usecases.CallApiUseCase
import com.frommetoyou.modulesapp2.presentation.ui.reducer.TDDReducer
import com.frommetoyou.modulesapp2.presentation.ui.state.TDDViewState
import com.frommetoyou.modulesapp2.presentation.viewmodel.TDDViewModel
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
import retrofit2.Response

/**
 * En la UI yo quiero:
 * -Tocar un boton para llamar a la API
 *      - Se mostrará un loading al comenzar
 * -Visualizar los resultados de la API
 *      - Se eliminará el login al finalizar
 *
 *
 * */
@ExperimentalCoroutinesApi
class TDDTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var coroutinesDispatchers: CoroutinesDispatcherProvider
    private lateinit var viewModel: TDDViewModel
    private lateinit var viewState: TDDViewState
    private lateinit var reducer: TDDReducer
    private lateinit var callApiUseCase: CallApiUseCase
    private lateinit var repository: FactsRepository
    private lateinit var factsApiService: FactsApiService

    @Before
    fun setup() {
        coroutinesDispatchers = provideFakeCoroutinesDispatcherProvider()
        Dispatchers.setMain(coroutinesDispatchers.io)
        factsApiService = mock()
        repository = FactsRepositoryImpl(factsApiService)
        callApiUseCase = CallApiUseCase(repository)
        reducer = TDDReducer(callApiUseCase)
        viewState = TDDViewState()
        viewModel = TDDViewModel(reducer)
    }

    @Test
    fun callAPIOnBtnClick() = runBlockingTest {
        whenever(factsApiService.getFacts()).thenReturn(Response.success(listOf()))
        viewModel.callAPI()
        viewModel.viewState.test {
            Assert.assertEquals(
                viewState.copy(loading = true), // resultado esperado
                awaitItem() // resultado actual
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
