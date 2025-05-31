package com.example.kolsatest.data.remote

import com.example.kolsatest.data.remote.model.ApiVideoWorkout
import com.example.kolsatest.data.remote.model.ApiWorkout
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkoutsApiService {

    @GET("get_workouts")
    suspend fun getWorkouts(): List<ApiWorkout>

    @GET("get_video")
    suspend fun getVideoWorkout(
        @Query("id") workoutId: Int,
    ): ApiVideoWorkout
}
