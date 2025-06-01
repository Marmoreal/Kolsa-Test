package com.example.kolsatest.presentation.videoplayer.ui

import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TEN_SECONDS_IN_MILLIS = 10_000

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val player: ExoPlayer,
) : ViewModel() {

    private val _state = MutableStateFlow(VideoPlayerUiState())
    val state = _state.asStateFlow()

    fun initPlayer(videoUrl: String) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        player.apply {
            seekToDefaultPosition(0)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_READY -> _state.update { it.copy(isShowControllers = false) }
                        Player.STATE_ENDED -> _state.update { it.copy(isShowControllers = true) }
                        Player.STATE_BUFFERING -> Unit
                        Player.STATE_IDLE -> Unit
                        else -> Unit
                    }
                }
                override fun onPlayerError(error: PlaybackException) {
                    _state.update { it.copy(error = error) }
                }
            })
        }
    }

    fun setPlaybackSpeed(speed: Float) {
        player.playbackParameters = PlaybackParameters(speed)
        _state.update { it.copy(playbackSpeed = speed) }
    }

    fun rewindTenSeconds() {
        val newPosition = maxOf(0, player.currentPosition - TEN_SECONDS_IN_MILLIS)
        player.seekTo(newPosition)
    }

    fun fastForwardTenSeconds() {
        val newPosition = minOf(player.duration, player.currentPosition + TEN_SECONDS_IN_MILLIS)
        player.seekTo(newPosition)
    }

    fun toggleControllersVisibility() {
        _state.update { it.copy(isShowControllers = !it.isShowControllers) }
    }

    fun resumeVideo() {
        player.play()
        player.playWhenReady = true
    }

    fun pauseVideo() {
        player.pause()
        player.playWhenReady = false
    }

    fun stopVideo() {
        _state.update { it.copy(playerCurrentPosition = player.currentPosition) }
        player.stop()
    }
}
