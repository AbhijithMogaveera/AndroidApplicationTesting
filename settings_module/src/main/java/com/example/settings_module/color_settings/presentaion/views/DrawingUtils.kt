package com.example.settings_module.color_settings.presentaion.views

import android.content.Context
import android.util.TypedValue

internal object DrawingUtils {
    fun dpToPx(c: Context, dipValue: Float): Int {
        val metrics = c.resources.displayMetrics
        val value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
        val res = (value + 0.5).toInt() // Round
        // Ensure at least 1 pixel if val was > 0
        return if (res == 0 && value > 0) 1 else res
    }
}
