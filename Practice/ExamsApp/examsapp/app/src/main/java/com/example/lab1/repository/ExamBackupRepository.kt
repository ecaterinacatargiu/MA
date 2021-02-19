package com.example.lab1.repository

import androidx.lifecycle.LiveData
import com.example.lab1.data.ExamBackupDAO
import com.example.lab1.model.ExamBackup

class ExamBackupRepository(private val examDAO : ExamBackupDAO) {

    val getFromBackup: LiveData<List<ExamBackup>> = examDAO.readData()


    suspend fun addBook(book: ExamBackup) {
        examDAO.addExam(book)
    }

    suspend fun deleteBook(book: ExamBackup)
    {
        examDAO.deleteBook(book)
    }
}