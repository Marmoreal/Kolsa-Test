package com.example.kolsatest.presentation

abstract class BaseUiState {
    abstract val isLoading: Boolean
    abstract val error: Throwable?
    open val isEmptyData: Boolean = false
}
