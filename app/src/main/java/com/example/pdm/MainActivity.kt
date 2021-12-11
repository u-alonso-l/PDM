package com.example.pdm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navView : BottomNavigationView = findViewById(R.id.bottom_nav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)


        val sharedPrefs = getPreferences(Context.MODE_PRIVATE)
        if(sharedPrefs != null) {
            val success = sharedPrefs.edit().putString("loggedTrainer", "0").commit()
            Log.d("DEBUG", "set loggedTrainer to ${sharedPrefs.getString("loggedTrainer", "")}")
        }

    }

}
