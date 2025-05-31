package com.example.kolsatest.presentation.workouts.mapper

import android.content.Context
import com.example.kolsatest.R
import com.example.kolsatest.domain.model.Workout
import com.example.kolsatest.domain.model.WorkoutType
import com.example.kolsatest.domain.model.WorkoutType.COMPLEX
import com.example.kolsatest.domain.model.WorkoutType.STREAM
import com.example.kolsatest.domain.model.WorkoutType.WORKOUT
import com.example.kolsatest.presentation.workouts.model.WorkoutUi
import com.example.kolsatest.presentation.workouts.model.WorkoutUiFilter
import com.example.kolsatest.presentation.workouts.ui.WorkoutsUiState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class WorkoutsUiMapper @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getWorkouts(
        workouts: List<Workout>
    ): WorkoutsUiState {
        val filters = workouts.map { fromModelToUi(it.type) }.distinctBy { it.title }
        val uiWorkouts = workouts.map { fromModelToUi(it) }
        return WorkoutsUiState(
            isLoading = false,
            isEmptyData = uiWorkouts.isEmpty(),
            workouts = uiWorkouts,
            filters = listOf(
                WorkoutUiFilter.All(
                    title = context.getString(R.string.workouts_filter_all),
                    isSelected = true,
                )
            ) + filters,
            filteredWorkouts = uiWorkouts,
        )
    }

    fun updateWorkoutsByFilter(
        filterItem: WorkoutUiFilter,
        currentState: WorkoutsUiState,
    ): WorkoutsUiState {
        val updatedFilters = currentState.filters.onEach { it.isSelected = filterItem == it }

        val updatedWorkouts = if (filterItem is WorkoutUiFilter.ByType) {
            currentState.workouts.filter {
                it.type == filterItem.title && it.title.lowercase().contains(currentState.query)
            }
        } else currentState.workouts.filter {
            it.title.lowercase().contains(currentState.query)
        }

        return currentState.copy(
            isEmptyData = updatedWorkouts.isEmpty(),
            filters = updatedFilters,
            filteredWorkouts = updatedWorkouts,
        )
    }

    fun updateWorkoutsBySearchQuery(
        query: String,
        currentState: WorkoutsUiState,
    ): WorkoutsUiState {
        val currentFilter = currentState.filters.find { it.isSelected }
        val updatedWorkouts =
            currentState.workouts.filter {
                if (currentFilter is WorkoutUiFilter.ByType) {
                    it.title.lowercase().contains(query) && it.type == currentFilter.title
                } else {
                    it.title.lowercase().contains(query)
                }
            }

        return currentState.copy(
            isEmptyData = updatedWorkouts.isEmpty(),
            filteredWorkouts = updatedWorkouts,
            query = query,
        )
    }

    fun setLoading(
        currentState: WorkoutsUiState,
    ) = currentState.copy(isLoading = true)

    fun setError(
        currentState: WorkoutsUiState,
        error: Throwable
    ) = currentState.copy(
        isLoading = false,
        error = error,
    )

    private fun fromModelToUi(model: Workout): WorkoutUi {
        return WorkoutUi(
            id = model.id,
            title = model.title,
            description = model.description,
            type = when (model.type) {
                WORKOUT -> context.getString(R.string.workouts_filter_workout)
                STREAM -> context.getString(R.string.workouts_filter_stream)
                COMPLEX -> context.getString(R.string.workouts_filter_complex)
            },
            duration = model.duration,
        )
    }

    private fun fromModelToUi(model: WorkoutType): WorkoutUiFilter {
        return when (model) {
            WORKOUT -> WorkoutUiFilter.ByType(
                title = context.getString(R.string.workouts_filter_workout),
                isSelected = false,
                type = model,
            )
            STREAM -> WorkoutUiFilter.ByType(
                title = context.getString(R.string.workouts_filter_stream),
                isSelected = false,
                type = model,
            )
            COMPLEX -> WorkoutUiFilter.ByType(
                title = context.getString(R.string.workouts_filter_complex),
                isSelected = false,
                type = model,
            )
        }
    }
}
