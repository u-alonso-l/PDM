package com.example.pdm.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_ACTIVITIES)
data class ActivityEntity(
    @PrimaryKey val name: String,
    val type: String,
    val participants: Int
)

fun ActivityEntity.toDBActivity() = DBActivity(
    name, type, participants
)