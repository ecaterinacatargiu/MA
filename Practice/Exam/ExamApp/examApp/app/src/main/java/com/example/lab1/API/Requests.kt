package com.example.lab1.API

import com.example.lab1.model.Entity
import com.example.lab1.model.Idu
import retrofit2.Call
import retrofit2.http.*

interface Requests {

    @GET("spaces/")
    fun getEntities(): Call<List<Entity>>

    @POST("space/")
    fun addEntity(@Body entity: Entity): Call<Entity>

    @DELETE("space/{id}")
    fun deleteBook(@Path("id") id: Int): Call<Entity>

    @POST("take/")
    fun take(@Body id: Idu): Call<Entity>

    @GET("free/")
    fun getFree(): Call<List<Entity>>

}