package com.example.pdm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pdm.database.DBActivity
import com.example.pdm.databinding.FragmentLeftBinding
import org.json.JSONObject

class LeftFragment : Fragment() {

    private lateinit var binding : FragmentLeftBinding
    private lateinit var activity : JSONObject
    private lateinit var queue: RequestQueue
    private val rightViewModel: RightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeftBinding.inflate(inflater, container, false)
        queue = Volley.newRequestQueue(context)

        binding.btnSearch.setOnClickListener{ search() }
        binding.btnSave.setOnClickListener{ saveActivity() }

        return binding.root
    }

    fun search() {
        val url = "https://www.boredapi.com/api/activity"

        val jsonRequest = JsonObjectRequest(
            url,
            { res ->
                activity = res;
                updateActivityDesc()
            },
            { err ->
                Log.d("DEBUG", "something went wrong. $err")
            }
        )

        queue.add(jsonRequest)
    }

    fun updateActivityDesc() {
        var text = ""
        text += "Name: ${activity.getString("activity")}\n"
        text += "Type: ${activity.getString("type")}\n"
        text += "Participants: ${activity.getString("participants")}"
        binding.etActivityDesc.text = text
    }

    fun saveActivity() {
        if(!::activity.isInitialized)
            return

        val activity = DBActivity(activity.getString("activity"), activity.getString("type"), activity.getString("participants").toInt())
        rightViewModel.save(activity)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll("stopped")
    }

}