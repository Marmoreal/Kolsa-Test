package com.example.kolsatest.di.module

import com.example.kolsatest.data.remote.SemimockWorkoutsApiService
import com.example.kolsatest.data.remote.WorkoutsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkoutsApiModule {

    @Provides
    @Singleton
    fun provideWorkoutsApiService(
        retrofit: Retrofit,
    ): WorkoutsApiService {
        return SemimockWorkoutsApiService(retrofit.create())
    }
}
