package com.example.lab1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab1.model.EntityBackup

@Dao
interface EntityBackupDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: EntityBackup)

    @Query("SELECT * FROM entityBackup ORDER BY id ASC")
    fun readData() : LiveData<List<EntityBackup>>

    @Delete
    suspend fun delete(entity: EntityBackup)
}