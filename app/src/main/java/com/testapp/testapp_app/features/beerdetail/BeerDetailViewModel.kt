package com.testapp.testapp_app.features.beerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.models.BeerListResponse
import com.testapp.testapp_app.setup.Prefs

class BeerDetailViewModel(private val prefs: Prefs) : ViewModel()  {
    //region Vars
    private var _beerItem = MutableLiveData<BeerBean>()
    val beerItem: LiveData<BeerBean> = _beerItem
    //endregion Vars

    //region Methods
    fun setBeerItem(beerBean: BeerBean) {
        _beerItem.value = beerBean
    }

    fun isBeerOnFavs(): Boolean {
        var isOnFavs = false
        val objectSaved = Gson().fromJson(prefs.favoritesListJson, BeerListResponse::class.java)
        objectSaved?.let {
            isOnFavs = it.data?.contains(_beerItem.value) ?: false
        }
        return isOnFavs
    }

    fun addBeerToFavs(beerBean: BeerBean) {
        prefs.addToFavs(beerBean)
    }

    fun removeBeerToFavs(beerBean: BeerBean) {
        prefs.removeToFavs(beerBean)
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerDetailViewModel::class.java.simpleName
    }
}