package com.example.kolsatest.presentation.workouts.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.kolsatest.databinding.ItemFilterBinding
import com.example.kolsatest.presentation.workouts.model.WorkoutUiFilter

class FilterViewHolder(
    private val binding: ItemFilterBinding,
    private val onItemClick: (WorkoutUiFilter) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(filter: WorkoutUiFilter) = with(binding.chipFilter) {
        text = filter.title
        isChecked = filter.isSelected

        setOnClickListener {
            isChecked = !isChecked
            filter.isSelected = !filter.isSelected
            onItemClick(filter)
        }
    }
}
