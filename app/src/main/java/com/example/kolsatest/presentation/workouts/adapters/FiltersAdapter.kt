package com.example.kolsatest.presentation.workouts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kolsatest.databinding.ItemFilterBinding
import com.example.kolsatest.presentation.workouts.model.WorkoutUiFilter
import javax.inject.Inject

class FiltersAdapter @Inject constructor() : RecyclerView.Adapter<FilterViewHolder>() {

    private val items = mutableListOf<WorkoutUiFilter>()

    lateinit var onItemFilterClick: (WorkoutUiFilter) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FilterViewHolder {
        return FilterViewHolder(
            onItemClick = onItemFilterClick,
            binding = ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
        )
    }

    override fun onBindViewHolder(
        holder: FilterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getItem(position: Int): WorkoutUiFilter {
        return items[position]
    }

    fun submitList(items: List<WorkoutUiFilter>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}