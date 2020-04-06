package com.insearching.revolutrate.di

import com.insearching.revolutrate.data.repository.RatesRepository
import org.koin.dsl.module
import org.mockito.Mockito.mock

val KoinMockRepositoriesModule = module {
    single { mock(RatesRepository::class.java) }
}