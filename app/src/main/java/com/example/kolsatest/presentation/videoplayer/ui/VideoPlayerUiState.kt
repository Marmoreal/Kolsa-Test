package com.example.kolsatest.presentation.videoplayer.ui

import com.example.kolsatest.presentation.BaseUiState

data class VideoPlayerUiState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val isShowControllers: Boolean = false,
    val playbackSpeed: Float = 1.0f,
    val playerCurrentPosition: Long = 0L,
) : BaseUiState()
