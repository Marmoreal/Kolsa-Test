package com.example.kolsatest.presentation.workout.ui

import com.example.kolsatest.presentation.BaseUiState

data class WorkoutUiState(
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
    override val isEmptyData: Boolean = false,
    val videoUrl: String = "",
) : BaseUiState
