package com.example.pdm.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyAppDataSource(private val monsterDao: MonsterDao) {
    suspend fun getMonsters(): LiveData<List<Monster>> = withContext(Dispatchers.IO) {
        return@withContext MutableLiveData(monsterDao.getMonstersFromDatabase().map { it.toMonster() })
    }

    suspend fun getMonstersFromTrainer(query: String): LiveData<List<Monster>> = withContext(Dispatchers.IO) {
        return@withContext MutableLiveData(monsterDao.getMonstersFromTrainer(query).map { it.toMonster() })
    }

    suspend fun delete(monster: Monster) = withContext(Dispatchers.IO) {
        monsterDao.delete(monster.toMonsterEntity())
    }

    suspend fun save(monster: Monster) = withContext(Dispatchers.IO) {
        monsterDao.save(monster.toMonsterEntity())
    }
}