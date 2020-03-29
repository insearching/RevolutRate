package com.insearching.revolutrate.di

import com.insearching.revolutrate.data.repository.RatesRepository
import org.koin.dsl.module

val KoinRepositoriesModule = module {
    single { RatesRepository(get(), get(), get()) }
}