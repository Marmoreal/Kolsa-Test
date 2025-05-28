package com.example.kolsatest.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiWorkout(
    val id: Int,
    val title: String?,
    val description: String?,
    val type: Int,
    val duration: String?,
)
