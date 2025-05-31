package com.example.kolsatest.presentation.workouts.ui

import com.example.kolsatest.presentation.BaseUiState
import com.example.kolsatest.presentation.workouts.model.WorkoutUi
import com.example.kolsatest.presentation.workouts.model.WorkoutUiFilter

data class WorkoutsUiState(
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
    override val isEmptyData: Boolean = false,
    /** Список всех тренировок */
    val workouts: List<WorkoutUi> = emptyList(),
    /** Список фильтров */
    val filters: List<WorkoutUiFilter> = emptyList(),
    /** Список тренировок по фильтру */
    val filteredWorkouts: List<WorkoutUi> = emptyList(),
    /** Запрос поиска по названию*/
    val query: String = "",
): BaseUiState
