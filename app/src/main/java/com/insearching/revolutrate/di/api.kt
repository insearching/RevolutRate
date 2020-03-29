package com.insearching.revolutrate.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.insearching.revolutrate.BuildConfig
import com.insearching.revolutrate.data.api.CountriesApi
import com.insearching.revolutrate.data.api.RevolutApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val KoinApiModule = module {
    single(definition = { getOkHttpClient() })
    single(definition = { getGson() })
    single(named("revolutApi")) { getRevouldRetrofit(get(), get()) }
    single(named("countriesApi")) { getCountriesRetrofit(get(), get()) }
    
    single { provideRevolutApi(get(named("revolutApi"))) }
    single { provideCountriesApi(get(named("countriesApi"))) }
}

fun provideRevolutApi(retrofit: Retrofit): RevolutApi = retrofit.create(RevolutApi::class.java)
fun provideCountriesApi(retrofit: Retrofit): CountriesApi = retrofit.create(CountriesApi::class.java)

fun getOkHttpClient(): OkHttpClient =
    with(OkHttpClient.Builder()) {
        connectTimeout(20, TimeUnit.SECONDS)
        readTimeout(20, TimeUnit.SECONDS)
        writeTimeout(20, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }
        return build()
    }

fun getGson(): Gson =
    GsonBuilder()
        .setLenient()
        .create()

fun getRevouldRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.REVOLUT_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

fun getCountriesRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.COUNTRIES_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()