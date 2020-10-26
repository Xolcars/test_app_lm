package com.testapp.testapp_app.setup.network

import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.models.BeerListResponse
import com.testapp.testapp_app.models.BeerStyleListResponse
import com.testapp.testapp_app.models.RandomBeerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("styles")
    suspend fun getBeerStylesList(@Query("key" )key: String): Response<BeerStyleListResponse>

    @GET("beers")
    suspend fun getBeerListByStyle(@Query("key") key: String,
                            @Query("styleId") styleId: Int,
                            @Query("p") page: Int): Response<BeerListResponse>

    @GET("beer/random")
    suspend fun getRandomBeer(@Query("key" )key: String): Response<RandomBeerResponse>
}