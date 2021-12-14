package com.example.pdm.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyAppDataSource(private val activityDao: ActivityDao) {
    suspend fun getActivities(): LiveData<List<DBActivity>> = withContext(Dispatchers.IO) {
        return@withContext MutableLiveData(activityDao.getActivitiesFromDatabase().map { it.toDBActivity() })
    }

    suspend fun delete(activity: DBActivity) = withContext(Dispatchers.IO) {
        activityDao.delete(activity.toActivityEntity())
    }

    suspend fun save(activity: DBActivity) = withContext(Dispatchers.IO) {
        activityDao.save(activity.toActivityEntity())
    }
}