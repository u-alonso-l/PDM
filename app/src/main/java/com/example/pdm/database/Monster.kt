package com.example.pdm.database

class Monster(id: Int, species: String, trainer: String) {
    val id = id
    val species = species
    val trainer = trainer
}

fun Monster.toMonsterEntity() = MonsterEntity(
    id, species, trainer
)