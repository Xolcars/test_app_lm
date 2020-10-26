package com.testapp.testapp_app.features.beerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.setup.network.ApiRepository

class BeerDetailViewModel : ViewModel()  {
    //region Vars
    private var _beerItem = MutableLiveData<BeerBean>()
    val beerItem: LiveData<BeerBean> = _beerItem
    //endregion Vars

    //region Methods
    fun setBeerItem(beerBean: BeerBean) {
        _beerItem.value = beerBean
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerDetailViewModel::class.java.simpleName
    }
}