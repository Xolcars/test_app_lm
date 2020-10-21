package com.testapp.testapp_app.setup.di

import com.testapp.testapp_app.setup.di.modules.networkModule
import com.testapp.testapp_app.setup.di.modules.preferencesModule
import com.testapp.testapp_app.setup.di.modules.repositoryModule
import com.testapp.testapp_app.setup.di.modules.viewModelModule


val appComponent = listOf(networkModule, repositoryModule, preferencesModule, viewModelModule)