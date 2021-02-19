package com.example.lab1.repository

import androidx.lifecycle.LiveData
import com.example.lab1.data.ExamDAO
import com.example.lab1.model.Exam

class ExamRepository(private val examDAO : ExamDAO) {

    val getAllExams: LiveData<List<Exam>> = examDAO.readData()

    suspend fun addExam(exam: Exam) {
        examDAO.addExam(exam)
    }
}