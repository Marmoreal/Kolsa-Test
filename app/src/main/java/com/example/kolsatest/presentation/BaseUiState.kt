package com.example.kolsatest.presentation

interface BaseUiState {
    val isLoading: Boolean
    val error: Throwable?
    val isEmptyData: Boolean
}
