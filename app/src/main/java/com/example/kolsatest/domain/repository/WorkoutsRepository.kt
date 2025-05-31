package com.example.kolsatest.domain.repository

import com.example.kolsatest.domain.model.VideoWorkout
import com.example.kolsatest.domain.model.Workout

interface WorkoutsRepository {

    /** Получить список тренировок */
    suspend fun getWorkouts(): List<Workout>

    /** Получить видео тренировки */
    suspend fun getVideoWorkout(workoutId: Int): VideoWorkout
}
