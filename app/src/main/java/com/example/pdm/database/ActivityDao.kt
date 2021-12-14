package com.example.pdm.database

import androidx.room.*

@Dao
interface ActivityDao {
    @Delete
    fun delete(activity: ActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(activity: ActivityEntity)

    @Query("SELECT * from $TABLE_ACTIVITIES")
    fun getActivitiesFromDatabase() : List<ActivityEntity>
}