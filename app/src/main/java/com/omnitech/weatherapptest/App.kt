package com.omnitech.weatherapptest

import android.app.Application
import android.util.Log
import com.omnitech.weatherapptest.di.appModule
import com.omnitech.weatherapptest.utils.EncryptionUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
class App : Application(){
    override fun onCreate() {
        super.onCreate()


        // Initialize Koin
        startKoin {
            androidContext(this@App)
            modules(appModule) // Pass the Koin module
        }
    }
}
