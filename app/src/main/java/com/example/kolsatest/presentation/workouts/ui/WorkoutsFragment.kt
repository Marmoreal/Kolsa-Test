package com.example.kolsatest.presentation.workouts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kolsatest.databinding.WorkoutsFragmentBinding
import com.example.kolsatest.presentation.workouts.adapters.FiltersAdapter
import com.example.kolsatest.presentation.workouts.adapters.WorkoutsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutsFragment : Fragment() {

    private lateinit var binding: WorkoutsFragmentBinding
    private val viewModel: WorkoutsViewModel by viewModels()

    @Inject lateinit var filtersAdapter: FiltersAdapter
    @Inject lateinit var workoutsAdapter: WorkoutsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WorkoutsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
        onBindViewModel()
    }

    private fun setupLayout() {
        setupSearch()
        initAdapter()
    }

    private fun onBindViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state is WorkoutUiState.Success) {
                    filtersAdapter.submitList(state.filters)
                    workoutsAdapter.submitList(state.filteredWorkouts) {
                        binding.workoutRecyclerView.scrollToPosition(0)
                    }
                }
            }
        }
    }

    private fun setupSearch() = with(binding) {
        editTextSearch.addTextChangedListener { editable ->
            val query = editable.toString()
            viewModel.updateSearchQuery(query)
        }
    }

    private fun initAdapter() = with(binding) {
        binding.filterRecyclerView.apply {
            filtersAdapter.onItemFilterClick = { filter ->
                viewModel.onFilterClicked(filter)
            }
            adapter = filtersAdapter
        }

        binding.workoutRecyclerView.apply {
            workoutsAdapter.onWorkoutClick = { id ->

            }
            adapter = workoutsAdapter
        }
    }
}
