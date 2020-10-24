package com.testapp.testapp_app.setup.di.modules

import com.testapp.testapp_app.features.beerdetail.BeerDetailViewModel
import com.testapp.testapp_app.features.beerlist.BeerListViewModel
import com.testapp.testapp_app.features.stylesbeerlist.BeerStylesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BeerStylesListViewModel(get(), get()) }
    viewModel { BeerListViewModel(get()) }
    viewModel { BeerDetailViewModel(get()) }
}
