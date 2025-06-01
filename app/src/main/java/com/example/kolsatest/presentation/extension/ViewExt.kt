package com.example.kolsatest.presentation.extension

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.fitInsetsWithPadding() {
    val initialPadding = recordInitialPaddingForView(this)

    // Последние ненулевые инсеты
    var lastTopInset = 0
    var lastBottomInset = 0

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        if (systemBars.top > 0) lastTopInset = systemBars.top
        if (systemBars.bottom > 0) lastBottomInset = systemBars.bottom

        view.setPadding(
            initialPadding.left,
            initialPadding.top + lastTopInset,
            initialPadding.right,
            initialPadding.bottom + lastBottomInset,
        )

        WindowInsetsCompat.CONSUMED
    }
    ViewCompat.requestApplyInsets(this)
}

private fun recordInitialPaddingForView(view: View) = Rect(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)
