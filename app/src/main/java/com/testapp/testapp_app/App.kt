package com.testapp.testapp_app

import android.app.Application
import com.testapp.testapp_app.setup.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class App: Application() {

    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION ---
    open fun configureDi() =
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(provideComponent())
        }

    // PUBLIC API ---
    open fun provideComponent() = appComponent
}