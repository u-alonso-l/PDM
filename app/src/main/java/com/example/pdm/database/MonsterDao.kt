package com.example.pdm.database

import androidx.room.*

@Dao
interface MonsterDao {
    @Delete
    fun delete(monster: MonsterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(monster: MonsterEntity)

    @Query("SELECT * from $TABLE_MONSTERS")
    fun getMonstersFromDatabase() : List<MonsterEntity>

    @Query("SELECT * from $TABLE_MONSTERS WHERE trainer = :query")
    fun getMonstersFromTrainer(query: String) : List<MonsterEntity>
}