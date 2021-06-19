package com.example.simplewallpaperapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pagination.models.Hit

@Database(entities = [Hit::class],version = 2)
abstract class AppDataBase:RoomDatabase() {
    abstract fun imageDao():ImageDao
    companion object{
        private var appDatabase: AppDataBase? = null
        @Synchronized
        fun getInstance(context: Context): AppDataBase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDataBase::class.java, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}