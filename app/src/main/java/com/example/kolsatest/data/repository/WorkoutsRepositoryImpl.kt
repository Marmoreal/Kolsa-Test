package com.example.kolsatest.data.repository

import com.example.kolsatest.data.mapper.WorkoutsMapper
import com.example.kolsatest.data.remote.WorkoutsApiService
import com.example.kolsatest.domain.model.Workout
import com.example.kolsatest.domain.repository.WorkoutsRepository
import javax.inject.Inject

class WorkoutsRepositoryImpl @Inject constructor(
    private val apiService: WorkoutsApiService,
    private val workoutsMapper: WorkoutsMapper,
) : WorkoutsRepository {

    override suspend fun getWorkouts(): List<Workout> {
        return apiService.getWorkouts().map {
            workoutsMapper.fromApiToModel(it)
        }
    }
}
