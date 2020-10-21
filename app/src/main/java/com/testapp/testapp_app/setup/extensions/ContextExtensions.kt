package com.testapp.testapp_app.setup.extensions

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.testapp.testapp_app.setup.network.ResponseResult
import retrofit2.Response

fun Context.getJsonFromResource(res: Int): String {
    return resources.openRawResource(res)
        .bufferedReader().use { it.readText() }
}

inline fun <reified T : Any> T.json(): String = Gson().toJson(this, T::class.java)
inline fun <reified T : Any> String.fromJson(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T : Any> Context.getMockListResponse(raw: Int): Response<List<T>> {
    val json = this.getJsonFromResource(raw)
    val response: List<T> = Gson().fromJson(json, Array<T>::class.java).toList()
    return retrofit2.Response.success(response)
}

inline fun <reified T : Any> Context.getMockResponse(raw: Int): Response<T> {
    val json = this.getJsonFromResource(raw)
    val response: T = json.fromJson()
    return retrofit2.Response.success(response)
}

inline fun <reified T : Any> Context.getMockResult(raw: Int): T {
    val json = this.getJsonFromResource(raw)
    return json.fromJson()
}

inline fun <reified T : Any> Context.getMockResponseResult(raw: Int): ResponseResult.Success<T> {
    return ResponseResult.Success(this.getMockResult(raw))
}

fun Context.color(color: Int) = ContextCompat.getColor(this, color)

fun Context.toastShort(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toastLong(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()