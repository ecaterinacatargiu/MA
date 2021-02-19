package com.example.lab1.repository

import androidx.lifecycle.LiveData
import com.example.lab1.data.EntityBackupDAO
import com.example.lab1.model.EntityBackup

class EntityBackupRepository(private val entityBackupDAO: EntityBackupDAO) {

    val getFromBackup: LiveData<List<EntityBackup>> = entityBackupDAO.readData()


    suspend fun add(entityBackup: EntityBackup) {
        entityBackupDAO.add(entityBackup)
    }

    suspend fun delete(entityBackup: EntityBackup)
    {
        entityBackupDAO.delete(entityBackup)
    }
}