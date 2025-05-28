package com.example.kolsatest.domain.model

data class Workout(
    val id: Int,
    val title: String,
    val description: String,
    val type: WorkoutType,
    val duration: String,
)
