package com.testapp.testapp_app.setup.di.modules

import com.testapp.testapp_app.setup.network.ApiRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repositoryModule = module {
    factory { ApiRepository(get(), androidContext()) }
}