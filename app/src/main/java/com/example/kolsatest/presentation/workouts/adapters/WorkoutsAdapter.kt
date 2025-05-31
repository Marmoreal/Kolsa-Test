package com.example.kolsatest.presentation.workouts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.kolsatest.databinding.ItemWorkoutBinding
import com.example.kolsatest.presentation.workouts.model.WorkoutUi
import javax.inject.Inject

class WorkoutsAdapter @Inject constructor() : ListAdapter<WorkoutUi, WorkoutViewHolder>(
    WorkoutDiffUtilItemCallback
) {

    lateinit var onWorkoutClick: (WorkoutUi) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutViewHolder {
        return WorkoutViewHolder(
            binding = ItemWorkoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onItemClick = onWorkoutClick,
        )
    }

    override fun onBindViewHolder(
        holder: WorkoutViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    private object WorkoutDiffUtilItemCallback : DiffUtil.ItemCallback<WorkoutUi>() {
        override fun areItemsTheSame(
            oldItem: WorkoutUi,
            newItem: WorkoutUi,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WorkoutUi,
            newItem: WorkoutUi,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
