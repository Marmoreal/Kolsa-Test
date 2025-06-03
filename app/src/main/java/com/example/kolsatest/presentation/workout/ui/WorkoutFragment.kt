package com.example.kolsatest.presentation.workout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.decode.VideoFrameDecoder
import coil.load
import com.example.kolsatest.databinding.FragmentWorkoutBinding
import com.example.kolsatest.presentation.commonarchitecture.BaseFragment
import com.example.kolsatest.presentation.extension.fitInsetsWithPadding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutFragment : BaseFragment() {

    private lateinit var binding: FragmentWorkoutBinding
    private val viewModel: WorkoutViewModel by viewModels()

    private val args: WorkoutFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getVideoWorkout(args.workout.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
        onBindViewModel()
    }

    private fun setupLayout() {
        binding.root.fitInsetsWithPadding()
        binding.buttonClose.setOnClickListener {
            findNavController().popBackStack()
        }
        setupViewFlipper()
        setupContent()
    }

    private fun onBindViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.stateViewFlipper.setState(state)

                if (binding.stateViewFlipper.currentStateIsData()) {
                    val videoUri = state.videoUrl.toUri()

                    binding.imageView.load(videoUri) {
                        decoderFactory(VideoFrameDecoder.Factory())
                    }
                }
            }
        }
    }

    private fun setupViewFlipper() = with(binding.stateViewFlipper) {
        setRetryMethod {
            viewModel.getVideoWorkout(args.workout.id)
        }
    }

    private fun setupContent() = with(binding) {
        imageView.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionWorkoutFragmentToVideoPlayerFragment(
                    videoUrl = viewModel.state.value.videoUrl,
                )
            )
        }
        textViewType.text = args.workout.type
        textViewTitle.text = args.workout.title
        textViewDuration.text = args.workout.duration
        textViewDescription.text = args.workout.description
    }
}
