package com.example.pdm.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_MONSTERS)
data class MonsterEntity(
    @PrimaryKey val id: Int,
    val species: String,
    val trainer: String
)

fun MonsterEntity.toMonster() = Monster(
    id, species, trainer
)