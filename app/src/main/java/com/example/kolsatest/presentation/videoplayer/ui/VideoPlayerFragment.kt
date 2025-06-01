package com.example.kolsatest.presentation.videoplayer.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kolsatest.R
import com.example.kolsatest.databinding.FragmentVideoPlayerBinding
import com.example.kolsatest.presentation.commonarchitecture.BaseFragment
import com.example.kolsatest.presentation.extension.fitInsetsWithPadding
import com.example.kolsatest.presentation.helpers.TimeFormatter
import com.example.kolsatest.presentation.videoplayer.model.PlaybackSpeed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VideoPlayerFragment @Inject constructor() : BaseFragment() {

    private lateinit var binding: FragmentVideoPlayerBinding
    private val viewModel: VideoPlayerViewModel by viewModels()

    private val args: VideoPlayerFragmentArgs by navArgs()

    private val controllerRootView: View by lazy { binding.playerView.findViewById(R.id.customControls) }
    private val buttonClose: View by lazy { binding.playerView.findViewById(R.id.buttonClose) }
    private val playButton: View by lazy { binding.playerView.findViewById(R.id.buttonPlay) }
    private val pauseButton: View by lazy { binding.playerView.findViewById(R.id.buttonPause) }
    private val buttonRewind: View by lazy { binding.playerView.findViewById(R.id.buttonRewind) }
    private val buttonFastForward: View by lazy { binding.playerView.findViewById(R.id.buttonFastForward) }
    private val buttonSettings: View by lazy { binding.playerView.findViewById(R.id.buttonSettings) }
    private val textViewDuration: TextView by lazy { binding.playerView.findViewById(R.id.textViewDuration) }

    private lateinit var settingsPopupMenu: PopupMenu
    private lateinit var settingsQualityPopupMenu: PopupMenu
    private lateinit var settingsRatePopupMenu: PopupMenu

    private var isPlaybackPositionRestored = false

    override val isLightStatusBar = false

    @Inject lateinit var timeFormatter: TimeFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR

        viewModel.initPlayer(args.videoUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
        onBindViewModel()
    }

    private fun setupLayout() {
        controllerRootView.fitInsetsWithPadding()
        buttonClose.setOnClickListener {
            findNavController().popBackStack()
        }
        setupPlayer()
        setupListeners()
        setupSettingMenu(buttonSettings)
        setupSettingsQualityMenu(buttonSettings)
        setupSettingsRateMenu(buttonSettings)
    }


    private fun onBindViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.root.setState(state)

                bindPlayer(state)
            }
        }
        observePlaybackTime()
    }

    private fun setupPlayer() {
        if (binding.playerView.player == null) {
            binding.playerView.player = viewModel.player
        }
    }

    private fun setupListeners() = with(binding) {
        root.setRetryMethod {
            viewModel.initPlayer(args.videoUrl)
        }
        playerView.setOnClickListener {
            viewModel.toggleControllersVisibility()
        }
        playButton.setOnClickListener {
            viewModel.resumeVideo()
            changedIsPlaying(true)
        }
        pauseButton.setOnClickListener {
            viewModel.pauseVideo()
            changedIsPlaying(false)
        }
        buttonSettings.setOnClickListener { view ->
            showPopupSettingsMenu()
        }
        buttonRewind.setOnClickListener {
            viewModel.rewindTenSeconds()
        }
        buttonFastForward.setOnClickListener {
            viewModel.fastForwardTenSeconds()
        }
    }

    @OptIn(UnstableApi::class)
    private fun bindPlayer(state: VideoPlayerUiState) = with(binding.playerView) {
        if (!isPlaybackPositionRestored && state.playerCurrentPosition > 0) {
            player?.seekTo(state.playerCurrentPosition)
            isPlaybackPositionRestored = true
        }

        if (binding.root.currentStateIsData()) {
            applyControllersVisibility(state.isShowControllers)
            changedIsPlaying(true)
        }

        textViewDuration.isVisible = state.isShowControllers
    }

    private fun observePlaybackTime() {
        val player = binding.playerView.player ?: return

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) {
                    val currentPosition = timeFormatter.formatTime(player.currentPosition)
                    val duration = timeFormatter.formatTime(player.duration)

                    textViewDuration.text = getString(
                        R.string.video_player_controls_time_duration_format,
                        currentPosition,
                        duration,
                    )

                    delay(500)
                }
            }
        }
    }

    private fun changedIsPlaying(isPlaying: Boolean) {
        playButton.isVisible = !isPlaying
        pauseButton.isVisible = isPlaying
    }

    @OptIn(UnstableApi::class)
    private fun applyControllersVisibility(visible: Boolean) {
        val window = requireActivity().window
        val controller = WindowInsetsControllerCompat(window, requireView())

        // Разрешаем transient скрытие (без задержек)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        if (visible) {
            controller.show(WindowInsetsCompat.Type.systemBars())
            binding.playerView.showController()
        } else {
            controller.hide(WindowInsetsCompat.Type.systemBars())
            binding.playerView.hideController()
        }
    }

    fun setupSettingMenu(anchor: View) {
        settingsPopupMenu = PopupMenu(requireContext(), anchor)
        settingsPopupMenu.menuInflater.inflate(
            R.menu.menu_video_player_settings,
            settingsPopupMenu.menu
        )
    }

    fun setupSettingsQualityMenu(anchor: View) {
        settingsQualityPopupMenu = PopupMenu(requireContext(), anchor)
        settingsQualityPopupMenu.menuInflater.inflate(
            R.menu.menu_video_player_quality_list,
            settingsQualityPopupMenu.menu
        )
    }

    fun setupSettingsRateMenu(anchor: View) {
        settingsRatePopupMenu = PopupMenu(requireContext(), anchor)
    }

    private fun showPopupSettingsMenu() {
        settingsPopupMenu.setOnMenuItemClickListener { item: MenuItem ->
            return@setOnMenuItemClickListener when (item.itemId) {
                R.id.menuItemQuality -> {
                    showPopupSettingsQualityMenu()
                    true
                }
                R.id.menuItemRate -> {
                    showPopupSettingsRateMenu()
                    true
                }
                else -> false
            }
        }

        settingsPopupMenu.show()
    }

    private fun showPopupSettingsRateMenu() {
        val currentSpeed = PlaybackSpeed.fromSpeed(viewModel.state.value.playbackSpeed)

        settingsRatePopupMenu.menu.clear()
        PlaybackSpeed.list.forEachIndexed { index, speed ->
            settingsRatePopupMenu.menu.add(Menu.NONE, index, index, speed.title).apply {
                isCheckable = true
                isChecked = speed == currentSpeed
            }
        }

        settingsRatePopupMenu.setOnMenuItemClickListener { item ->
            val selectedSpeed = PlaybackSpeed.list.getOrNull(item.itemId)
            selectedSpeed?.let {
                viewModel.setPlaybackSpeed(it.speed)
            }
            true
        }

        settingsRatePopupMenu.show()
    }

    private fun showPopupSettingsQualityMenu() {
        settingsQualityPopupMenu.setOnMenuItemClickListener { item: MenuItem ->
            return@setOnMenuItemClickListener when (item.itemId) {
                else -> false
            }
        }

        settingsQualityPopupMenu.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeVideo()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseVideo()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopVideo()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
