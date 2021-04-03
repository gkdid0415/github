package com.example.github.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    fun getClient(baseUrl: String) : Retrofit? = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}