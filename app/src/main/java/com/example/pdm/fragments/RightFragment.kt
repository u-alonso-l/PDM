package com.example.pdm.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pdm.database.Monster
import com.example.pdm.databinding.FragmentRightBinding

class RightFragment : Fragment() {

    private lateinit var queue: RequestQueue
    private lateinit var binding : FragmentRightBinding
    private val leftViewModel: LeftViewModel by viewModels()
    private var loggedTrainer = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRightBinding.inflate(inflater, container, false)

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
        if(sharedPrefs != null)
            loggedTrainer = sharedPrefs.getString("loggedTrainer", "")?.toInt() ?: 0

        queue = Volley.newRequestQueue(context)
        val etName = binding.etSearchName
        val btnSearch = binding.btnSearch
        btnSearch.setOnClickListener {
            if(etName.text.isNotBlank())
                search(etName.text.toString())

            etName.text.clear()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun search(name: String) {
        val url = "https://pokeapi.co/api/v2/pokemon/$name"

        val jsonRequest = JsonObjectRequest(
            url,
            { res ->
                var id = "${res.getInt("id")}".toInt()
                var species = "${res.getString("name").replaceFirstChar {it.titlecase()}}"
                leftViewModel.save(Monster(id, species, "$loggedTrainer"))
                Toast.makeText(context, "Successfully added $species. Check it out in your dex!", Toast.LENGTH_SHORT).show()

                val name = id.toString()
                val destination = RightFragmentDirections.actionRightFragmentToMonDescriptionFragment(name)
                NavHostFragment.findNavController(this).navigate(destination)
            },
            { err ->
                Toast.makeText(context, "Looks like something went wrong, try again later.", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll("stopped")
    }

}