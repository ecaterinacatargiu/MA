package com.example.lab1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lab1.data.ExamDatabase
import com.example.lab1.model.Exam
import com.example.lab1.model.ExamBackup
import com.example.lab1.repository.ExamBackupRepository
import com.example.lab1.repository.ExamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExamViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Exam>>
    private val repository: ExamRepository

    val bookBackupDAO = ExamDatabase.getDatabase(getApplication()).examBackupDAO()

    private val backupRepository = ExamBackupRepository(bookBackupDAO)
    val readBackup : LiveData<List<ExamBackup>>


    init {
        val examDAO = ExamDatabase.getDatabase(
            application
        ).examDAO()

        repository = ExamRepository(examDAO)
        readAllData = repository.getAllExams
        readBackup = backupRepository.getFromBackup
    }

    fun addBook(exam: Exam)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExam(exam)
        }
    }

    fun addBookToBackUp(book: ExamBackup)
    {
        viewModelScope.launch(Dispatchers.IO) {
            backupRepository.addBook(book)
        }
    }

    fun deleteBookFromBackup(book: ExamBackup)
    {
        viewModelScope.launch(Dispatchers.IO) {
            backupRepository.deleteBook(book)
        }
    }

}