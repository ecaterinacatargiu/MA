package com.example.lab1.repository

import androidx.lifecycle.LiveData
import com.example.lab1.data.EntityDAO
import com.example.lab1.model.Entity

class EntityRepository(private val entityDAO : EntityDAO) {

    val getAll: LiveData<List<Entity>> = entityDAO.readData()

    suspend fun add(entity : Entity) {
        entityDAO.add(entity)
    }

    suspend fun update(entity: Entity){
        entityDAO.update(entity)
    }
}