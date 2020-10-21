package com.testapp.testapp_app.setup.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.testapp.testapp_app.setup.extensions.logApi
import com.testapp.testapp_app.setup.network.ApiInterceptor
import com.testapp.testapp_app.setup.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    factory {
        HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                when {
                    //Filter trash logs
                    message.contains("Content-Type: application/x-www-form-urlencoded") -> print("")
                    message.contains("Content-Length:") -> print("")
                    message.contains("content-type:") -> print("")
                    message.contains("content-length:") -> print("")
                    message.contains("server:") -> print("")
                    message.contains("date:") -> print("")
                    message.contains("Date:") -> print("")
                    message.contains("Content-Type:") -> print("")
                    message.contains("Connection:") -> print("")
                    message.contains("Server:") -> print("")
                    message.contains("Set-Cookie:") -> print("")
                    message.contains("set-cookie:") -> print("")
                    message.contains("Access-Control") -> print("")
                    message.contains("access-control-allow-methods:") -> print("")
                    message.contains("access-control-max-age:") -> print("")
                    message.contains("access-control-allow-headers:") -> print("")
                    message.contains("access-control-allow-origin:") -> print("")
                    message.contains("Accept: application/json") -> print("")
                    message.contains("Accept: application/json") -> print("")
                    message.contains("Transfer-Encoding:") -> print("")
                    message.contains("transfer-encoding:") -> print("")
                    message.contains("--> END") -> print("")
                    message.contains("<-- END") -> print("")
                    message.contains("x-powered-by") -> print("")
                    message.contains("X-Powered-By") -> print("")
                    message.isEmpty() -> {print("")}
                    //show valid logs
                    else -> logApi(message)
                }
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    factory { ApiInterceptor(prefs = get()) }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<ApiInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>()).build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(com.testapp.testapp_app.BuildConfig.BASE_URL_APP)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { get<Retrofit>().create<ApiService>() }
}