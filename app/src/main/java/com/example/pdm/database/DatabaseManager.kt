package com.example.pdm.database

import android.content.Context
import androidx.room.Room

class DatabaseManager {
    lateinit var  database: AppDatabase

    companion object {
        val instance = DatabaseManager()
    }

    fun initializeDB(context: Context) {
        createDB(context)
    }

    private fun createDB(context: Context) {
        database = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}