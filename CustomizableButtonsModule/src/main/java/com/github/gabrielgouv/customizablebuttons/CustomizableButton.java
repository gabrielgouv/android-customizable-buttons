package com.github.gabrielgouv.customizablebuttons;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.github.gabrielgouv.customizablebuttons.util.ColorUtil;
import com.github.gabrielgouv.customizablebuttons.util.DimensionUtil;
import com.github.gabrielgouv.customizablebuttons.util.DrawableFactory;


public class CustomizableButton extends AppCompatButton {

    public static final boolean DEFAULT_USE_RIPPLE_EFFECT = false;
    public static final int DEFAULT_RIPPLE_COLOR = 0xff000000;
    public static final float DEFAULT_RIPPLE_OPACITY = 0.26f;
    public static final int DEFAULT_TEXT_SIZE = 14;
    public static final int DEFAULT_TEXT_STYLE = Typeface.NORMAL;
    public static final boolean DEFAULT_TEXT_ALL_CAPS = false;
    public static final int DEFAULT_BACKGROUND_COLOR = 0xffcccccc;
    public static final int DEFAULT_BORDER_COLOR = 0xff000000;
    public static final int DEFAULT_BORDER_THICKNESS = 0;
    public static final int DEFAULT_BORDER_RADIUS = 2;
    public static final float DEFAULT_COLOR_FACTOR = 0.8f;
    public static final int DEFAULT_DISABLED_BACKGROUND_COLOR = 0xffefefef;
    public static final int DEFAULT_DISABLED_TEXT_COLOR = 0xffbdbdbd;
    public static final int DEFAULT_DISABLED_BORDER_COLOR = 0xffcccccc;
    public static final int DEFAULT_ELEVATION = 0;
    public static final boolean DEFAULT_BUTTON_ENABLED = true;
    public static final float DEFAULT_BACKGROUND_OPACITY = 1f;

    protected Context mContext;
    protected AttributeSet mAttrs;
    protected int mDefStyleAttr;

    protected String mText;
    protected int mTextSize;
    protected int mTextStyle;
    protected boolean mTextAllCaps;
    protected boolean mUseRippleEffect;
    protected int mRippleColor;
    protected float mRippleOpacity;
    protected boolean mEnabled;
    protected int mElevation;

    protected int mTextColorNormal;
    protected int mBackgroundColorNormal;
    protected float mBackgroundOpacityNormal;
    protected int mBorderColorNormal;
    protected int mBorderThicknessNormal;
    protected int mBorderRadiusNormal;

    protected int mTextColorPressed;
    protected int mBackgroundColorPressed;
    protected float mBackgroundOpacityPressed;
    protected int mBorderColorPressed;
    protected int mBorderThicknessPressed;
    protected int mBorderRadiusPressed;

    protected int mTextColorDisabled;
    protected int mBackgroundColorDisabled;
    protected float mBackgroundOpacityDisabled;
    protected int mBorderColorDisabled;
    protected int mBorderThicknessDisabled;
    protected int mBorderRadiusDisabled;

    private Drawable mDrawableNormal;
    private Drawable mDrawablePressed;
    private Drawable mDrawableDisabled;

    public CustomizableButton(Context context) {

        super(context);
        mContext = context;
        init();

    }

    public CustomizableButton(Context context, AttributeSet attrs) {

        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        init();

    }

    public CustomizableButton(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttrs = attrs;
        mDefStyleAttr = defStyleAttr;
        init();

    }

    private void init() {

        initAttributes();
        setupButton();
    }

    /**
     * Initialize attributes
     */

    protected void initAttributes() {

        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.CustomizableButton, mDefStyleAttr, 0);

