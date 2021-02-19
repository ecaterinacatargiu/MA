package com.example.lab1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab1.model.Exam
import com.example.lab1.model.ExamBackup

@Dao
interface ExamBackupDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExam(exam: ExamBackup)

    @Query("SELECT * FROM examBackup ORDER BY id ASC")
    fun readData() : LiveData<List<ExamBackup>>

    @Delete
    suspend fun deleteBook(book: ExamBackup)

    /*@Query("SELECT * FROM examBackup WHERE id LIKE '% :id %'")
    fun getExam(id: Int) : LiveData<List<ExamBackup>>

    @Query("SELECT * FROM examBackup WHERE status LIKE 'draft' ")
    fun getDraft() : LiveData<List<ExamBackup>>

    @Query("SELECT * FROM examBackup WHERE status LIKE '% :group %' ")
    fun getByGroup(group: String) : LiveData<List<ExamBackup>>*/
}