package com.example.kolsatest.presentation.videoplayer.model

sealed class PlaybackSpeed(val speed: Float, val title: String) {
    object Speed05x : PlaybackSpeed(0.5f, "0.5x")
    object Speed075x : PlaybackSpeed(0.75f, "0.75x")
    object SpeedNormal : PlaybackSpeed(1.0f, "Обычная")
    object Speed125x : PlaybackSpeed(1.25f, "1.25x")
    object Speed15x : PlaybackSpeed(1.5f, "1.5x")
    object Speed20x : PlaybackSpeed(2.0f, "2.0x")

    companion object {
        val list = listOf(Speed05x, Speed075x, SpeedNormal, Speed125x, Speed15x, Speed20x)

        fun fromSpeed(speed: Float): PlaybackSpeed {
            return list.find { it.speed == speed } ?: SpeedNormal
        }
    }
}