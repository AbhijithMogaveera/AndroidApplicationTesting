package com.example.settings_module.color_settings.presentaion.views.color_picker_view

import android.graphics.Bitmap
import android.graphics.Canvas

internal data class BitmapCache(
    var bitmap: Bitmap,
    var canvas: Canvas = Canvas(bitmap),
    var value: Float = 0f
)