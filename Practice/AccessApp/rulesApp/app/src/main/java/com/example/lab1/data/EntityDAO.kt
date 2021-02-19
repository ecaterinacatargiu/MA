package com.example.lab1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab1.model.Entity

@Dao
interface EntityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: Entity)

    @Query("SELECT * FROM entity ORDER BY id ASC")
    fun readData() : LiveData<List<com.example.lab1.model.Entity>>

    @Delete
    suspend fun delete(entity: com.example.lab1.model.Entity)

    @Update
    suspend fun update(entity: Entity)

}