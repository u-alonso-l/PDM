package com.example.pdm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdm.database.DBActivity
import com.example.pdm.databinding.ItemActivityBinding

class ActivityAdapter(private val activities: List<DBActivity>, private val rightViewModel: RightViewModel, private val fragment: RightFragment): RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.btnAction.setOnClickListener {
            val name = binding.tvName.text.toString()
            rightViewModel.delete(DBActivity(name, "", 0))
            rightViewModel.getActivities()
        }

        return ActivityHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int){
        holder.render(activities[position])
    }

    override fun getItemCount(): Int = activities.size


    class ActivityHolder(val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun render(activity: DBActivity) {
            binding.tvName.text = activity.name
            binding.tvParticipants.text = activity.participants.toString()
        }
    }
}