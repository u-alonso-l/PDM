package com.example.pdm.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdm.MonsterAdapter
import com.example.pdm.database.Monster
import org.json.JSONObject
import com.example.pdm.databinding.FragmentLeftBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var database: DatabaseReference

class LeftFragment : Fragment() {

    private lateinit var binding : FragmentLeftBinding
    private var loggedTrainer = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeftBinding.inflate(inflater, container, false)

        val db = FirebaseDatabase.getInstance()
        database = db.reference

        // val trainer = Trainer(0, "Haruka", "2002-11-21")
        // val trainer = Trainer(1, "Hikari", "2006-09-28")
        // updateTrainer(trainer)
        // getTrainer(0)

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
        if(sharedPrefs != null)
            loggedTrainer = sharedPrefs.getString("loggedTrainer", "")?.toInt() ?: 0;

        getTrainer(loggedTrainer)

        return binding.root
    }

    fun getTrainer(id: Int) {
        database.child("trainers").child("$id").get().addOnSuccessListener { res ->
            val json = JSONObject(res.value.toString())
            binding.tvTrainerName.text = json.getString("name")
            binding.tvTrainerDateJoined.text = json.getString("dateJoined")
            Log.d("DEBUG", "$json")
        }.addOnFailureListener{ err ->
            Log.d("DEBUG", "$err")
        }
    }

    fun updateTrainer(trainer: Trainer) {
        database.child("trainers").child("${trainer.id}").setValue(trainer).addOnSuccessListener {
            Log.d("DEBUG", "updated ${trainer.name}")
        }.addOnFailureListener{ err ->
            Log.d("DEBUG", "$err")
        }
    }

}

class Trainer(id: Int, name: String, dateJoined: String) {
    val id = id
    val name = name
    val dateJoined = dateJoined
}