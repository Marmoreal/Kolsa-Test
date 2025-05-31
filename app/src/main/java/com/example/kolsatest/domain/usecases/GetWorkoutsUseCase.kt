package com.example.kolsatest.domain.usecases

import com.example.kolsatest.domain.BaseFlowUseCase
import com.example.kolsatest.domain.model.Workout
import com.example.kolsatest.domain.repository.WorkoutsRepository
import com.example.kolsatest.domain.LoadableResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutsRepository,
): BaseFlowUseCase() {

    fun execute(): Flow<LoadableResult<List<Workout>>> {
        return loadData { repository.getWorkouts() }
    }

}
