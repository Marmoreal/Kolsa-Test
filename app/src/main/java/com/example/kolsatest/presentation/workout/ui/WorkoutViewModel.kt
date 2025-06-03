package com.example.kolsatest.presentation.workout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolsatest.domain.LoadableResult
import com.example.kolsatest.domain.usecases.GetVideoWorkoutUseCase
import com.example.kolsatest.presentation.extension.collectResult
import com.example.kolsatest.presentation.workout.mapper.WorkoutUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getVideoWorkoutUseCase: GetVideoWorkoutUseCase,
    private val uiMapper: WorkoutUiMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(WorkoutUiState())
    val state = _state.asStateFlow()

    fun getVideoWorkout(workoutId: Int) {
        getVideoWorkoutUseCase.execute(workoutId).collectResult(
            viewModelScope,
            onLoading = {
                _state.update { uiMapper.setLoading(_state.value) }
            },
            onSuccess = { videoWorkout ->
                _state.update { uiMapper.getVideoUrl(videoWorkout) }
            },
            onError = { error ->
                _state.update { uiMapper.setError(_state.value, error) }
            }
        )
    }
}
