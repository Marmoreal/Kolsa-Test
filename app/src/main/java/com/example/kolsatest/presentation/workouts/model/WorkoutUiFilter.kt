package com.example.kolsatest.presentation.workouts.model

import com.example.kolsatest.domain.model.WorkoutType

sealed class WorkoutUiFilter(
    open val title: String,
    open var isSelected: Boolean,
) {
    data class All(
        override val title: String,
        override var isSelected: Boolean,
    ) : WorkoutUiFilter(title, isSelected, )

    data class ByType(
        override val title: String,
        override var isSelected: Boolean,
        val type: WorkoutType,
    ) : WorkoutUiFilter(title, isSelected, )
}
