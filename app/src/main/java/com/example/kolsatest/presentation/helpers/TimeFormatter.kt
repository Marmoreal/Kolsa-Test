package com.example.kolsatest.presentation.helpers

import javax.inject.Inject

private const val MIL_IN_SEC = 1000
private const val MIN_IN_HOUR = 60
private const val SEC_IN_MINUTE = 60

class TimeFormatter @Inject constructor() {

    fun formatTime(millis: Long): String {
        val seconds = millis / MIL_IN_SEC
        val minutes = seconds / SEC_IN_MINUTE
        val hours = minutes / MIN_IN_HOUR

        return if (hours > 0) {
            "%02d:%02d:%02d".format(hours, minutes, seconds)
        } else {
            "%02d:%02d".format( minutes, seconds)
        }
    }
}
