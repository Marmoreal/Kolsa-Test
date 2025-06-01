package com.example.kolsatest.presentation.extension

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

data class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

fun View.fitInsetsWithPadding() {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.setPadding(
            initialPadding.left,
            initialPadding.top + systemBars.top,
            initialPadding.right,
            initialPadding.bottom + systemBars.bottom
        )

        WindowInsetsCompat.CONSUMED
    }
    ViewCompat.requestApplyInsets(this)
}
