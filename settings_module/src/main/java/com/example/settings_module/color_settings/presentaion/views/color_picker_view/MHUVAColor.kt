package com.example.settings_module.color_settings.presentaion.views.color_picker_view

import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect

internal object HUVAColor {
    private const val HUE_MAX = 360f
    private const val ALPHA_MAX = 0xff

    var hue: Float = HUE_MAX
    var alpha: Int = ALPHA_MAX
    var sat: Float = 0f
    var value: Float = 0f

    fun asFloatArray() = floatArrayOf(hue, sat, value)

    fun getRGBColorInt() = Color.HSVToColor(asFloatArray())

    fun getARGBColorInt() = Color.HSVToColor(alpha, asFloatArray())

    fun hueToPoint(hueRect: Rect): Point {
        return hueRect.let {
            Point().apply {
                y = (it.height() - hue * it.height() / HUE_MAX + it.top).toInt()
                x = it.left
            }
        }
    }

    fun pointToHue(){}

    fun alphaToPoint(alphaRect: Rect): Point {
        return alphaRect.let {
            Point().apply {
                x = it.width() - alpha * it.width() / ALPHA_MAX + it.left
                y = it.top
            }
        }
    }

}