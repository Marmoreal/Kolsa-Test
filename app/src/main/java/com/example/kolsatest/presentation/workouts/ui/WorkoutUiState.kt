package com.example.kolsatest.presentation.workouts.ui

import com.example.kolsatest.presentation.workouts.model.WorkoutUi
import com.example.kolsatest.presentation.workouts.model.WorkoutUiFilter

sealed interface WorkoutUiState {

    data object Loading : WorkoutUiState

    data class Success(
        /** Список всех тренировок */
        val workouts: List<WorkoutUi> = emptyList(),
        /** Список фильтров */
        val filters: List<WorkoutUiFilter> = emptyList(),
        /** Список тренировок по фильтру */
        val filteredWorkouts: List<WorkoutUi> = emptyList(),
        /** Запрос поиска по названию*/
        val query: String = "",
    ) : WorkoutUiState

    data class Error(
        val error: Throwable,
    ) : WorkoutUiState
}
