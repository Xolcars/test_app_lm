package com.testapp.testapp_app.setup.network

import android.content.Context
import com.google.gson.Gson
import com.testapp.testapp_app.BuildConfig
import com.testapp.testapp_app.R
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.models.BeerListResponse
import com.testapp.testapp_app.models.BeerStyleListResponse
import com.testapp.testapp_app.models.RandomBeerResponse
import com.testapp.testapp_app.setup.extensions.getJsonFromResource
import com.testapp.testapp_app.setup.network.NetworkExceptionController.checkException
import com.testapp.testapp_app.setup.network.NetworkExceptionController.checkResponse
import kotlinx.coroutines.delay

class ApiRepository(private val service: ApiService, private val context: Context) {
    val MOCK_DELAY = 1000L
    val apiKey = BuildConfig.API_KEY

    suspend fun getBeerStylesList(fake: Boolean = BuildConfig.MOCK): ResponseResult<BeerStyleListResponse> {
        return if (!fake) {
            try {
                val response = service.getBeerStylesList(apiKey)
                checkResponse(context, response)
            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            val json = context.getJsonFromResource(R.raw.beerstyleslist)
            val response: BeerStyleListResponse = Gson().fromJson(json, BeerStyleListResponse::class.java)
            ResponseResult.Success(response)
        }
    }

    suspend fun getRandomBeer(fake: Boolean = BuildConfig.MOCK): ResponseResult<RandomBeerResponse> {
        return if (!fake) {
            try {
                val response = service.getRandomBeer(apiKey)
                checkResponse(context, response)
            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            val json = context.getJsonFromResource(R.raw.randombeer)
            val response: RandomBeerResponse = Gson().fromJson(json, RandomBeerResponse::class.java)
            ResponseResult.Success(response)
        }
    }

    suspend fun getBeerListByStyle(fake: Boolean = BuildConfig.MOCK, styleId: Int, page: Int): ResponseResult<BeerListResponse> {
        return if (!fake) {
            try {
                val response = service.getBeerListByStyle(apiKey, styleId, page)
                checkResponse(context, response)
            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            val json = context.getJsonFromResource(R.raw.beerlist)
            val response: BeerListResponse = Gson().fromJson(json, BeerListResponse::class.java)
            ResponseResult.Success(response)
        }
    }
}