        // normal state
        mBackgroundColorNormal = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorNormal, DEFAULT_BACKGROUND_COLOR);
        mBackgroundOpacityNormal = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityNormal, DEFAULT_BACKGROUND_OPACITY);
        mTextColorNormal = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorNormal, ColorUtil.getTextColorFromBackgroundColor(mBackgroundColorNormal));
        mBorderColorNormal = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorNormal, DEFAULT_BORDER_COLOR);
        mBorderThicknessNormal = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessNormal, DEFAULT_BORDER_THICKNESS);
        mBorderRadiusNormal = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusNormal, DEFAULT_BORDER_RADIUS);

        // pressed/focused state
        mBackgroundColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorPressed, ColorUtil.darkenLightenColor(mBackgroundColorNormal, DEFAULT_COLOR_FACTOR));
        mBackgroundOpacityPressed = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityPressed, DEFAULT_BACKGROUND_OPACITY);
        mTextColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorPressed, ColorUtil.getTextColorFromBackgroundColor(mBackgroundColorPressed));
        mBorderColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorPressed, mBorderColorNormal);
        mBorderThicknessPressed = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessPressed, mBorderThicknessNormal);
        mBorderRadiusPressed = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusPressed, mBorderRadiusNormal);

        // disabled state
        mBackgroundColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorDisabled, DEFAULT_DISABLED_BACKGROUND_COLOR);
        mBackgroundOpacityDisabled = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityDisabled, DEFAULT_BACKGROUND_OPACITY);
        mTextColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorDisabled, DEFAULT_DISABLED_TEXT_COLOR);
        mBorderColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorDisabled, DEFAULT_DISABLED_BORDER_COLOR);
        mBorderThicknessDisabled = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessDisabled, mBorderThicknessNormal);
        mBorderRadiusDisabled = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusDisabled, mBorderRadiusNormal);

        // general
        mEnabled = typedArray.getBoolean(R.styleable.CustomizableButton_cb_enabled, DEFAULT_BUTTON_ENABLED);
        mEnabled = typedArray.getBoolean(R.styleable.CustomizableButton_android_enabled, mEnabled);
        mText = typedArray.getString(R.styleable.CustomizableButton_cb_text);
        if (mText == null)
            mText = typedArray.getString(R.styleable.CustomizableButton_android_text);
        mTextSize = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_textSize, DEFAULT_TEXT_SIZE);
        mTextSize = (int) typedArray.getDimension(R.styleable.CustomizableButton_android_textSize, mTextSize);
        mTextStyle = typedArray.getInt(R.styleable.CustomizableButton_cb_textStyle, DEFAULT_TEXT_STYLE);
        mTextStyle = typedArray.getInt(R.styleable.CustomizableButton_android_textStyle, mTextStyle);
        mTextAllCaps = typedArray.getBoolean(R.styleable.CustomizableButton_cb_textAllCaps, DEFAULT_TEXT_ALL_CAPS);
        mTextAllCaps = typedArray.getBoolean(R.styleable.CustomizableButton_android_textAllCaps, mTextAllCaps);
        mUseRippleEffect = typedArray.getBoolean(R.styleable.CustomizableButton_cb_useRippleEffect, DEFAULT_USE_RIPPLE_EFFECT);
        mRippleColor = typedArray.getColor(R.styleable.CustomizableButton_cb_rippleColor, DEFAULT_RIPPLE_COLOR);
        mRippleOpacity = typedArray.getFloat(R.styleable.CustomizableButton_cb_rippleOpacity, DEFAULT_RIPPLE_OPACITY);
        mElevation = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_elevation, DEFAULT_ELEVATION);

        typedArray.recycle();

    }

    /**
     * Setup the button
     */

    private void setupButton() {

        this.setEnabled(mEnabled);
        this.setPadding(DimensionUtil.dipToPx(16), 0, DimensionUtil.dipToPx(16), 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
            setElevation(mElevation);
        }

        setButtonText();

        if (mEnabled && mUseRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setButtonBackground(getRippleDrawable());
        } else {
            setButtonBackground(getButtonBackgrounds());
        }

    }

    /**
     * Set button background for all APIs
     */

    private void setButtonBackground(Drawable drawable) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(drawable);
        } else {
            this.setBackgroundDrawable(drawable);
        }

    }

    /**
     * Set button text
     */

    private void setButtonText() {

        this.setTextColor(getButtonTextColors());
        this.setAllCaps(mTextAllCaps);
        this.setText(mText);
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        this.setTypeface(this.getTypeface(), mTextStyle);

    }


    private Drawable getButtonBackgrounds() {

        mDrawableNormal = DrawableFactory.getBackgroundDrawable(mBorderRadiusNormal, mBorderThicknessNormal, mBorderColorNormal,
                mBackgroundColorNormal, mBackgroundOpacityNormal);

        mDrawablePressed = DrawableFactory.getBackgroundDrawable(mBorderRadiusPressed, mBorderThicknessPressed, mBorderColorPressed,
                mBackgroundColorPressed, mBackgroundOpacityPressed);

        mDrawableDisabled = DrawableFactory.getBackgroundDrawable(mBorderRadiusDisabled, mBorderThicknessDisabled, mBorderColorDisabled,
                mBackgroundColorDisabled, mBackgroundOpacityDisabled);

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_enabled}, mDrawablePressed);
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled}, mDrawablePressed);
        states.addState(new int[] {-android.R.attr.state_enabled}, mDrawableDisabled);
        states.addState(new int[] {}, mDrawableNormal);

        return states;
    }

    private ColorStateList getButtonTextColors() {

        if (mUseRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mTextColorPressed = mTextColorNormal;

        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_focused, android.R.attr.state_pressed},
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{}
                },
                new int[]{
                        mTextColorPressed,
                        mTextColorNormal,
                        mTextColorPressed,
                        mTextColorDisabled,
                        mTextColorNormal
                }
        );
    }

    /**
     * Create Ripple Effect for the button
     * @return Ripple effect
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable() {

        GradientDrawable mask = new GradientDrawable();

        if (mDrawableNormal.getConstantState() != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = (GradientDrawable) mDrawableNormal.getConstantState().newDrawable();
            mask.setColor(Color.WHITE);
        }

        if (mUseRippleEffect && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            mRippleColor = mBackgroundColorPressed;

        return new RippleDrawable(ColorUtil.getRippleColorFromColor(mRippleColor, mRippleOpacity), mDrawableNormal, mask);

    }

    public int getTextColorNormal() {
        return mTextColorNormal;
    }

    public void setTextColorNormal(int color) {
        this.mTextColorNormal = color;
        setupButton();
    }

    public int getBackgroundColorNormal() {
        return mBackgroundColorNormal;
    }

    public void setBackgroundColorNormal(int color) {
        this.mBackgroundColorNormal = color;
        setupButton();
    }

    public float getBackgroundOpacityNormal() {
        return mBackgroundOpacityNormal;
    }

    public void setBackgroundOpacityNormal(float opacity) {
        if (opacity >= 0 && opacity <= 1) {
            this.mBackgroundOpacityNormal = opacity;
            setupButton();
        }
    }

    public int getBorderColorNormal() {
        return mBorderColorNormal;
    }

    public void setBorderColorNormal(int color) {
        this.mBorderColorNormal = color;
        setupButton();
    }

    public int getBorderThicknessNormal() {
        return mBorderThicknessNormal;
    }

    public void setBorderThicknessNormal(int thickness) {
        if (thickness >= 0) {
            this.mBorderThicknessNormal = thickness;
            setupButton();
        }
    }

    public int getBorderRadiusNormal() {
        return mBorderRadiusNormal;
    }

    public void setBorderRadiusNormal(int radius) {
        if (radius >= 0) {
            this.mBorderRadiusNormal = radius;
            setupButton();
        }
    }

    public int getTextColorPressed() {
        return mTextColorPressed;
    }

    public void setTextColorPressed(int color) {
        this.mTextColorPressed = color;
        setupButton();
    }

    public int getBackgroundColorPressed() {
        return mBackgroundColorPressed;
    }

    public void setBackgroundColorPressed(int color) {
        this.mBackgroundColorPressed = color;
        setupButton();
    }

    public float getBackgroundOpacityPressed() {
        return mBackgroundOpacityPressed;
    }

    public void setBackgroundOpacityPressed(float opacity) {
        if (opacity >= 0 && opacity < 1) {
            this.mBackgroundOpacityPressed = opacity;
            setupButton();
        }
    }

    public int getBorderColorPressed() {
        return mBorderColorPressed;
    }

    public void setBorderColorPressed(int color) {

        this.mBorderColorPressed = color;
        setupButton();

    }

    public int getBorderThicknessPressed() {
        return mBorderThicknessPressed;
    }

    public void setBorderThicknessPressed(int thickness) {
        if (thickness >= 0) {
            this.mBorderThicknessPressed = thickness;
            setupButton();
        }
    }

    public int getBorderRadiusPressed() {
        return mBorderRadiusPressed;
    }

    public void setBorderRadiusPressed(int radius) {
        if (radius >= 0) {
            this.mBorderRadiusPressed = radius;
            setupButton();
        }
    }

    public int getTextColorDisabled() {
        return mTextColorDisabled;
    }

    public void setTextColorDisabled(int color) {
        this.mTextColorDisabled = color;
        setupButton();
    }

    public int getBackgroundColorDisabled() {
        return mBackgroundColorDisabled;
    }

    public void setBackgroundColorDisabled(int color) {
        this.mBackgroundColorDisabled = color;
        setupButton();
    }

    public float getBackgroundOpacityDisabled() {
        return mBackgroundOpacityDisabled;
    }

    public void setBackgroundOpacityDisabled(float opacity) {
        if (opacity >= 0 && opacity <= 1) {
            this.mBackgroundOpacityDisabled = opacity;
            setupButton();
        }
    }

    public int getBorderColorDisabled() {
        return mBorderColorDisabled;
    }

    public void setBorderColorDisabled(int color) {
        this.mBorderColorDisabled = color;
        setupButton();
    }

    public int getBorderThicknessDisabled() {
        return mBorderThicknessDisabled;
    }

    public void setBorderThicknessDisabled(int thickness) {
        if (thickness >= 0) {
            this.mBorderThicknessDisabled = thickness;
            setupButton();
        }
    }

    public int getBorderRadiusDisabled() {
        return mBorderRadiusDisabled;
    }

    public void setBorderRadiusDisabled(int radius) {
        if (radius >= 0) {
            this.mBorderRadiusDisabled = radius;
            setupButton();
        }
    }

}
