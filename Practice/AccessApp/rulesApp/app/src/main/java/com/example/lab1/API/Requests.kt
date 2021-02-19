package com.example.lab1.API

import com.example.lab1.model.Entity
import com.example.lab1.model.Idu
import retrofit2.Call
import retrofit2.http.*

interface Requests {

    @GET("rules/")
    fun getEntities(): Call<List<Entity>>//Call<ExamsListDTO>

    @POST("rule/")
    fun addEntity(@Body entity: Entity): Call<Entity>

    @GET("rule/{id}")
    fun getEntity(@Path("id") id: Int): Call<Entity>

    @POST("update/")
    fun updateEntity(@Body entity: Entity): Call<Entity>

    @GET("level/{level}")
    fun getLevel(@Path("level") level: Int):  Call<List<Entity>>

}