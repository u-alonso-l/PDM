package com.example.pdm.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdm.MonsterAdapter
import com.example.pdm.R
import com.example.pdm.databinding.FragmentHomeBinding
import com.example.pdm.databinding.FragmentLeftBinding

class HomeFragment : Fragment() {

    private val leftViewModel: LeftViewModel by viewModels()
    private lateinit var binding : FragmentHomeBinding
    private var loggedTrainer = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
        if(sharedPrefs != null)
            loggedTrainer = sharedPrefs.getString("loggedTrainer", "")?.toInt() ?: 0;

        // leftViewModel.save(Monster(255, "Torchic", "0"))
        // leftViewModel.save(Monster(393, "Piplup", "1"))

        // Recycle view
        leftViewModel.getMonstersFromTrainer("$loggedTrainer")
        leftViewModel.savedMonsters.observe(viewLifecycleOwner, { monsters ->
            binding.rvMonEntries.layoutManager = LinearLayoutManager(view?.context)
            val adapter = MonsterAdapter(monsters, leftViewModel, loggedTrainer, this)
            binding.rvMonEntries.adapter = adapter
        })

        return binding.root
    }

}