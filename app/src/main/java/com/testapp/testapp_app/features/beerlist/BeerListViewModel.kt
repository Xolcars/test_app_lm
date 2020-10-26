package com.testapp.testapp_app.features.beerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.setup.network.ApiRepository
import com.testapp.testapp_app.setup.network.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BeerListViewModel(private val repository: ApiRepository) : ViewModel() {
    //region Vars
    private var _beerList = MutableLiveData<MutableList<BeerBean>>()
    val beerList: LiveData<MutableList<BeerBean>> = _beerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean> = _onError

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty
    //endregion Vars

    //region Methods
    fun getBeersListByStyleRequest(styleSelected: Int, page: Int) {
        _isLoading.value = true
        _onError.value = false
        _isEmpty.value = false
        GlobalScope.launch(Dispatchers.Main) {
            when (val response = repository.getBeerListByStyle(
                styleId = styleSelected,
                page = page
            )) {
                is ResponseResult.Success -> {
                    if(response.value.data.isNullOrEmpty()) {
                        _isEmpty.value = true
                        _beerList.value = ArrayList()
                    } else {
                        _beerList.value = response.value.data
                    }
                    _isLoading.value = false
                }
                is ResponseResult.Error -> {
                    _onError.value = true
                    _isLoading.value = false
                }
                is ResponseResult.NotContent -> {
                    _isEmpty.value = true
                    _isLoading.value = false
                    _beerList.value = ArrayList()
                }
            }
        }
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerListViewModel::class.java.simpleName
    }
}