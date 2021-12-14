package com.example.pdm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdm.databinding.FragmentRightBinding

class RightFragment : Fragment() {

    private val rightViewModel: RightViewModel by viewModels()
    private lateinit var binding : FragmentRightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRightBinding.inflate(inflater, container, false)

        // Recycle view
        rightViewModel.getActivities()
        rightViewModel.savedActivities.observe(viewLifecycleOwner, { activities ->
            binding.rvActEntries.layoutManager = LinearLayoutManager(view?.context)
            val adapter = ActivityAdapter(activities, rightViewModel, this)
            binding.rvActEntries.adapter = adapter
        })

        return binding.root
    }

}