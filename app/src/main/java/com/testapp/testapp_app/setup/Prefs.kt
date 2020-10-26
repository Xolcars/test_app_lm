package com.testapp.testapp_app.setup

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.models.BeerListResponse
import org.koin.android.BuildConfig

class Prefs(context: Context) {
    //region Vars
    private val FILENAME = "${BuildConfig.APPLICATION_ID}.prefs"

    private val TOKEN = "TOKEN"

    private val LAST_DAY_CONNECTED = "LAST_DAY_CONNECTED"
    private val FAVORITES_JSON = "FAVORITES_JSON"
    //endregion

    private val prefs: SharedPreferences =
        context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

    //region UserPrefs
    var token: String?
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()
    //endregion

    //region OtherPrefs
    var lastDayConnected: String?
        get() = prefs.getString(LAST_DAY_CONNECTED, "")
        set(value) = prefs.edit().putString(LAST_DAY_CONNECTED, value).apply()

    var favoritesListJson: String?
        get() = prefs.getString(FAVORITES_JSON, "{data:[]}")
        set(value) = prefs.edit().putString(FAVORITES_JSON, value).apply()
    //endregion

    //region Methods
    fun addToFavs(itemToAdd: BeerBean) {
        val objectSaved = Gson().fromJson(favoritesListJson, BeerListResponse::class.java)
        objectSaved.data?.add(itemToAdd)

        val jsonNew = Gson().toJson(objectSaved)
        favoritesListJson = jsonNew
    }
    fun removeToFavs(itemToRemove: BeerBean) {
        val objectSaved = Gson().fromJson(favoritesListJson, BeerListResponse::class.java)
        objectSaved.data?.remove(itemToRemove)

        val jsonNew = Gson().toJson(objectSaved)
        favoritesListJson = jsonNew
    }
    //endregion Methods

    //region Clear and remove Prefs
    fun clear(): Boolean {
        return prefs.edit().clear().commit()
    }

    private fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
    //endregion
}