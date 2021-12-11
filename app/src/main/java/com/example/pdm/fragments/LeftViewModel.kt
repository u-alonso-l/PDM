package com.example.pdm.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm.database.DatabaseManager
import com.example.pdm.database.Monster
import com.example.pdm.database.MyAppDataSource
import kotlinx.coroutines.launch

class LeftViewModel : ViewModel() {
    val savedMonsters = MutableLiveData<List<Monster>>()

    fun save(monster: Monster) {
        viewModelScope.launch {
            val monstersDao = DatabaseManager.instance.database.monstersDao()
            MyAppDataSource(monstersDao).save(monster)
        }
    }

    fun delete(monster: Monster) {
        viewModelScope.launch {
            val monstersDao = DatabaseManager.instance.database.monstersDao()
            MyAppDataSource(monstersDao).delete(monster)
        }
    }

    fun getMonsters() {
        viewModelScope.launch {
            val monstersDao = DatabaseManager.instance.database.monstersDao()
            savedMonsters.value = MyAppDataSource(monstersDao).getMonsters().value
        }
    }

    fun getMonstersFromTrainer(query: String) {
        viewModelScope.launch {
            val monstersDao = DatabaseManager.instance.database.monstersDao()
            savedMonsters.value = MyAppDataSource(monstersDao).getMonstersFromTrainer(query).value
        }
    }

}