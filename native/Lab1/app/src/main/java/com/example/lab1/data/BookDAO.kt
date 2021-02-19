package com.example.lab1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab1.model.Book

@Dao
interface BookDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book: Book)

    @Query("SELECT * FROM book ORDER BY id ASC")
    fun readData() : LiveData<List<Book>>

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}