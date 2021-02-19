package com.example.lab1.API

import com.example.lab1.API.APIHelper.Companion.URL
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:2020/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Requests by lazy {
        retrofit.create(Requests::class.java)
    }
}