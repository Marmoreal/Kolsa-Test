package com.example.kolsatest.presentation.workouts.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.kolsatest.databinding.ItemWorkoutBinding
import com.example.kolsatest.presentation.workouts.model.WorkoutUi

class WorkoutViewHolder(
    private val binding: ItemWorkoutBinding,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(workout: WorkoutUi) = with(binding) {
        root.setOnClickListener {
            onItemClick(workout.id)
        }
        textViewTitle.text = workout.title
        textViewType.text = workout.type
        textViewDuration.text = workout.duration
        textViewDescription.text = workout.description
    }
}
