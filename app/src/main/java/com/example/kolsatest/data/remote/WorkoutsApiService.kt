package com.example.kolsatest.data.remote

import com.example.kolsatest.data.remote.model.ApiWorkout
import retrofit2.http.GET

interface WorkoutsApiService {

    @GET("get_workouts")
    suspend fun getWorkouts(): List<ApiWorkout>
}
