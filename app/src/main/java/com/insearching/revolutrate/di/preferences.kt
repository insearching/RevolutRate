package com.insearching.revolutrate.di

import android.content.Context
import android.content.SharedPreferences
import com.insearching.revolutrate.RevoluteRateApp
import com.insearching.revolutrate.data.prefs.SharedPrefsHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val KoinPreferencesModule = module {
    single { provideSharedPreferences(androidApplication()) }
    single { SharedPrefsHelper(get()) }
}

fun provideSharedPreferences(applicationContext: Context): SharedPreferences =
    applicationContext.getSharedPreferences(
        RevoluteRateApp.SHARED_PREF_KEY,
        Context.MODE_PRIVATE
    )