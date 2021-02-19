package com.example.lab1.API

import com.example.lab1.model.Entity
import com.example.lab1.model.Idu
import retrofit2.Call
import retrofit2.http.*

interface Requests {

    @GET("items/")
    fun getEntities(): Call<List<Entity>>

    @POST("item/")
    fun addEntity(@Body entity: Entity): Call<Entity>

    @DELETE("item/{id}")
    fun deleteBook(@Path("id") id: Int): Call<Entity>

    @POST("buy/")
    fun buy(@Body id: Idu): Call<Entity>

    @GET("bought/")
    fun getBought(): Call<List<Entity>>

}