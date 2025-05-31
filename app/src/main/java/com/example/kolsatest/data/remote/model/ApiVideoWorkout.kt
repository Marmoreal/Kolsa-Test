package com.example.kolsatest.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiVideoWorkout(
    val id: Int,
    val duration: String,
    val link: String,
)
