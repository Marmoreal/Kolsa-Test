package com.example.kolsatest.data.remote

class SemimockWorkoutsApiService(
    apiService: WorkoutsApiService
) : WorkoutsApiService by apiService
