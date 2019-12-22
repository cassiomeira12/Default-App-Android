package com.android.app.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInitializer {

    private val URL_BASE = "http://192.168.0.128:3000/"

    val interceptor = HttpLoggingInterceptor()

    val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    fun users(): UserService {
        return retrofit.create(UserService::class.java)
    }
}