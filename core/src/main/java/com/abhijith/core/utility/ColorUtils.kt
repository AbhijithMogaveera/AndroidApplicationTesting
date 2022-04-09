package com.abhijith.core.utility

import android.graphics.Color
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils
import java.util.*

object ColorUtils {
    const val TAG = "ColorUtils"
    private const val ARGB_FORMAT = "#%08X"
    private const val RGB_FORMAT = "#%06X"
    private const val HSV_FORMAT = "hsv[%.2f, %.2f, %.2f]"

    /**
     * Returns the complimentary color. (Alpha channel remains intact)
     *
     * @param color An ARGB color to return the compliment of
     * @return An ARGB of compliment color
     */
    @ColorInt
    fun getCompliment(@ColorInt color: Int): Int {
        // get existing colors
        val alpha = Color.alpha(color)
        var red = Color.red(color)
        var blue = Color.blue(color)
        var green = Color.green(color)

        // find compliments
        red = red.inv() and 0xff
        blue = blue.inv() and 0xff
        green = green.inv() and 0xff
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * Convert a color to an ARGB string.
     *
     * i.e. [Color.BLUE] returns the string `#FF0000FF`.
     * @param color a color
     * @return the color as string, or null if `color` is `null`.
     */
    fun toARGBString(@ColorInt color: Int): String {
        return String.format(Locale.US, ARGB_FORMAT, color)
    }

    /**
     * Convert a color to an RGB string.
     *
     * i.e. [Color.BLUE] returns the string `#0000FF`.
     * @param color a color
     * @return the color as string, or null if `color` is `null`.
     */
    fun toRGBString(@ColorInt color: Int): String {
        return String.format(Locale.US, RGB_FORMAT, 0xFFFFFF and color)
    }

    /**
     * Convert a color to an HSV string.
     *
     * i.e. [Color.BLUE] returns the string `hsv[240.00, 1.00, 1.00]`.
     * @param color a color
     * @return the color as a HSV string.
     */
    fun toHsvString(color: Int): String {
        return toHsvString(toHSV(color))
    }

    /**
     * Convert a HSV `float[]` to an HSV string.
     *
     * i.e. [Color.BLUE] returns the string `hsv[240.00, 1.00, 1.00]`.
     * @param hsv a HSV `float[]`
     * @return the HSV as a HSV string.
     */
    fun toHsvString(hsv: FloatArray): String {
        return String.format(Locale.US, HSV_FORMAT, hsv[0], hsv[1], hsv[2])
    }

    fun toHSV(@ColorInt color: Int): FloatArray {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        return hsv
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     *
     * @param color A color
     * @param alpha The new alpha value for the color, in the range 0 - 255.
     * @return A the given `color`, with the given `alpha`
     */
    @ColorInt
    fun setAlphaComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) alpha: Int
    ): Int {
        return ColorUtils.setAlphaComponent(color, alpha)
    }

    /**
     * Returns the luminance of a color in the range {code 0d} for darkest black, to `1d` for brightest white.
     *
     *
     * Formula defined here: http://www.w3.org/TR/2008/REC-WCAG20-20081211/#relativeluminancedef
     *
     * @param color A color to test
     * @return the luminance of a color
     */
    fun calculateLuminance(@ColorInt color: Int): Double {
        return ColorUtils.calculateLuminance(color)
    }

    /**
     * Returns the contrast ratio between `foreground` and `background`.
     * `background` must be opaque.
     *
     *
     * Formula defined [here](http://www.w3.org/TR/2008/REC-WCAG20-20081211/#contrast-ratiodef).
     *
     * @param foreground A color
     * @param background A color
     * @return the contrast ratio between `foreground` and `background`.
     */
    fun calculateContrast(@ColorInt foreground: Int, @ColorInt background: Int): Double {
        return ColorUtils.calculateContrast(foreground, background)
    }

    /**
     * Calculates the minimum alpha value which can be applied to `foreground` so that would
     * have a contrast value of at least `minContrastRatio` when compared to
     * `background`.
     *
     * @param foreground       the foreground color.
     * @param background       the background color. Should be opaque.
     * @param minContrastRatio the minimum contrast ratio.
     * @return the alpha value in the range 0-255, or -1 if no value could be calculated.
     */
    fun calculateMinimumAlpha(
        @ColorInt foreground: Int,
        @ColorInt background: Int,
        minContrastRatio: Float
    ): Int {
        return ColorUtils.calculateMinimumAlpha(foreground, background, minContrastRatio)
    }

    fun opposeColor(ColorToInvert: Int): Int {
        val RGBMAX = 255
        val hsv = FloatArray(3)
        Log.i("HSV_H", "Start Color=$ColorToInvert")
        Color.RGBToHSV(
            Color.red(ColorToInvert),
            RGBMAX - Color.green(ColorToInvert),
            Color.blue(ColorToInvert),
            hsv
        )
        Log.i("HSV_H", "Hue=" + hsv[0])
        Log.i("HSV_H", "Saturation=" + hsv[1])
        Log.i("HSV_H", "Value=" + hsv[2])
        var H: Float = (hsv[0] + 0.5).toFloat()
        if (H > 1) H -= 1f
        Log.i("HSV_H", "Hue2=$H")
        Log.i("HSV_H", "Color=" + Color.HSVToColor(hsv))
        return Color.HSVToColor(hsv)
    }


    fun getComplementaryColor(colorToInvert: Int): Int {
        val hsv = FloatArray(3)
        Color.RGBToHSV(
            Color.red(colorToInvert), Color.green(colorToInvert),
            Color.blue(colorToInvert), hsv
        )
        hsv[0] = (hsv[0] + 180) % 360
        return Color.HSVToColor(hsv)
    }

    fun getContrastVersionForColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.RGBToHSV(
            Color.red(color), Color.green(color), Color.blue(color),
            hsv
        )
        if (hsv[2] < 0.5) {
            hsv[2] = 0.7f
        } else {
            hsv[2] = 0.3f
        }
        hsv[1] = hsv[1] * 0.2f
        return Color.HSVToColor(hsv)
    }
}