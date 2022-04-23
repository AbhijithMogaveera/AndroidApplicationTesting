package com.example.settings_module.color_settings.presentaion.views.color_picker_view

import android.graphics.Rect

/**
 * Mapping y from = [0 to width] to =[0 to 360]
 * */
internal fun Rect.pointToHue(y: Float): Float {
    val height = height().toFloat()
    val newY = when {
        y < top -> {
            0f
        }
        y > bottom -> {
            height
        }
        else -> {
            y - top
        }
    }
    return 360f - newY * 360f / height
}

/**
 * @param x will be mapped to saturation
 * @param y will be mapped to value
 * <br> old java style function 😛
 * */
internal fun Rect.pointToSatAndVal(x: Float, y: Float): FloatArray {
    val result = FloatArray(2)
    val saturationRectWidth = this.width().toFloat()
    val saturationRectHeight = this.height().toFloat()
    val pointX = when {
        x < this.left -> {
            0f
        }
        x > this.right -> {
            saturationRectWidth
        }
        else -> {
            x - this.left
        }
    }
    val pointY = when {
        y < this.top -> {
            0f
        }
        y > this.bottom -> {
            saturationRectHeight
        }
        else -> {
            y - this.top
        }
    }
    result[0] = 1f / saturationRectWidth * pointX
    result[1] = 1f / saturationRectHeight * pointY
    return result
}

internal fun Rect.pointToAlpha(x: Int): Int {
        var calculateAlpha = x
        val width = width()
        calculateAlpha = when {
            calculateAlpha < left -> {
                0
            }
            calculateAlpha > right -> {
                width
            }
            else -> {
                calculateAlpha - left
            }
        }
        return 0xff - (calculateAlpha * 0xff / width)
    }