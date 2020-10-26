package com.testapp.testapp_app.features.stylesbeerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.models.BeerStyleBean
import com.testapp.testapp_app.setup.Prefs
import com.testapp.testapp_app.setup.network.ApiRepository
import com.testapp.testapp_app.setup.network.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BeerStylesListViewModel(private val repository: ApiRepository, private val prefs: Prefs): ViewModel() {
    //region Vars
    private var _beerStylesList = MutableLiveData<MutableList<BeerStyleBean>>()
    val beerStylesList: LiveData<MutableList<BeerStyleBean>> = _beerStylesList

    private var _randomBeer = MutableLiveData<BeerBean>()
    val randomBeer: LiveData<BeerBean> = _randomBeer
    private var _onErrorRandomBeer = MutableLiveData<Boolean>()
    val onErrorRandomBeer: LiveData<Boolean> = _onErrorRandomBeer

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean> = _onError

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty
    //endregion Vars

    //region Methods
    fun getBeerStylesListRequest() {
        _isLoading.value = true
        _onError.value = false
        _isEmpty.value = false
        GlobalScope.launch(Dispatchers.Main) {
            when(val response = repository.getBeerStylesList()) {
                is ResponseResult.Success -> {
                    _beerStylesList.value = response.value.data
                    _isLoading.value = false
                }
                is ResponseResult.Error -> {
                    _onError.value = true
                    _isLoading.value = false
                }
                is ResponseResult.NotContent -> {
                    _isEmpty.value = true
                    _isLoading.value = false
                    _beerStylesList.value = ArrayList()
                }
            }
        }
    }

    fun getRandomBeer() {
        prefs.lastDayConnected = getTodayDateAsString()
        GlobalScope.launch(Dispatchers.Main) {
            _onErrorRandomBeer.value = false
            when(val response = repository.getRandomBeer()) {
                is ResponseResult.Success -> {
                    _randomBeer.value = response.value.data
                }
                is ResponseResult.Error -> {
                    _onErrorRandomBeer.value = true
                }
                is ResponseResult.NotContent -> {
                }
            }
        }
    }

    private fun getTodayDateAsString(): String {
        val calendar = Calendar.getInstance()
        val date = calendar.time

        return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
    }

    fun needRequestRandomBeer(): Boolean {
        return !prefs.lastDayConnected.equals(getTodayDateAsString())
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerStylesListViewModel::class.java.simpleName
    }
}