package com.testapp.testapp_app.setup.di.modules

import com.testapp.testapp_app.features.serviceslist.BeerStylesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BeerStylesListViewModel(get()) }
}