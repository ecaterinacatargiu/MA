package com.example.lab1.repository

import androidx.lifecycle.LiveData
import com.example.lab1.data.BookDAO
import com.example.lab1.model.Book

class BookRepository(private val bookDAO : BookDAO) {

    val getAllBooks: LiveData<List<Book>> = bookDAO.readData()

    suspend fun addBook(book: Book) {
        bookDAO.addBook(book)
    }

    suspend fun updateBook(book: Book){
        bookDAO.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDAO.deleteBook(book)
    }
}