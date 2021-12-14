package com.example.pdm

import android.app.Application
import com.example.pdm.database.DatabaseManager

open class MyApplication : Application(){

    override fun onCreate() {
        DatabaseManager.instance.initializeDB((applicationContext))
        super.onCreate()
    }

}