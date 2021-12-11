package com.example.pdm.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_VERSION = 2
const val TABLE_MONSTERS = "monsters"
const val DATABASE_NAME = "pdm.sqlite"

@Database(
    entities = [MonsterEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){

    abstract fun monstersDao(): MonsterDao

}