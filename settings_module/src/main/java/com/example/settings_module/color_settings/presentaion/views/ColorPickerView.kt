package com.example.settings_module.color_settings.presentaion.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.example.settings_module.R
import kotlin.math.max
import kotlin.math.roundToInt

@SuppressLint("ClickableViewAccessibility")
class ColorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var huePanelWidthPx = 0
    private var alphaPanelHeightPx = 0
    private var panelSpacingPx = 0
    private var circleTrackerRadiusPx = 0
    private var sliderTrackerOffsetPx = 0
    private var sliderTrackerSizePx = 0

    private var satValPaint: Paint? = null
    private var satValTrackerPaint: Paint? = null
    private var alphaPaint: Paint? = null
    private var alphaTextPaint: Paint? = null
    private var hueAlphaTrackerPaint: Paint? = null
    private var borderPaint: Paint? = null

    private var valShader: Shader? = null
    private var satShader: Shader? = null
    private var alphaShader: Shader? = null

    private var satValBackgroundCache: BitmapCache? = null
    private var hueBackgroundCache: BitmapCache? = null

    private var alpha = 0xff
    private var hue = 360f
    private var sat = 0f
    private var value = 0f
    private var showAlphaPanel = false

    private var alphaSliderText: String? = null

    private var sliderTrackerColor = DEFAULT_SLIDER_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR

    private var mMinFingerTrackingPadding = 0

    private val drawingRect: Rect = Rect()
    private var satValRect: Rect? = null
    private var hueRect: Rect? = null
    private var alphaRect: Rect? = null

    private var startTouchPoint: Point? = null

    private var alphaPatternDrawable: AlphaPatternDrawable? = null
    private var onColorChangedListener: OnColorChangedListener? = null

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
            alphaSliderText = bundle.getString("alpha_text")
            state = bundle.getParcelable("instanceState")
        }
        super.onRestoreInstanceState(state)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerView)
        showAlphaPanel = a.getBoolean(R.styleable.ColorPickerView_cpv_alphaChannelVisible, false)
        alphaSliderText = a.getString(R.styleable.ColorPickerView_cpv_alphaChannelText)
        sliderTrackerColor = a.getColor(R.styleable.ColorPickerView_cpv_sliderColor, -0x424243)
        borderColor = a.getColor(R.styleable.ColorPickerView_cpv_borderColor, -0x919192)
        a.recycle()
        applyThemeColors(context)
        huePanelWidthPx = DrawingUtils.dpToPx(getContext(), HUE_PANEL_WIDTH_DP.toFloat())
        alphaPanelHeightPx = DrawingUtils.dpToPx(getContext(), ALPHA_PANEL_HEIGHT_DP.toFloat())
        panelSpacingPx = DrawingUtils.dpToPx(getContext(), PANEL_SPACING_DP.toFloat())
        circleTrackerRadiusPx =
            DrawingUtils.dpToPx(getContext(), CIRCLE_TRACKER_RADIUS_DP.toFloat())
        sliderTrackerSizePx = DrawingUtils.dpToPx(getContext(), SLIDER_TRACKER_SIZE_DP.toFloat())
        sliderTrackerOffsetPx =
            DrawingUtils.dpToPx(getContext(), SLIDER_TRACKER_OFFSET_DP.toFloat())
        mMinFingerTrackingPadding = resources.getDimensionPixelSize(R.dimen.cpv_required_padding)
        initPaintTools()

        //Needed for receiving trackball motion events.
        isFocusable = true
        isFocusableInTouchMode = true
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

    private fun initPaintTools() {
        satValPaint = Paint()
        satValTrackerPaint = Paint()
        hueAlphaTrackerPaint = Paint()
        alphaPaint = Paint()
        alphaTextPaint = Paint()
        borderPaint = Paint()
        satValTrackerPaint!!.style = Paint.Style.STROKE
        satValTrackerPaint!!.strokeWidth = DrawingUtils.dpToPx(context, 2f).toFloat()
        satValTrackerPaint!!.isAntiAlias = true
        hueAlphaTrackerPaint!!.color = sliderTrackerColor
        hueAlphaTrackerPaint!!.style = Paint.Style.STROKE
        hueAlphaTrackerPaint!!.strokeWidth = DrawingUtils.dpToPx(context, 2f).toFloat()
        hueAlphaTrackerPaint!!.isAntiAlias = true
        alphaTextPaint!!.color = -0xe3e3e4
        alphaTextPaint!!.textSize = DrawingUtils.dpToPx(context, 14f).toFloat()
        alphaTextPaint!!.isAntiAlias = true
        alphaTextPaint!!.textAlign = Align.CENTER
        alphaTextPaint!!.isFakeBoldText = true
    }

    override fun onDraw(canvas: Canvas) {
        if (drawingRect!!.width() <= 0 || drawingRect!!.height() <= 0) {
            return
        }
        drawSatValPanel(canvas)
        drawHuePanel(canvas)
        drawAlphaPanel(canvas)
    }

    private fun drawSatValPanel(canvas: Canvas) {
        val rect = satValRect
        if (BORDER_WIDTH_PX > 0) {
            borderPaint!!.color = borderColor
            canvas.drawRect(
                drawingRect.left.toFloat(),
                drawingRect.top.toFloat(),
                (rect!!.right + BORDER_WIDTH_PX).toFloat(),
                (rect.bottom + BORDER_WIDTH_PX).toFloat(),
                borderPaint!!
            )
        }
        if (valShader == null) {
            valShader = LinearGradient(
                rect!!.left.toFloat(),
                rect.top.toFloat(),
                rect.left.toFloat(),
                rect.bottom.toFloat(),
                -0x1,
                -0x1000000,
                Shader.TileMode.CLAMP
            )
        }

        if (satValBackgroundCache == null || satValBackgroundCache!!.value != hue) {
            if (satValBackgroundCache == null) {
                satValBackgroundCache = BitmapCache()
            }

            if (satValBackgroundCache!!.bitmap == null) {
                satValBackgroundCache!!.bitmap =
                    Bitmap.createBitmap(rect!!.width(), rect.height(), Bitmap.Config.ARGB_8888)
            }

            if (satValBackgroundCache!!.canvas == null) {
                satValBackgroundCache!!.canvas = Canvas(satValBackgroundCache!!.bitmap!!)
            }
            val rgb = Color.HSVToColor(floatArrayOf(hue, 1f, 1f))
            satShader = LinearGradient(
                rect!!.left.toFloat(),
                rect.top.toFloat(),
                rect.right.toFloat(),
                rect.top.toFloat(),
                -0x1,
                rgb,
                Shader.TileMode.CLAMP
            )
            val mShader = ComposeShader(valShader!!, satShader!!, PorterDuff.Mode.MULTIPLY)
            satValPaint!!.shader = mShader

            satValBackgroundCache!!.canvas!!.drawRect(
                0f, 0f, satValBackgroundCache!!.bitmap!!.width.toFloat(),
                satValBackgroundCache!!.bitmap!!.height.toFloat(), satValPaint!!
            )

            satValBackgroundCache!!.value = hue
        }

        canvas.drawBitmap(satValBackgroundCache!!.bitmap!!, null, rect!!, null)
        val p = satValToPoint(sat, value)
        satValTrackerPaint!!.color = -0x1000000
        canvas.drawCircle(
            p.x.toFloat(), p.y.toFloat(), (circleTrackerRadiusPx - DrawingUtils.dpToPx(
                context, 1f
            )).toFloat(), satValTrackerPaint!!
        )
        satValTrackerPaint!!.color = -0x222223
        canvas.drawCircle(
            p.x.toFloat(),
            p.y.toFloat(),
            circleTrackerRadiusPx.toFloat(),
            satValTrackerPaint!!
        )
    }

    private fun drawHuePanel(canvas: Canvas) {
        val rect = hueRect
        if (BORDER_WIDTH_PX > 0) {
            borderPaint!!.color = borderColor
            canvas.drawRect(
                (rect!!.left - BORDER_WIDTH_PX).toFloat(),
                (rect.top - BORDER_WIDTH_PX).toFloat(),
                (rect.right + BORDER_WIDTH_PX).toFloat(),
                (
                        rect.bottom + BORDER_WIDTH_PX).toFloat(),
                borderPaint!!
            )
        }
        if (hueBackgroundCache == null) {
            hueBackgroundCache = BitmapCache()
            hueBackgroundCache!!.bitmap =
                Bitmap.createBitmap(rect!!.width(), rect.height(), Bitmap.Config.ARGB_8888)
            hueBackgroundCache!!.canvas = Canvas(hueBackgroundCache!!.bitmap!!)
            val hueColors = IntArray((rect.height() + 0.5f).toInt())

            var h = 360f
            for (i in hueColors.indices) {
                hueColors[i] = Color.HSVToColor(floatArrayOf(h, 1f, 1f))
                h -= 360f / hueColors.size
            }

            val linePaint = Paint()
            linePaint.strokeWidth = 0f
            for (i in hueColors.indices) {
                linePaint.color = hueColors[i]
                hueBackgroundCache!!.canvas!!.drawLine(
                    0f,
                    i.toFloat(),
                    hueBackgroundCache!!.bitmap?.width?.toFloat()!!,
                    i.toFloat(),
                    linePaint
                )
            }
        }
        canvas.drawBitmap(hueBackgroundCache!!.bitmap!!, null, rect!!, null)
        val p = hueToPoint(hue)
        val r = RectF()
        r.left = (rect.left - sliderTrackerOffsetPx).toFloat()
        r.right = (rect.right + sliderTrackerOffsetPx).toFloat()
        r.top = (p.y - sliderTrackerSizePx / 2).toFloat()
        r.bottom = (p.y + sliderTrackerSizePx / 2).toFloat()
        canvas.drawRoundRect(r, 2f, 2f, hueAlphaTrackerPaint!!)
    }

    private fun drawAlphaPanel(canvas: Canvas) {
        if (!showAlphaPanel || alphaRect == null || alphaPatternDrawable == null)
            return
        val rect: Rect = alphaRect!!
        if (BORDER_WIDTH_PX > 0) {
            borderPaint!!.color = borderColor
            canvas.drawRect(
                (rect.left - BORDER_WIDTH_PX).toFloat(),
                (rect.top - BORDER_WIDTH_PX).toFloat(),
                (rect.right + BORDER_WIDTH_PX).toFloat(),
                (
                        rect.bottom + BORDER_WIDTH_PX).toFloat(),
                borderPaint!!
            )
        }
        alphaPatternDrawable!!.draw(canvas)
        val hsv = floatArrayOf(hue, sat, value)
        val color = Color.HSVToColor(hsv)
        val acolor = Color.HSVToColor(0, hsv)
        alphaShader = LinearGradient(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.top.toFloat(),
            color,
            acolor,
            Shader.TileMode.CLAMP
        )
        alphaPaint!!.shader = alphaShader
        canvas.drawRect(rect, alphaPaint!!)
        if (alphaSliderText != null && alphaSliderText != "") {
            canvas.drawText(
                alphaSliderText!!, rect.centerX().toFloat(), (rect.centerY() + DrawingUtils.dpToPx(
                    context, 4f
                )).toFloat(),
                alphaTextPaint!!
            )
        }
        val p = alphaToPoint(alpha)
        val r = RectF()
        r.left = (p.x - sliderTrackerSizePx / 2).toFloat()
        r.right = (p.x + sliderTrackerSizePx / 2).toFloat()
        r.top = (rect.top - sliderTrackerOffsetPx).toFloat()
        r.bottom = (rect.bottom + sliderTrackerOffsetPx).toFloat()
        canvas.drawRoundRect(r, 2f, 2f, hueAlphaTrackerPaint!!)
    }

    private fun hueToPoint(hue: Float): Point {
        val rect = hueRect
        val height = rect!!.height().toFloat()
        val p = Point()
        p.y = (height - hue * height / 360f + rect.top).toInt()
        p.x = rect.left
        return p
    }

    private fun satValToPoint(sat: Float, `val`: Float): Point {
        val rect = satValRect
        val height = rect!!.height().toFloat()
        val width = rect.width().toFloat()
        val p = Point()
        p.x = (sat * width + rect.left).toInt()
        p.y = ((1f - `val`) * height + rect.top).toInt()
        return p
    }

    private fun alphaToPoint(alpha: Int): Point {
        val rect = alphaRect
        val width = rect!!.width().toFloat()
        val p = Point()
        p.x = (width - alpha * width / 0xff + rect.left).toInt()
        p.y = rect.top
        return p
    }

    private fun pointToSatVal(x: Float, y: Float): FloatArray {
        var x = x
        var y = y
        val rect = satValRect
        val result = FloatArray(2)
        val width = rect!!.width().toFloat()
        val height = rect.height().toFloat()
        x = when {
            x < rect.left -> {
                0f
            }
            x > rect.right -> {
                width
            }
            else -> {
                x - rect.left
            }
        }
        y = when {
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
        result[0] = 1f / width * x
        result[1] = 1f - 1f / height * y
        return result
    }

    private fun pointToHue(y: Float): Float {
        val rect = hueRect
        val height = rect!!.height().toFloat()
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
        val width = rect!!.width()
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
            if (onColorChangedListener != null) {
                onColorChangedListener!!.onColorChanged(
                    Color.HSVToColor(
                        alpha,
                        floatArrayOf(hue, sat, value)
                    )
                )
            }
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
        if (hueRect!!.contains(startX, startY)) {
            hue = pointToHue(event.y)
            update = true
        } else if (satValRect!!.contains(startX, startY)) {
            val result = pointToSatVal(event.x, event.y)
            sat = result[0]
            value = result[1]
            update = true
        } else if (alphaRect != null && alphaRect!!.contains(startX, startY)) {
            alpha = pointToAlpha(event.x.toInt())
            update = true
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
        drawingRect.apply {
            left = paddingLeft
            right = newWidth - paddingRight
            top = paddingTop
            bottom = newHeight - paddingBottom
        }
        valShader = null
        satShader = null
        alphaShader = null
        satValBackgroundCache = null
        hueBackgroundCache = null
        setUpSatValRect()
        setUpHueRect()
        setUpAlphaRect()
    }

    private fun setUpSatValRect() {
        val dRect = drawingRect
        val left = dRect!!.left + BORDER_WIDTH_PX
        val top = dRect.top + BORDER_WIDTH_PX
        var bottom = dRect.bottom - BORDER_WIDTH_PX
        val right = dRect.right - BORDER_WIDTH_PX - panelSpacingPx - huePanelWidthPx
        if (showAlphaPanel) {
            bottom -= alphaPanelHeightPx + panelSpacingPx
        }
        satValRect = Rect(left, top, right, bottom)
    }

    private fun setUpHueRect() {
        val dRect = drawingRect
        val left = dRect.right - huePanelWidthPx + BORDER_WIDTH_PX
        val top = dRect.top + BORDER_WIDTH_PX
        val bottom =
            dRect.bottom - BORDER_WIDTH_PX - if (showAlphaPanel) panelSpacingPx + alphaPanelHeightPx else 0
        val right = dRect.right - BORDER_WIDTH_PX
        hueRect = Rect(left, top, right, bottom)
    }

    private fun setUpAlphaRect() {

        if (!showAlphaPanel)
            return

        val dRect = drawingRect
        val left = dRect.left + BORDER_WIDTH_PX
        val top = dRect.bottom - alphaPanelHeightPx + BORDER_WIDTH_PX
        val bottom = dRect.bottom - BORDER_WIDTH_PX
        val right = dRect.right - BORDER_WIDTH_PX

        alphaRect = Rect(left, top, right, bottom)
        alphaPatternDrawable = AlphaPatternDrawable(DrawingUtils.dpToPx(context, 4f))
        alphaPatternDrawable!!.setBounds(
            alphaRect!!.left.toFloat().roundToInt(),
            alphaRect!!.top.toFloat().roundToInt(),
            alphaRect!!.right.toFloat().roundToInt(),
            alphaRect!!.bottom.toFloat().roundToInt()
        )
    }

    fun setOnColorChangedListener(listener: OnColorChangedListener?) {
        onColorChangedListener = listener
    }

    var color: Int
        get() = Color.HSVToColor(alpha, floatArrayOf(hue, sat, value))
        set(color) {
            setColor(color, false)
        }

    private fun setColor(color: Int, callback: Boolean) {
        val alpha = Color.alpha(color)
        val red = Color.red(color)
        val blue = Color.blue(color)
        val green = Color.green(color)
        val hsv = FloatArray(3)
        Color.RGBToHSV(red, green, blue, hsv)
        this.alpha = alpha
        hue = hsv[0]
        sat = hsv[1]
        value = hsv[2]
        if (callback && onColorChangedListener != null) {
            onColorChangedListener!!.onColorChanged(
                Color.HSVToColor(
                    this.alpha,
                    floatArrayOf(hue, sat, value)
                )
            )
        }
        invalidate()
    }

    fun setAlphaSliderVisible(visible: Boolean) {
        if (showAlphaPanel != visible) {
            showAlphaPanel = visible

            valShader = null
            satShader = null
            alphaShader = null
            hueBackgroundCache = null
            satValBackgroundCache = null
            requestLayout()
        }
    }

    fun getSliderTrackerColor(): Int {
        return sliderTrackerColor
    }

    fun setSliderTrackerColor(color: Int) {
        sliderTrackerColor = color
        hueAlphaTrackerPaint!!.color = sliderTrackerColor
        invalidate()
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderColor(color: Int) {
        borderColor = color
        invalidate()
    }

    fun setAlphaSliderText(res: Int) {
        val text = context.getString(res)
        setAlphaSliderText(text)
    }

    fun setAlphaSliderText(text: String?) {
        alphaSliderText = text
        invalidate()
    }

    interface OnColorChangedListener {
        fun onColorChanged(newColor: Int)
    }

    private inner class BitmapCache {
        var canvas: Canvas? = null
        var bitmap: Bitmap? = null
        var value = 0f
    }

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

    init {
        init(context, attrs)
    }
}