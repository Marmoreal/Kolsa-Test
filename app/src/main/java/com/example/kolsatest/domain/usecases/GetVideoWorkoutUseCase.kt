package com.example.kolsatest.domain.usecases

import com.example.kolsatest.domain.BaseFlowUseCase
import com.example.kolsatest.domain.LoadableResult
import com.example.kolsatest.domain.model.VideoWorkout
import com.example.kolsatest.domain.repository.WorkoutsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoWorkoutUseCase @Inject constructor(
    private val repository: WorkoutsRepository,
) : BaseFlowUseCase() {

    fun execute(workoutId: Int): Flow<LoadableResult<VideoWorkout>> {
        return loadData { repository.getVideoWorkout(workoutId) }
    }
}
