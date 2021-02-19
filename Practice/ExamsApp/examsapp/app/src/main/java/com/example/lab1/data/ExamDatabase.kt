package com.example.lab1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab1.model.Exam
import com.example.lab1.model.ExamBackup
import com.example.lab1.model.ExamsListDTO

@Database(entities = [Exam::class, ExamBackup::class], version =2, exportSchema = false)
abstract class ExamDatabase : RoomDatabase() {

    abstract fun examDAO(): ExamDAO
    abstract fun examBackupDAO() : ExamBackupDAO

    companion object{
        @Volatile
        private var INSTANCE: ExamDatabase? = null

        fun getDatabase(context: Context): ExamDatabase {
            var tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExamDatabase::class.java,
                    "examApp"
                ).build()


                INSTANCE = instance
                return instance
            }
        }
    }
}