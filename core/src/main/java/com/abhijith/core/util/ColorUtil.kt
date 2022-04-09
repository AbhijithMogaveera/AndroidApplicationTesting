package com.abhijith.core.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

fun getContrastColor(
    @ColorInt victimColor: Int
): Int {
    val whiteContrast = ColorUtils.calculateContrast(
        Color.WHITE,
        victimColor
    )

    val blackContrast = ColorUtils.calculateContrast(
        Color.BLACK,
        victimColor
    )

    return if (whiteContrast > blackContrast)
        android.graphics.Color.WHITE
    else
        android.graphics.Color.BLACK
}