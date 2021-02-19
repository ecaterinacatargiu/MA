package com.example.lab1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab1.model.Exam

@Dao
interface ExamDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExam(exam: Exam)

    @Query("SELECT * FROM exam ORDER BY id ASC")
    fun readData() : LiveData<List<Exam>>

    @Delete
    suspend fun deleteBook(book: Exam)

   /* @Query("SELECT * FROM exam WHERE id LIKE '% :id %'")
    fun getExam(id: Int) : LiveData<List<Exam>>

    @Query("SELECT * FROM exam WHERE status LIKE 'draft' ")
    fun getDraft() : LiveData<List<Exam>>

    @Query("SELECT * FROM exam WHERE status LIKE '% :group %' ")
    fun getByGroup(group: String) : LiveData<List<Exam>>*/

}