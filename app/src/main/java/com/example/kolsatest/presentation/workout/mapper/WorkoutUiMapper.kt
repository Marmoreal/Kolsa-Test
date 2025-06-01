package com.example.kolsatest.presentation.workout.mapper

import com.example.kolsatest.domain.model.VideoWorkout
import com.example.kolsatest.presentation.workout.ui.WorkoutUiState
import javax.inject.Inject

private const val BASE_URL = "https://ref.test.kolsa.ru"
class WorkoutUiMapper @Inject constructor() {

    fun getVideoUrl(
        videoWorkout: VideoWorkout,
    ): WorkoutUiState {
        return WorkoutUiState (
            isLoading = false,
            videoUrl = BASE_URL + videoWorkout.link,
        )
    }

    fun setLoading(
        currentState: WorkoutUiState,
    ) = currentState.copy(isLoading = true)

    fun setError(
        currentState: WorkoutUiState,
        error: Throwable,
    ) = currentState.copy(
        isLoading = false,
        error = error,
    )
}
