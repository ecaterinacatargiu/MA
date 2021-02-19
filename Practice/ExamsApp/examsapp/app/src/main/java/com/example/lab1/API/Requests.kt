package com.example.lab1.API

import com.example.lab1.model.Exam
import com.example.lab1.model.ExamsListDTO
import com.example.lab1.model.Idu
import retrofit2.Call
import retrofit2.http.*

interface Requests {

    @GET("exams/")
    fun getExams(): Call<List<Exam>>//Call<ExamsListDTO>

    @POST("exam/")
    fun addExam(@Body exam: Exam): Call<Exam>

    @GET("exam/{id}")
    fun getExam(@Path("id") id: Int): Call<Exam>

    @GET("draft/")
    fun getDrafts(): Call<List<Exam>>

    @POST("join")
    fun joinExam(@Body id: Idu): Call<Exam>

    @GET("group/{group}")
    fun getByGroup(@Path("group") group: String): Call<List<Exam>>
}