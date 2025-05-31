package com.example.kolsatest.presentation.workouts.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutUi(
    val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val duration: String,
) : Parcelable
