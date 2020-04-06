package com.insearching.revolutrate.util

import com.insearching.revolutrate.di.KoinMockApiModule
import com.insearching.revolutrate.di.KoinMockArchitectureComponentViewModels
import com.insearching.revolutrate.di.KoinMockPreferencesModule
import com.insearching.revolutrate.di.KoinMockRepositoriesModule
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

open class AutoInjectKoinTest : AutoCloseKoinTest() {
    @Before
    fun beforeTestsInitKoinAndLiveStore() {
        startKoin {
            KoinMockArchitectureComponentViewModels
            KoinMockApiModule
            KoinMockRepositoriesModule
            KoinMockPreferencesModule
        }
    }
}