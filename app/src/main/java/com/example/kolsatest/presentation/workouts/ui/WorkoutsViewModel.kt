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
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val uiMapper: WorkoutsUiMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(WorkoutsUiState())
    val state = _state.asStateFlow()

    init {
        getWorkouts()
    }

    fun getWorkouts() {
        getWorkoutsUseCase.execute().onEach { result ->
            _state.value = when (result) {
                is LoadableResult.Loading -> {
                    uiMapper.setLoading(_state.value)
                }
                is LoadableResult.Success -> {
                    uiMapper.getWorkouts(
                        workouts = result.value,
                    )
                }
                is LoadableResult.Failure -> {
                    uiMapper.setError(
                        currentState = _state.value,
                        error = result.error,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onFilterClicked(filterItem: WorkoutUiFilter) {
        _state.value = uiMapper.updateWorkoutsByFilter(
            filterItem = filterItem,
            currentState = _state.value,
        )
    }

    fun updateSearchQuery(query: String) {
        _state.value = uiMapper.updateWorkoutsBySearchQuery(
            query = query.lowercase(),
            currentState = _state.value,
        )
    }
}
