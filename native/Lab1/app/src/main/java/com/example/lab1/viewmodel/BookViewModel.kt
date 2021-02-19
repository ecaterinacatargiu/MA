package com.example.lab1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lab1.data.BookDatabase
import com.example.lab1.repository.BookRepository
import com.example.lab1.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Book>>
    private val repository: BookRepository

    init {
        val bookDAO = BookDatabase.getDatabase(
            application
        ).bookDAO()
        repository = BookRepository(bookDAO)
        readAllData = repository.getAllBooks
    }

    fun addBook(book: Book)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
    }
}