package com.testapp.testapp_app.setup.di.modules

import com.testapp.testapp_app.setup.Prefs
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val preferencesModule = module {
    factory { Prefs(androidApplication()) }
}