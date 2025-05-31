package com.example.kolsatest.data.remote

import com.example.kolsatest.data.remote.model.ApiVideoWorkout
import com.example.kolsatest.data.remote.model.ApiWorkout

class MockWorkoutsApiService : WorkoutsApiService {
    
    override suspend fun getWorkouts(): List<ApiWorkout> {
        TODO("Not yet implemented")
    }

    override suspend fun getVideoWorkout(workoutId: Int): ApiVideoWorkout {
        TODO("Not yet implemented")
    }
}
