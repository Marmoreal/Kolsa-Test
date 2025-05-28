package com.example.kolsatest.presentation.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kolsatest.databinding.WorkoutsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutsFragment : Fragment() {

    private lateinit var binding: WorkoutsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WorkoutsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}
