package com.example.lab1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lab1.data.EntityBackupDAO
import com.example.lab1.data.EntityDatabase
import com.example.lab1.model.Entity
import com.example.lab1.model.EntityBackup
import com.example.lab1.repository.EntityBackupRepository
import com.example.lab1.repository.EntityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntityViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Entity>>
    private val repository: EntityRepository

    val bookBackupDAO = EntityDatabase.getDatabase(getApplication()).entityBackupDAO()

    private val backupRepository = EntityBackupRepository(bookBackupDAO)
    val readBackup : LiveData<List<EntityBackup>>


    init {
        val examDAO = EntityDatabase.getDatabase(
            application
        ).entityDAO()

        repository = EntityRepository(examDAO)
        readAllData = repository.getAll
        readBackup = backupRepository.getFromBackup
    }

    fun add(entity : Entity)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(entity)
        }
    }

    fun addToBackUp(entity : EntityBackup)
    {
        viewModelScope.launch(Dispatchers.IO) {
            backupRepository.add(entity)
        }
    }

    fun deleteFromBackup(entity : EntityBackup)
    {
        viewModelScope.launch(Dispatchers.IO) {
            backupRepository.delete(entity)
        }
    }

    fun update(entity: Entity)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(entity)
        }
    }

}