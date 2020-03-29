package com.insearching.revolutrate

import android.app.Application
import com.insearching.revolutrate.di.KoinApiModule
import com.insearching.revolutrate.di.KoinArchitectureComponentViewModels
import com.insearching.revolutrate.di.KoinPreferencesModule
import com.insearching.revolutrate.di.KoinRepositoriesModule
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RevoluteRateApp : Application() {
    
    companion object {
        const val SHARED_PREF_KEY = "RevolutRate"
    }
    
    override fun onCreate() {
        super.onCreate()
        
        initDependencyInjector()
        initPicasso()
    }
    
    private fun initPicasso() {
        Picasso.setSingletonInstance(
            Picasso.Builder(this)
                .build()
        )
    }
    
    private fun initDependencyInjector() {
        startKoin {
            androidContext(this@RevoluteRateApp)
            modules(
                KoinRepositoriesModule,
                KoinApiModule,
                KoinArchitectureComponentViewModels,
                KoinPreferencesModule
            )
        }
    }
}