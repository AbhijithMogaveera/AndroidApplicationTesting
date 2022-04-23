package com.example.settings_module.color_settings.presentaion.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Color.*
import android.graphics.Paint.Align
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import com.example.settings_module.R
import kotlin.math.max

@SuppressLint("ClickableViewAccessibility")
class ColorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private var isDirtyView: Boolean = true

    private val huePanelWidthPx = HUE_PANEL_WIDTH_DP.dpToPx
    private val alphaPanelHeightPx = ALPHA_PANEL_HEIGHT_DP.dpToPx
    private val panelSpacingPx = PANEL_SPACING_DP.dpToPx

    private val circleTrackerRadiusPx = CIRCLE_TRACKER_RADIUS_DP.dpToPx
    private val sliderTrackerOffsetPx = SLIDER_TRACKER_OFFSET_DP.dpToPx
    private val sliderTrackerSizePx = SLIDER_TRACKER_SIZE_DP.dpToPx

    private val satValPaint: Paint = Paint()
    private val alphaPaint: Paint = Paint()
    private val satValTrackerPaint: Paint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = DrawingUtils.dpToPx(context, 2f).toFloat()
            isAntiAlias = true
        }
    }
    private val alphaTextPaint: Paint by lazy {
        Paint().apply {
            color = -0xe3e3e4
            textSize = DrawingUtils.dpToPx(context, 14f).toFloat()
            isAntiAlias = true
            textAlign = Align.CENTER
            isFakeBoldText = true
        }
    }
    private val hueAlphaTrackerPaint: Paint by lazy {
        Paint().apply {
            color = sliderTrackerColor
            style = Paint.Style.STROKE
            strokeWidth = DrawingUtils.dpToPx(context, 2f).toFloat()
            isAntiAlias = true
        }
    }
    private val borderPaint: Paint = Paint()

    private lateinit var valShader: Shader
    private lateinit var satShader: Shader
    private lateinit var alphaShader: Shader

    private lateinit var satValBackgroundCache: BitmapCache
    private lateinit var hueBackgroundCache: BitmapCache

    private var alpha = 0xff
    private var hue = 360f
    private var sat = 0f
    private var value = 0f
    var showAlphaPanel = false
        set(value) {
            if (field == value)
                return
            field = value
            isDirtyView = true
            requestLayout()
        }

    private var alphaSliderText: String = ""
        set(value) {
            field = value
            invalidate()
        }

    var sliderTrackerColor = DEFAULT_SLIDER_COLOR
        set(value) {
            field = value
            hueAlphaTrackerPaint.color = sliderTrackerColor
            invalidate()
        }
    var borderColor = DEFAULT_BORDER_COLOR
        set(value) {
            field = value
            invalidate()
        }

    private val mMinFingerTrackingPadding =
        resources.getDimensionPixelSize(R.dimen.cpv_required_padding)

    private val drawingRect: Rect = Rect()
    private val saturationRect: Rect = Rect()
    private val hueRect: Rect = Rect()
    private val alphaRect: Rect = Rect()

    private var startTouchPoint: Point? = null

    private lateinit var alphaPatternDrawable: AlphaPatternDrawable
    private var onColorChangedListener: OnColorChangedListener? = null

    init {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerView)
        showAlphaPanel = a.getBoolean(R.styleable.ColorPickerView_cpv_alphaChannelVisible, false)
        alphaSliderText = a.getString(R.styleable.ColorPickerView_cpv_alphaChannelText) ?: ""
        sliderTrackerColor = a.getColor(R.styleable.ColorPickerView_cpv_sliderColor, -0x424243)
        borderColor = a.getColor(R.styleable.ColorPickerView_cpv_borderColor, -0x919192)
        a.recycle()

        applyThemeColors(context)

        isFocusable = true
        isFocusableInTouchMode = true
    }

    public override fun onSaveInstanceState(): Parcelable {
        val state = Bundle()
        state.putParcelable("instanceState", super.onSaveInstanceState())
        state.putInt("alpha", alpha)
        state.putFloat("hue", hue)
        state.putFloat("sat", sat)
        state.putFloat("val", value)
        state.putBoolean("show_alpha", showAlphaPanel)
        state.putString("alpha_text", alphaSliderText)
        return state
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        var state: Parcelable? = state
        if (state is Bundle) {
            val bundle = state
            alpha = bundle.getInt("alpha")
            hue = bundle.getFloat("hue")
            sat = bundle.getFloat("sat")
            value = bundle.getFloat("val")
            showAlphaPanel = bundle.getBoolean("show_alpha")
            alphaSliderText = bundle.getString("alpha_text") ?: ""
            state = bundle.getParcelable("instanceState")
        }
        super.onRestoreInstanceState(state)
    }

    override fun onDraw(canvas: Canvas) {
        if (drawingRect.width() <= 0 || drawingRect.height() <= 0) {
            return
        }
        drawHUEPanel(canvas)
        drawSaturationValPanel(canvas)
        drawAlphaPanel(canvas)
        isDirtyView = false
    }

    private fun drawSaturationValPanel(canvas: Canvas) {
       saturationRect
        if (BORDER_WIDTH_PX > 0) {
            borderPaint.color = borderColor
            canvas.drawRect(
                drawingRect.left.toFloat(),
                drawingRect.top.toFloat(),
                (saturationRect.right + BORDER_WIDTH_PX).toFloat(),
                (saturationRect.bottom + BORDER_WIDTH_PX).toFloat(),
                borderPaint
            )
        }
        if (isDirtyView) {
            valShader = LinearGradient(
                //x0
                saturationRect.left.toFloat(),
                //y0
                saturationRect.top.toFloat(),
                //x1
                saturationRect.right.toFloat(),
                //y1
                saturationRect.bottom.toFloat(),
                "#000000".toColorInt(),
                "#ffffff".toColorInt(),
                Shader.TileMode.CLAMP
            )
        }

        if (isDirtyView || satValBackgroundCache.value != hue) {
            satValBackgroundCache = BitmapCache(
                bitmap = Bitmap.createBitmap(saturationRect.width(), saturationRect.height(), Bitmap.Config.ARGB_8888),
            )
            val rgb = HSVToColor(floatArrayOf(hue, 1f, 1f))
            satShader = LinearGradient(
                saturationRect.left.toFloat(),
                saturationRect.top.toFloat(),
                saturationRect.right.toFloat(),
                saturationRect.top.toFloat(),
                "#ffffff".toColorInt(),
                rgb,
                Shader.TileMode.CLAMP
            )
            satValPaint.shader = ComposeShader(valShader, satShader, PorterDuff.Mode.MULTIPLY)
            satValBackgroundCache.canvas.drawRect(
                0f,
                0f,
                satValBackgroundCache.bitmap.width.toFloat(),
                satValBackgroundCache.bitmap.height.toFloat(),
                satValPaint
            )
            satValBackgroundCache.value = hue
        }

        canvas.drawBitmap(satValBackgroundCache.bitmap, null, saturationRect, null)
        val p = saturationValueToPoint(sat, value)
        satValTrackerPaint.color = "#ff000000".toColorInt()
        canvas.drawCircle(
            p.x.toFloat(), p.y.toFloat(), (circleTrackerRadiusPx - DrawingUtils.dpToPx(
                context, 1f
            )).toFloat(), satValTrackerPaint
        )
        satValTrackerPaint.color = "#ffdddddd".toColorInt()
        canvas.drawCircle(
            p.x.toFloat(),
            p.y.toFloat(),
            circleTrackerRadiusPx.toFloat(),
            satValTrackerPaint
        )
    }

    private fun drawHUEPanel(canvas: Canvas) {
        if (BORDER_WIDTH_PX > 0) {
            borderPaint.color = borderColor
            canvas.drawRect(
                (hueRect.left - BORDER_WIDTH_PX).toFloat(),
                (hueRect.top - BORDER_WIDTH_PX).toFloat(),
                (hueRect.right + BORDER_WIDTH_PX).toFloat(),
                (hueRect.bottom + BORDER_WIDTH_PX).toFloat(),
                borderPaint
            )
        }
        if (isDirtyView) {
            hueBackgroundCache = BitmapCache(
                bitmap = Bitmap.createBitmap(
                    hueRect.width(),
                    hueRect.height(),
                    Bitmap.Config.ARGB_8888
                )
            )
            val hueColors = IntArray((hueRect.height() + 0.5f).toInt())
            var h = 360f
            for (i in hueColors.indices) {
                hueColors[i] = HSVToColor(floatArrayOf(h, 1f, 1f))
                h -= 360f / hueColors.size
            }
            val linePaint = Paint()
            linePaint.strokeWidth = 0f
            hueColors.indices.forEach { i ->
                linePaint.color = hueColors[i]
                hueBackgroundCache.canvas.drawLine(
                    0f,
                    i.toFloat(),
                    hueBackgroundCache.bitmap.width.toFloat(),
                    i.toFloat(),
                    linePaint
                )
            }
        }
        canvas.drawBitmap(hueBackgroundCache.bitmap, null, hueRect, null)

        val p = hueToPoint(hue)
        val rectF = RectF()
        rectF.left = (hueRect.left - sliderTrackerOffsetPx).toFloat()
        rectF.right = (hueRect.right + sliderTrackerOffsetPx).toFloat()
        rectF.top = (p.y - sliderTrackerSizePx / 2).toFloat()
        rectF.bottom = (p.y + sliderTrackerSizePx / 2).toFloat()

        canvas.drawRoundRect(rectF, 2f, 2f, hueAlphaTrackerPaint)
    }

    private fun drawAlphaPanel(canvas: Canvas) {
        if (!showAlphaPanel)
            return
        val rect: Rect = alphaRect
        if (BORDER_WIDTH_PX > 0) {
            borderPaint.color = borderColor
            canvas.drawRect(
                (rect.left - BORDER_WIDTH_PX).toFloat(),
                (rect.top - BORDER_WIDTH_PX).toFloat(),
                (rect.right + BORDER_WIDTH_PX).toFloat(),
                (rect.bottom + BORDER_WIDTH_PX).toFloat(),
                borderPaint
            )
        }
        alphaPatternDrawable.draw(canvas)
        val hsv = floatArrayOf(hue, sat, value)
        val color = HSVToColor(hsv)
        val aColor = HSVToColor(0, hsv)
        alphaShader = LinearGradient(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.top.toFloat(),
            color,
            aColor,
            Shader.TileMode.CLAMP
        )
        alphaPaint.shader = alphaShader
        canvas.drawRect(rect, alphaPaint)
        if (alphaSliderText.isNotEmpty()) {
            canvas.drawText(
                alphaSliderText,
                rect.centerX().toFloat(),
                (rect.centerY() + DrawingUtils.dpToPx(context, 4f)).toFloat(),
                alphaTextPaint
            )
        }
        val point = alphaToPoint(alpha)
        val rectF = RectF()
        rectF.left = (point.x - sliderTrackerSizePx / 2).toFloat()
        rectF.right = (point.x + sliderTrackerSizePx / 2).toFloat()
        rectF.top = (rect.top - sliderTrackerOffsetPx).toFloat()
        rectF.bottom = (rect.bottom + sliderTrackerOffsetPx).toFloat()
        canvas.drawRoundRect(rectF, 2f, 2f, hueAlphaTrackerPaint)
    }

    private fun applyThemeColors(c: Context) {
        val value = TypedValue()
        val a = c.obtainStyledAttributes(value.data, intArrayOf(android.R.attr.textColorSecondary))
        if (borderColor == DEFAULT_BORDER_COLOR) {
            borderColor = a.getColor(0, DEFAULT_BORDER_COLOR)
        }
        if (sliderTrackerColor == DEFAULT_SLIDER_COLOR) {
            sliderTrackerColor = a.getColor(0, DEFAULT_SLIDER_COLOR)
        }
        a.recycle()
    }


    private fun hueToPoint(hue: Float): Point {
        val rect = hueRect
        val height = rect.height().toFloat()
        val p = Point()
        p.y = (height - hue * height / 360f + rect.top).toInt()
        p.x = rect.left
        return p
    }

    private fun saturationValueToPoint(sat: Float, value: Float): Point {
        val height = saturationRect.height().toFloat()
        val width = saturationRect.width().toFloat()
        val p = Point()
        p.x = (sat * width + saturationRect.left).toInt()
        p.y = ((1f - value) * height + saturationRect.top).toInt()
        return p
    }

    private fun alphaToPoint(alpha: Int): Point {
        val rect = alphaRect
        val width = rect.width().toFloat()
        val p = Point()
        p.x = (width - alpha * width / 0xff + rect.left).toInt()
        p.y = rect.top
        return p
    }

    private fun pointToSatVal(x: Float, y: Float): FloatArray {
        var x = x
        var y = y
        saturationRect
        val result = FloatArray(2)
        val width = saturationRect.width().toFloat()
        val height = saturationRect.height().toFloat()
        x = when {
            x < saturationRect.left -> {
                0f
            }
            x > saturationRect.right -> {
                width
            }
            else -> {
                x - saturationRect.left
            }
        }
        y = when {
            y < saturationRect.top -> {
                0f
            }
            y > saturationRect.bottom -> {
                height
            }
            else -> {
                y - saturationRect.top
            }
        }
        result[0] = 1f / width * x
        result[1] = 1f - 1f / height * y
        return result
    }

    private fun pointToHue(y: Float): Float {
        val rect = hueRect
        val height = rect.height().toFloat()
        val newY = when {
            y < rect.top -> {
                0f
            }
            y > rect.bottom -> {
                height
            }
            else -> {
                y - rect.top
            }
        }
        return 360f - newY * 360f / height
    }

    private fun pointToAlpha(x: Int): Int {
        var x = x
        val rect = alphaRect
        val width = rect.width()
        x = when {
            x < rect.left -> {
                0
            }
            x > rect.right -> {
                width
            }
            else -> {
                x - rect.left
            }
        }
        return 0xff - x * 0xff / width
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var update = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startTouchPoint = Point(event.x.toInt(), event.y.toInt())
                update = moveTrackersIfNeeded(event)
            }
            MotionEvent.ACTION_MOVE -> update = moveTrackersIfNeeded(event)
            MotionEvent.ACTION_UP -> {
                startTouchPoint = null
                update = moveTrackersIfNeeded(event)
            }
        }
        if (update) {
            onColorChangedListener?.onColorChanged(HSVToColor(alpha, floatArrayOf(hue, sat, value)))
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun moveTrackersIfNeeded(event: MotionEvent): Boolean {
        if (startTouchPoint == null) {
            return false
        }
        var update = false
        val startX = startTouchPoint!!.x
        val startY = startTouchPoint!!.y
        when {
            hueRect.contains(startX, startY) -> {
                hue = pointToHue(event.y)
                update = true
            }
            saturationRect.contains(startX, startY) -> {
                val result = pointToSatVal(event.x, event.y)
                sat = result[0]
                value = result[1]
                update = true
            }
            alphaRect.contains(startX, startY) -> {
                alpha = pointToAlpha(event.x.toInt())
                update = true
            }
        }
        return update
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val finalWidth: Int
        val finalHeight: Int
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthAllowed = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        val heightAllowed = MeasureSpec.getSize(heightMeasureSpec) - paddingBottom - paddingTop
        if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY) {
            if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
                var h = widthAllowed - panelSpacingPx - huePanelWidthPx
                if (showAlphaPanel) {
                    h += panelSpacingPx + alphaPanelHeightPx
                }
                finalHeight = if (h > heightAllowed) {
                    heightAllowed
                } else {
                    h
                }
                finalWidth = widthAllowed
            } else if (heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY) {
                var w = heightAllowed + panelSpacingPx + huePanelWidthPx
                if (showAlphaPanel) {
                    w -= panelSpacingPx + alphaPanelHeightPx
                }
                finalWidth = if (w > widthAllowed) {
                    widthAllowed
                } else {
                    w
                }
                finalHeight = heightAllowed
            } else {
                finalWidth = widthAllowed
                finalHeight = heightAllowed
            }
        } else {

            var widthNeeded = heightAllowed + panelSpacingPx + huePanelWidthPx
            var heightNeeded = widthAllowed - panelSpacingPx - huePanelWidthPx
            if (showAlphaPanel) {
                widthNeeded -= panelSpacingPx + alphaPanelHeightPx
                heightNeeded += panelSpacingPx + alphaPanelHeightPx
            }
            var widthOk = false
            var heightOk = false
            if (widthNeeded <= widthAllowed) {
                widthOk = true
            }
            if (heightNeeded <= heightAllowed) {
                heightOk = true
            }
            if (widthOk && heightOk) {
                finalWidth = widthAllowed
                finalHeight = heightNeeded
            } else if (!heightOk && widthOk) {
                finalHeight = heightAllowed
                finalWidth = widthNeeded
            } else if (!widthOk && heightOk) {
                finalHeight = heightNeeded
                finalWidth = widthAllowed
            } else {
                finalHeight = heightAllowed
                finalWidth = widthAllowed
            }
        }
        setMeasuredDimension(
            finalWidth + paddingLeft + paddingRight,
            finalHeight + paddingTop + paddingBottom
        )
    }

    private val preferredWidth: Int
        get() {
            val width = DrawingUtils.dpToPx(context, 200f)
            return width + huePanelWidthPx + panelSpacingPx
        }
    private val preferredHeight: Int
        get() {
            var height = DrawingUtils.dpToPx(context, 200f)
            if (showAlphaPanel) {
                height += panelSpacingPx + alphaPanelHeightPx
            }
            return height
        }

    override fun getPaddingTop(): Int {
        return max(super.getPaddingTop(), mMinFingerTrackingPadding)
    }

    override fun getPaddingBottom(): Int {
        return max(super.getPaddingBottom(), mMinFingerTrackingPadding)
    }

    override fun getPaddingLeft(): Int {
        return max(super.getPaddingLeft(), mMinFingerTrackingPadding)
    }

    override fun getPaddingRight(): Int {
        return max(super.getPaddingRight(), mMinFingerTrackingPadding)
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        isDirtyView
        drawingRect.apply {
            left = paddingLeft
            right = newWidth - paddingRight
            top = paddingTop
            bottom = newHeight - paddingBottom
        }
        isDirtyView = true
        setUpSatValRect()
        setUpHueRect()
        setUpAlphaRect()
    }

    private fun setUpSatValRect() {
        var bottom = drawingRect.bottom - BORDER_WIDTH_PX
        if (showAlphaPanel) {
            bottom -= alphaPanelHeightPx + panelSpacingPx
        }
        saturationRect.apply {
            this.left = drawingRect.left + BORDER_WIDTH_PX
            this.right = drawingRect.right - BORDER_WIDTH_PX - panelSpacingPx - huePanelWidthPx
            this.top = drawingRect.top + BORDER_WIDTH_PX
            this.bottom = bottom
        }
    }

    private fun setUpHueRect() {
        hueRect.apply {
            left = drawingRect.right - huePanelWidthPx + BORDER_WIDTH_PX
            top = drawingRect.top + BORDER_WIDTH_PX
            bottom =
                drawingRect.bottom - BORDER_WIDTH_PX - if (showAlphaPanel) panelSpacingPx + alphaPanelHeightPx else 0
            right = drawingRect.right - BORDER_WIDTH_PX
        }
    }

    private fun setUpAlphaRect() {

        if (!showAlphaPanel)
            return

        alphaRect.apply {
            left = drawingRect.left + BORDER_WIDTH_PX
            top = drawingRect.bottom - alphaPanelHeightPx + BORDER_WIDTH_PX
            bottom = drawingRect.bottom - BORDER_WIDTH_PX
            right = drawingRect.right - BORDER_WIDTH_PX
        }
        alphaPatternDrawable = AlphaPatternDrawable(DrawingUtils.dpToPx(context, 4f))
        alphaPatternDrawable.bounds = alphaRect
    }

    fun setOnColorChangedListener(listener: OnColorChangedListener?) {
        onColorChangedListener = listener
    }

    var color: Int = 0
        get() = HSVToColor(alpha, floatArrayOf(hue, sat, value))
        set(color) {
            field = color
            setColor(color, false)
        }

    private fun setColor(color: Int, broadCastToCallBack: Boolean) {
        val hsv = FloatArray(3)
        RGBToHSV(
            red(color),
            green(color),
            blue(color),
            hsv
        )
        hsv.apply {
            this@ColorPickerView.alpha = alpha(color)
            hue = this[0]
            sat = this[1]
            value = this[2]
            if (broadCastToCallBack) {
                onColorChangedListener?.onColorChanged(HSVToColor(this@ColorPickerView.alpha, this))
            }
            invalidate()
        }

    }

    fun setAlphaSliderText(res: Int) {
        val text = context.getString(res)
        alphaSliderText = text
    }


    interface OnColorChangedListener {
        fun onColorChanged(newColor: Int)
    }

    private data class BitmapCache(
        var bitmap: Bitmap,
        var canvas: Canvas = Canvas(bitmap),
        var value: Float = 0f
    )

    companion object {
        private const val DEFAULT_BORDER_COLOR = -0x919192
        private const val DEFAULT_SLIDER_COLOR = -0x424243
        private const val HUE_PANEL_WIDTH_DP = 30
        private const val ALPHA_PANEL_HEIGHT_DP = 20
        private const val PANEL_SPACING_DP = 10
        private const val CIRCLE_TRACKER_RADIUS_DP = 5
        private const val SLIDER_TRACKER_SIZE_DP = 4
        private const val SLIDER_TRACKER_OFFSET_DP = 2
        private const val BORDER_WIDTH_PX = 1
    }


}