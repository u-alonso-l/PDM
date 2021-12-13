package com.example.pdm

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.Geocoder
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var queue: RequestQueue
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var tvLocations: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        tvLocations = findViewById(R.id.tv_locations)
        findViewById<Button>(R.id.btn_update).setOnClickListener{ updateLocation() }
    }

    fun updateLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Doesn't have permission",Toast.LENGTH_SHORT).show()
            Log.d("LocationPermissions", "Doesn't have permission")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }
            return
        }else{
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->

                val addresses: List<Address>
                val geocoder = Geocoder(this, Locale.getDefault())

                val lat = location?.latitude
                val lon = location?.longitude

                addresses = geocoder.getFromLocation(lat!!, lon!!, 1 )
                Log.d("DEBUG", "${addresses[0].postalCode}, $lat, $lon")
                search(addresses[0].postalCode)
            }
        }
    }

    fun search(zipCode: String) {
        val url = "https://api.zippopotam.us/mx/$zipCode"

        val jsonRequest = JsonObjectRequest(
            url,
            { res ->
                var places = res.getJSONArray("places")
                Log.d("DEBUG", places.toString())
                var place = places[0] as JSONObject
                tvLocations.text = place.getString("place name")
            },
            { err ->
                Toast.makeText(this,"Not found.",Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }


    override fun onStop() {
        super.onStop()
        queue.cancelAll("stopped")
    }
}