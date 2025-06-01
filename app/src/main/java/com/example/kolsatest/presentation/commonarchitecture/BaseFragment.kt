package com.example.kolsatest.presentation.commonarchitecture

import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

abstract class BaseFragment() : Fragment() {

    /** Нужно ли показывать system bars */
    open val shouldShowSystemBars: Boolean = true

    /** Используем для управления цвета иконок statusBar*/
    open val isLightStatusBar: Boolean = true

    override fun onResume() {
        super.onResume()
        configureSystemBars()
    }

    private fun configureSystemBars() {
        val window = requireActivity().window
        val controller = WindowInsetsControllerCompat(window, requireView())

        if (shouldShowSystemBars) {
            controller.show(WindowInsetsCompat.Type.systemBars())
            WindowCompat.setDecorFitsSystemWindows(window, true)
        } else {
            controller.hide(WindowInsetsCompat.Type.systemBars())
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        controller.isAppearanceLightStatusBars = isLightStatusBar

        ViewCompat.requestApplyInsets(requireView())
    }
}
