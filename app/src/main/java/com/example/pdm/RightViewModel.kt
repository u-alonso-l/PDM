package com.example.pdm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm.database.DBActivity
import com.example.pdm.database.DatabaseManager
import com.example.pdm.database.MyAppDataSource
import kotlinx.coroutines.launch

class RightViewModel : ViewModel() {
    val savedActivities = MutableLiveData<List<DBActivity>>()

    fun save(activity: DBActivity) {
        viewModelScope.launch {
            val activitiesDao = DatabaseManager.instance.database.activitiesDao()
            MyAppDataSource(activitiesDao).save(activity)
        }
    }

    fun delete(activity: DBActivity) {
        viewModelScope.launch {
            val activitiesDao = DatabaseManager.instance.database.activitiesDao()
            MyAppDataSource(activitiesDao).delete(activity)
        }
    }

    fun getActivities() {
        viewModelScope.launch {
            val activitiesDao = DatabaseManager.instance.database.activitiesDao()
            savedActivities.value = MyAppDataSource(activitiesDao).getActivities().value
        }
    }

}