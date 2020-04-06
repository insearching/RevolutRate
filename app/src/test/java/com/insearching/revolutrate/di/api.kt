package com.insearching.revolutrate.di

import com.insearching.revolutrate.data.api.CountriesApi
import com.insearching.revolutrate.data.api.RevolutApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import org.mockito.Mockito
import retrofit2.Retrofit

val KoinMockApiModule = module {
    single { Mockito.mock(OkHttpClient::class.java) }
    single { Mockito.mock(Retrofit::class.java) }
    
    single { Mockito.mock(CountriesApi::class.java) }
    single { Mockito.mock(RevolutApi::class.java) }
}