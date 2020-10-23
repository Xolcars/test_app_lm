package com.testapp.testapp_app.setup

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.BuildConfig

class Prefs(context: Context) {
    //region Vars
    private val FILENAME = "${BuildConfig.APPLICATION_ID}.prefs"

    private val TOKEN = "TOKEN"

    private val LAST_DAY_CONNECTED = "LAST_DAY_CONNECTED"
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
    //endregion

    //region Clear and remove Prefs
    fun clear(): Boolean {
        return prefs.edit().clear().commit()
    }

    private fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
    //endregion
}