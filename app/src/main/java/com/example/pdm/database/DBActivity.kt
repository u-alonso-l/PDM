package com.example.pdm.database

class DBActivity(name: String, type: String, participants: Int) {
    val name = name
    val type = type
    val participants = participants
}

fun DBActivity.toActivityEntity() = ActivityEntity(
    name, type, participants
)