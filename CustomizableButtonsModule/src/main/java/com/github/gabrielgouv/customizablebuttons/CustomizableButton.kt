package com.github.gabrielgouv.customizablebuttons

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import com.github.gabrielgouv.customizablebuttons.attribute.DrawableAttributes
import com.github.gabrielgouv.customizablebuttons.attribute.RippleAttributes
import com.github.gabrielgouv.customizablebuttons.util.ColorUtil
import com.github.gabrielgouv.customizablebuttons.util.DimensionUtil
import com.github.gabrielgouv.customizablebuttons.util.DrawableFactory

open class CustomizableButton : BaseButton {

    object Constants {
        val DEFAULT_USE_RIPPLE_EFFECT = false
        val DEFAULT_RIPPLE_COLOR = -0x1000000 //0xff000000
        val DEFAULT_RIPPLE_OPACITY = 0.26f
        val DEFAULT_TEXT_SIZE = 14
        val DEFAULT_TEXT_STYLE = Typeface.NORMAL
        val DEFAULT_TEXT_ALL_CAPS = false
        val DEFAULT_BACKGROUND_COLOR = -0x333334 //0xffcccccc
        val DEFAULT_BORDER_COLOR = -0x1000000 //0xff000000
        val DEFAULT_BORDER_THICKNESS = 0
        val DEFAULT_BORDER_RADIUS = 2
        val DEFAULT_COLOR_FACTOR = 0.8f
        val DEFAULT_DISABLED_BACKGROUND_COLOR = -0x101011 //0xffefefef
        val DEFAULT_DISABLED_TEXT_COLOR = -0x424243 //0xffbdbdbd
        val DEFAULT_DISABLED_BORDER_COLOR = -0x333334 //0xffcccccc
        val DEFAULT_ELEVATION = 0
        val DEFAULT_BUTTON_ENABLED = true
        val DEFAULT_BACKGROUND_OPACITY = 1f
    }

    private var mText: String? = null
    private var mTextSize: Int = Constants.DEFAULT_TEXT_SIZE
    private var mTextStyle: Int = Constants.DEFAULT_TEXT_STYLE
    private var mTextColorNormal: Int = ColorUtil.getTextColorFromBackgroundColor(mDrawableNormal.backgroundColor)
    private var mTextColorPressed: Int = ColorUtil.getTextColorFromBackgroundColor(mDrawablePressed.backgroundColor)
    private var mTextColorDisabled: Int = Constants.DEFAULT_DISABLED_TEXT_COLOR
    private var mTextAllCaps: Boolean = false
    private var mEnabled: Boolean = true
    private var mElevation: Int = Constants.DEFAULT_ELEVATION

    private lateinit var mRippleAttributes: RippleAttributes
    private lateinit var mDrawableNormal: DrawableAttributes
    private lateinit var mDrawablePressed: DrawableAttributes
    private lateinit var mDrawableDisabled: DrawableAttributes

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun init() {
        initAttributes()
        setupButton()
    }

    /**
     * Initialize attributes
     */

    private fun initAttributes() {

        val typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.CustomizableButton, mDefStyleAttr, 0)

