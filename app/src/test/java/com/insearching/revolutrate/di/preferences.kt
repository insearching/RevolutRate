package com.insearching.revolutrate.di

import android.content.SharedPreferences
import com.insearching.revolutrate.data.prefs.SharedPrefsHelper
import org.koin.dsl.module
import org.mockito.Mockito.mock

val KoinMockPreferencesModule = module {
    single { mock(SharedPreferences::class.java) }
    single { mock(SharedPrefsHelper::class.java) }
}