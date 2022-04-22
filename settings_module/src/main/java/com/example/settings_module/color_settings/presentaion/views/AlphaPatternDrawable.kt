package com.example.settings_module.color_settings.presentaion.views

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.ceil

internal class AlphaPatternDrawable(private val rectangleSize: Int) : Drawable() {
    private val paint = Paint()
    private val paintWhite = Paint()
    private val paintGray = Paint()
    private var numRectanglesHorizontal = 0
    private var numRectanglesVertical = 0

    private lateinit var bitmap: Bitmap
    override fun draw(canvas: Canvas) {
        if (this::bitmap.isInitialized && !bitmap.isRecycled) {
            canvas.drawBitmap(bitmap, null, bounds, paint)
        }
    }

    override fun getOpacity(): Int {
        return 0
    }

    override fun setAlpha(alpha: Int) {
        throw UnsupportedOperationException("Alpha is not supported by this drawable.")
    }

    override fun setColorFilter(cf: ColorFilter?) {
        throw UnsupportedOperationException("ColorFilter is not supported by this drawable.")
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val height = bounds.height()
        val width = bounds.width()
        numRectanglesHorizontal = ceil((width / rectangleSize).toDouble()).toInt()
        numRectanglesVertical = ceil((height / rectangleSize).toDouble()).toInt()
        generatePatternBitmap()
    }

    private fun generatePatternBitmap() {
        if (bounds.width() <= 0 || bounds.height() <= 0) {
            return
        }
        bitmap =
            Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888) ?: return
        val canvas = Canvas(bitmap)
        val r = Rect()
        var verticalStartWhite = true
        for (i in 0..numRectanglesVertical) {
            var isWhite = verticalStartWhite
            for (j in 0..numRectanglesHorizontal) {
                r.top = i * rectangleSize
                r.left = j * rectangleSize
                r.bottom = r.top + rectangleSize
                r.right = r.left + rectangleSize
                canvas.drawRect(r, if (isWhite) paintWhite else paintGray)
                isWhite = !isWhite
            }
            verticalStartWhite = !verticalStartWhite
        }
    }

    init {
        paintWhite.color = -0x1
        paintGray.color = -0x343435
    }
}