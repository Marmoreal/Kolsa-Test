package com.example.kolsatest.domain.usecases

import com.example.kolsatest.domain.model.Workout
import com.example.kolsatest.domain.repository.WorkoutsRepository
import com.example.kolsatest.domain.LoadableResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutsRepository,
) {

    fun execute(): Flow<LoadableResult<List<Workout>>> {
        return loadData { repository.getWorkouts() }
    }

    private fun loadData(
        block: suspend () -> List<Workout>,
    ): Flow<LoadableResult<List<Workout>>> = flow {
        try {
            emit(LoadableResult.Loading())
            val result = block()
            emit(LoadableResult.Success(result))
        } catch (error: Throwable) {
            emit(LoadableResult.Failure(error))
        }
    }
}
