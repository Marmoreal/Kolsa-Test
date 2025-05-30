package com.example.kolsatest.di.module

import com.example.kolsatest.data.repository.WorkoutsRepositoryImpl
import com.example.kolsatest.domain.repository.WorkoutsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutsBindingModule {

    @Binds
    abstract fun provideWorkoutsRepository(
        workoutsRepository: WorkoutsRepositoryImpl,
    ): WorkoutsRepository
}
