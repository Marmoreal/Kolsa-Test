package com.example.kolsatest.presentation.workouts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kolsatest.databinding.WorkoutsFragmentBinding
import com.example.kolsatest.presentation.commonview.StateViewFlipper
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
        setupViewFlipper()
        setupSearch()
        initAdapter()
    }

    private fun onBindViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.root.setState(state)

                if (binding.root.currentState() == StateViewFlipper.State.DATA) {
                    filtersAdapter.submitList(state.filters)

                    binding.viewFlipper.displayedChild = if (state.filteredWorkouts.isNotEmpty()) {
                        workoutsAdapter.submitList(state.filteredWorkouts) {
                            binding.workoutRecyclerView.scrollToPosition(0)
                        }
                        STATE_DATA
                    } else STATE_EMPTY
                }
            }
        }
    }

    private fun setupViewFlipper() = with(binding.root) {
        setRetryMethod {
            viewModel.getWorkouts()
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
            workoutsAdapter.onWorkoutClick = { workout ->
                findNavController().navigate(
                    WorkoutsFragmentDirections.actionWorkoutsFragmentToWorkoutFragment(workout)
                )
            }
            adapter = workoutsAdapter
        }
    }

    companion object {
        private const val STATE_EMPTY = 0
        private const val STATE_DATA = 1
    }
}
