package com.example.lab1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab1.model.Entity
import com.example.lab1.model.EntityBackup

@Database(entities = [Entity::class, EntityBackup::class], version =2, exportSchema = false)
abstract class EntityDatabase : RoomDatabase() {

    abstract fun entityDAO(): EntityDAO
    abstract fun entityBackupDAO() : EntityBackupDAO

    companion object{
        @Volatile
        private var INSTANCE: EntityDatabase? = null

        fun getDatabase(context: Context): EntityDatabase {
            var tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntityDatabase::class.java,
                    "exam"
                ).build()


                INSTANCE = instance
                return instance
            }
        }
    }
}