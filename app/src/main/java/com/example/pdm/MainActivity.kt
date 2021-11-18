package com.example.pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var queue: RequestQueue
    private lateinit var tvDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        tvDesc = findViewById<TextView>(R.id.tv_desc)

        val etName = findViewById<EditText>(R.id.et_search_name)
        val btnSearch = findViewById<Button>(R.id.btn_search)
        btnSearch.setOnClickListener {
            if(etName.text.isNotBlank())
                search(etName.text.toString())

            etName.text.clear()
        }
    }

    fun search(name: String) {
        val url = "https://pokeapi.co/api/v2/pokemon/$name"

        val jsonRequest = JsonObjectRequest(
            url,
            { res ->
                var text = "#${res.getInt("id")} ${res.getString("name").replaceFirstChar {it.titlecase()}}\n"
                text += "weight : ${res.getInt("weight")}\n"
                text += "height : ${res.getInt("height")}\n"

                var statNames = arrayOf("hp", "attack", "defense", "sp. attack", "sp. defense", "speed")
                val stats = res.getJSONArray("stats")
                text += "\nStats\n"

                var i = 0;
                while (i < stats.length()) {
                    val obj = stats.getJSONObject(i);
                    val value = obj.getInt("base_stat")
                    text += "${statNames[i++]} : ${value}\n"
                }

                tvDesc.text = text
            },
            { err ->
                tvDesc.text = "Not found."
            }
        )

        queue.add(jsonRequest)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll("stopped")
    }

}