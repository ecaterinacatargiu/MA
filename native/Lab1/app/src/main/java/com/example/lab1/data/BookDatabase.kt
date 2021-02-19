package com.example.lab1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab1.model.Book

@Database(entities = [Book::class], version =1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDAO(): BookDAO

    companion object{
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            var tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}