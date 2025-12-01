package com.github.guilhermebauer.lealappstestetecnico.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.databinding.ItemWorkoutBinding

class WorkoutAdapter(
    private val onClick: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private val list = mutableListOf<Workout>()

    fun submitList(newList: List<Workout>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class WorkoutViewHolder(val binding: ItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = list[position]

        holder.binding.apply {
            textTitle.text = workout.name
            textDescription.text = workout.description

            root.setOnClickListener {
                onClick(workout)
            }
        }
    }

    override fun getItemCount() = list.size
}
