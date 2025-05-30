package com.example.kolsatest.presentation.workouts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolsatest.domain.LoadableResult
import com.example.kolsatest.domain.usecases.GetWorkoutsUseCase
import com.example.kolsatest.presentation.workouts.mapper.WorkoutsUiMapper
import com.example.kolsatest.presentation.workouts.model.WorkoutUiFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    getWorkoutsUseCase: GetWorkoutsUseCase,
    private val mapper: WorkoutsUiMapper,
) : ViewModel() {

    private val _state = MutableStateFlow<WorkoutUiState>(WorkoutUiState.Loading)
    val state = _state.asStateFlow()

    init {
        getWorkoutsUseCase.execute().onEach { result ->
            _state.value = when (result) {
                is LoadableResult.Loading -> {
                    WorkoutUiState.Loading
                }

                is LoadableResult.Success -> {
                    mapper.getWorkouts(
                        workouts = result.value,
                    )
                }

                is LoadableResult.Failure -> {
                    mapper.setError(result.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onFilterClicked(filterItem: WorkoutUiFilter) {
        _state.value = mapper.updateWorkoutsByFilter(
            filterItem = filterItem,
            currentState = _state.value as WorkoutUiState.Success,
        )
    }

    fun updateSearchQuery(query: String) {
        _state.value = mapper.updateWorkoutsBySearchQuery(
            query = query.lowercase(),
            currentState = _state.value as WorkoutUiState.Success,
        )
    }
}
