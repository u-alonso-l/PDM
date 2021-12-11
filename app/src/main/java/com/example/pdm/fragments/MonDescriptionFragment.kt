package com.example.pdm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pdm.databinding.FragmentMonDescriptionBinding

class MonDescriptionFragment : Fragment() {

    private lateinit var binding : FragmentMonDescriptionBinding
    private lateinit var queue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(context)
        val name = arguments?.getString("name_arg")
        if (name != null)
            search(name)
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

                binding.tvDesc.text = text
            },
            { err ->
                binding.tvDesc.text = "Not found."
            }
        )

        queue.add(jsonRequest)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll("stopped")
    }

}