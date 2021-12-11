package com.example.pdm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pdm.database.Monster
import com.example.pdm.databinding.ItemMonsterBinding
import com.example.pdm.fragments.HomeFragment
import com.example.pdm.fragments.HomeFragmentDirections
import com.example.pdm.fragments.LeftViewModel

class MonsterAdapter(private val monsters: List<Monster>, private val leftViewModel: LeftViewModel, private val loggedTrainer: Int, private val fragment: HomeFragment): RecyclerView.Adapter<MonsterAdapter.MonsterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterHolder {
        val binding = ItemMonsterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.btnAction.setOnClickListener {
            val id = binding.tvMonId.text.toString().toInt()
            leftViewModel.delete(Monster(id, "", ""))
            leftViewModel.getMonstersFromTrainer("$loggedTrainer")
        }

        binding.tvMonSpecies.setOnClickListener {
            val name = binding.tvMonId.text.toString()
            val destination = HomeFragmentDirections.actionHomeFragmentToMonDescriptionFragment(name)
            NavHostFragment.findNavController(fragment).navigate(destination)
        }

        return MonsterHolder(binding)
    }

    override fun onBindViewHolder(holder: MonsterHolder, position: Int){
        holder.render(monsters[position])
    }

    override fun getItemCount(): Int = monsters.size


    class MonsterHolder(val binding: ItemMonsterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun render(monster: Monster) {
            binding.tvMonSpecies.text = monster.species
            binding.tvMonId.text = monster.id.toString()
        }
    }
}
