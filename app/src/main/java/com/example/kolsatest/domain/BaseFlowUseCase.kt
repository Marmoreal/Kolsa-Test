package com.example.kolsatest.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseFlowUseCase {

    fun <T> loadData(
        block: suspend () -> T,
    ): Flow<LoadableResult<T>> = flow {
        try {
            emit(LoadableResult.Loading())
            val result = block()
            emit(LoadableResult.Success(result))
        } catch (error: Throwable) {
            emit(LoadableResult.Failure(error))
        }
    }
}