        // normal state
        mDrawableNormal = DrawableAttributes()
        mDrawableNormal.backgroundColor = typedArray?.getColor(R.styleable.CustomizableButton_cb_backgroundColorNormal, Constants.DEFAULT_BACKGROUND_COLOR)!!
        mDrawableNormal.backgroundOpacity = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityNormal, Constants.DEFAULT_BACKGROUND_OPACITY)
        mDrawableNormal.borderColor = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorNormal, Constants.DEFAULT_BORDER_COLOR)
        mDrawableNormal.borderThickness = typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessNormal, Constants.DEFAULT_BORDER_THICKNESS.toFloat()).toInt()
        mDrawableNormal.borderRadius = typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusNormal, Constants.DEFAULT_BORDER_RADIUS.toFloat()).toInt()
        mTextColorNormal = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorNormal, ColorUtil.getTextColorFromBackgroundColor(mDrawableNormal.backgroundColor))

        // pressed/focused state
        mDrawablePressed = DrawableAttributes()
        mDrawablePressed.backgroundColor = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorPressed, ColorUtil.darkenLightenColor(mDrawableNormal.backgroundColor, Constants.DEFAULT_COLOR_FACTOR))
        mDrawablePressed.backgroundOpacity = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityPressed, Constants.DEFAULT_BACKGROUND_OPACITY)
        mDrawablePressed.borderColor = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorPressed, mDrawableNormal.backgroundColor)
        mDrawablePressed.borderThickness = typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessPressed, mDrawableNormal.borderThickness.toFloat()).toInt()
        mDrawablePressed.borderRadius = typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusPressed, mDrawableNormal.borderRadius.toFloat()).toInt()
        mTextColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorPressed, ColorUtil.getTextColorFromBackgroundColor(mDrawablePressed.backgroundColor))

        // disabled state
        mDrawableDisabled = DrawableAttributes()
        mDrawableDisabled.backgroundColor = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorDisabled, Constants.DEFAULT_DISABLED_BACKGROUND_COLOR)
        mDrawableDisabled.backgroundOpacity = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityDisabled, Constants.DEFAULT_BACKGROUND_OPACITY)
        mDrawableDisabled.borderColor = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorDisabled, Constants.DEFAULT_DISABLED_BORDER_COLOR)
        mDrawableDisabled.borderThickness = typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessDisabled, mDrawableNormal.borderThickness.toFloat()).toInt()
        mDrawableDisabled.borderRadius = typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusDisabled, mDrawableNormal.borderRadius.toFloat()).toInt()
        mTextColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorDisabled, Constants.DEFAULT_DISABLED_TEXT_COLOR)

        // general
        mEnabled = typedArray.getBoolean(R.styleable.CustomizableButton_cb_enabled, Constants.DEFAULT_BUTTON_ENABLED)
        mEnabled = typedArray.getBoolean(R.styleable.CustomizableButton_android_enabled, mEnabled)
        mText = typedArray.getString(R.styleable.CustomizableButton_cb_text)
        if (mText == null)
            mText = typedArray.getString(R.styleable.CustomizableButton_android_text)
        mTextSize = typedArray.getDimension(R.styleable.CustomizableButton_cb_textSize, Constants.DEFAULT_TEXT_SIZE.toFloat()).toInt()
        mTextSize = typedArray.getDimension(R.styleable.CustomizableButton_android_textSize, mTextSize.toFloat()).toInt()
        mTextStyle = typedArray.getInt(R.styleable.CustomizableButton_cb_textStyle, Constants.DEFAULT_TEXT_STYLE)
        mTextStyle = typedArray.getInt(R.styleable.CustomizableButton_android_textStyle, mTextStyle)
        mTextAllCaps = typedArray.getBoolean(R.styleable.CustomizableButton_cb_textAllCaps, Constants.DEFAULT_TEXT_ALL_CAPS)
        mTextAllCaps = typedArray.getBoolean(R.styleable.CustomizableButton_android_textAllCaps, mTextAllCaps)

        mElevation = typedArray.getDimension(R.styleable.CustomizableButton_cb_elevation, Constants.DEFAULT_ELEVATION.toFloat()).toInt()

        mRippleAttributes = RippleAttributes()
        mRippleAttributes.isUseRippleEffect = typedArray.getBoolean(R.styleable.CustomizableButton_cb_useRippleEffect, Constants.DEFAULT_USE_RIPPLE_EFFECT)
        mRippleAttributes.rippleColor = typedArray.getColor(R.styleable.CustomizableButton_cb_rippleColor, Constants.DEFAULT_RIPPLE_COLOR)
        mRippleAttributes.rippleOpacity = typedArray.getFloat(R.styleable.CustomizableButton_cb_rippleOpacity, Constants.DEFAULT_RIPPLE_OPACITY)

        typedArray.recycle()

    }

    /**
     * Setup the button
     */

    private fun setupButton() {

        this.isEnabled = mEnabled
        this.setPadding(DimensionUtil.dipToPx(16f), 0, DimensionUtil.dipToPx(16f), 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stateListAnimator = null
            elevation = mElevation.toFloat()
        }

        setButtonText()

        if (mEnabled && mRippleAttributes.isUseRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setButtonBackground(DrawableFactory.getRippleDrawable(mDrawableNormal, mRippleAttributes))
        } else {
            setButtonBackground(getButtonBackgrounds())
        }

    }

    /**
     * Set button text
     */

    private fun setButtonText() {

        this.setTextColor(getButtonTextColors())
        this.setAllCaps(mTextAllCaps)
        this.text = mText
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize.toFloat())
        setTextStyle(mTextStyle)

    }

    private fun getButtonBackgrounds(): Drawable {

        val drawableNormal = DrawableFactory.getBackgroundDrawable(mDrawableNormal)
        val drawablePressed = DrawableFactory.getBackgroundDrawable(mDrawablePressed)
        val drawableDisabled = DrawableFactory.getBackgroundDrawable(mDrawableDisabled)

        val states = StateListDrawable()

        states.addState(intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled), drawablePressed)
        states.addState(intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled), drawablePressed)
        states.addState(intArrayOf(-android.R.attr.state_enabled), drawableDisabled)
        states.addState(intArrayOf(), drawableNormal)

        return states
    }

    private fun getButtonTextColors(): ColorStateList {

        if (mRippleAttributes.isUseRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mTextColorPressed = mTextColorNormal

        return ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_focused, android.R.attr.state_pressed),
                        intArrayOf(-android.R.attr.state_enabled),
                        intArrayOf()),

                intArrayOf(mTextColorPressed,
                        mTextColorNormal,
                        mTextColorPressed,
                        mTextColorDisabled,
                        mTextColorNormal)
        )
    }

    fun setTextColorNormal(color: Int) {
        this.mTextColorNormal = color
        setupButton()
    }

    fun setBackgroundColorNormal(color: Int) {
        mDrawableNormal.backgroundColor = color
        setupButton()
    }

    fun setBackgroundOpacityNormal(opacity: Float) {
        if (opacity >= 0 && opacity <= 1) {
            mDrawableNormal.backgroundOpacity = opacity
            setupButton()
        }
    }

    fun setBorderColorNormal(color: Int) {
        mDrawableNormal.borderColor = color
        setupButton()
    }

    fun setBorderThicknessNormal(thickness: Int) {
        if (thickness >= 0) {
            mDrawableNormal.borderThickness = thickness
            setupButton()
        }
    }

    fun setBorderRadiusNormal(radius: Int) {
        if (radius >= 0) {
            mDrawableNormal.borderRadius = radius
            setupButton()
        }
    }

    fun setTextColorPressed(color: Int) {
        this.mTextColorPressed = color
        setupButton()
    }

    fun setBackgroundColorPressed(color: Int) {
        mDrawablePressed.backgroundColor = color
        setupButton()
    }

    fun setBackgroundOpacityPressed(opacity: Float) {
        if (opacity >= 0 && opacity < 1) {
            mDrawablePressed.backgroundOpacity = opacity
            setupButton()
        }
    }

    fun setBorderColorPressed(color: Int) {
        mDrawablePressed.borderColor = color
        setupButton()

    }

    fun setBorderThicknessPressed(thickness: Int) {
        if (thickness >= 0) {
            mDrawablePressed.borderThickness = thickness
            setupButton()
        }
    }

    fun setBorderRadiusPressed(radius: Int) {
        if (radius >= 0) {
            mDrawablePressed.borderRadius = radius
            setupButton()
        }
    }

    fun setTextColorDisabled(color: Int) {
        this.mTextColorDisabled = color
        setupButton()
    }

    fun setBackgroundColorDisabled(color: Int) {
        mDrawableDisabled.backgroundColor = color
        setupButton()
    }

    fun setBackgroundOpacityDisabled(opacity: Float) {
        if (opacity >= 0 && opacity <= 1) {
            mDrawableDisabled.backgroundOpacity = opacity
            setupButton()
        }
    }

    fun setBorderColorDisabled(color: Int) {
        mDrawableDisabled.borderColor = color
        setupButton()
    }

    fun setBorderThicknessDisabled(thickness: Int) {
        if (thickness >= 0) {
            mDrawableDisabled.borderThickness = thickness
            setupButton()
        }
    }

    fun setBorderRadiusDisabled(radius: Int) {
        if (radius >= 0) {
            mDrawableDisabled.borderRadius = radius
            setupButton()
        }
    }

    fun setTextStyle(textStyle: Int) {
        this.setTypeface(this.typeface, textStyle)
    }

}
