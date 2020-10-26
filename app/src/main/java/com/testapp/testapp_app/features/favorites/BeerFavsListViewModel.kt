package com.testapp.testapp_app.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.models.BeerListResponse
import com.testapp.testapp_app.setup.Prefs
import com.testapp.testapp_app.setup.network.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BeerFavsListViewModel(private val prefs: Prefs) : ViewModel() {
    //region Vars
    private var _beerList = MutableLiveData<MutableList<BeerBean>>()
    val beerList: LiveData<MutableList<BeerBean>> = _beerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty
    //endregion Vars

    //region Methods
    fun getFavoriteBeers() {
        _isLoading.value = true
        _isEmpty.value = false
        GlobalScope.launch(Dispatchers.Main) {
            val favoritesBeerResponse = Gson().fromJson(prefs.favoritesListJson, BeerListResponse::class.java)
            if(favoritesBeerResponse.data.isNullOrEmpty()) {
                _isEmpty.value = true
                _isLoading.value = false
                _beerList.value = ArrayList()
            } else {
                _isEmpty.value = false
                _isLoading.value = false
                _beerList.value = favoritesBeerResponse.data
            }
        }
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerFavsListViewModel::class.java.simpleName
    }
}