package com.insearching.revolutrate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.insearching.revolutrate.data.prefs.SharedPrefsHelper
import com.insearching.revolutrate.data.repository.RatesRepository
import com.insearching.revolutrate.fake.FakeCountriesApi
import com.insearching.revolutrate.fake.FakeRevolutApi
import com.insearching.revolutrate.fake.FakeSharedPreferences
import com.insearching.revolutrate.ui.RatesViewModel
import com.insearching.revolutrate.ui.RatesViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class RatesViewModelE2ETest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        startKoin {
            modules(
                module {
                    single { FakeRevolutApi() }
                    single { FakeCountriesApi() }
                    single { SharedPrefsHelper(FakeSharedPreferences()) }
                    single { RatesRepository(get(), get(), get()) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `initial load emits rates with descriptions`() = dispatcher.runBlockingTest {
        val viewModel = RatesViewModel()
        val states = mutableListOf<RatesViewState>()
        val observer = { state: RatesViewState -> states.add(state) }
        viewModel.viewStates.observeForever(observer)

        // cancel repeating updater to avoid infinite loop
        viewModel.viewModelScope.cancel()

        advanceUntilIdle()

        assertTrue(states.isNotEmpty())
        val loaded = states.last()
        assertFalse(loaded.isLoading)
        assertEquals(3, loaded.rates.size)
        assertEquals("US dollar", loaded.rates[0].description)
        assertEquals("Euro", loaded.rates.first { it.title == "EUR" }.description)
        assertEquals("British Pound", loaded.rates.first { it.title == "GBP" }.description)
    }

    @Test
    fun `updating value recalculates rates`() = dispatcher.runBlockingTest {
        val viewModel = RatesViewModel()
        val states = mutableListOf<RatesViewState>()
        val observer = { state: RatesViewState -> states.add(state) }
        viewModel.viewStates.observeForever(observer)
        viewModel.viewModelScope.cancel()

        advanceUntilIdle()
        states.clear()

        viewModel.updateValue(2.0)
        advanceUntilIdle()

        val updated = states.last()
        assertEquals(2.0, updated.rates[0].value)
        assertEquals(1.8, updated.rates.first { it.title == "EUR" }.value)
        assertEquals(1.6, updated.rates.first { it.title == "GBP" }.value)
    }
}
