package com.example.kolsatest.domain.model

enum class WorkoutType(val type: Int) {
    WORKOUT(1),
    STREAM(2),
    COMPLEX(3);

    companion object {
        fun fromInt(value: Int): WorkoutType = when (value) {
            1 -> WORKOUT
            2 -> STREAM
            3 -> COMPLEX
            else -> throw IllegalArgumentException("Unknown workout type: $value")
        }
    }
}
