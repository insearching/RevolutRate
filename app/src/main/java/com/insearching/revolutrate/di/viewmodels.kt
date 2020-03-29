package com.insearching.revolutrate.di

import com.insearching.revolutrate.ui.RatesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val KoinArchitectureComponentViewModels = module {
    viewModel { RatesViewModel() }
}