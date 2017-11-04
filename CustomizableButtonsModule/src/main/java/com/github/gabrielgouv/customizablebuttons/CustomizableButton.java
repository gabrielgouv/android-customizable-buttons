package com.github.gabrielgouv.customizablebuttons;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.github.gabrielgouv.customizablebuttons.attribute.DrawableAttributes;
import com.github.gabrielgouv.customizablebuttons.attribute.RippleAttributes;
import com.github.gabrielgouv.customizablebuttons.util.ColorUtil;
import com.github.gabrielgouv.customizablebuttons.util.DimensionUtil;
import com.github.gabrielgouv.customizablebuttons.util.DrawableFactory;


public class CustomizableButton extends BaseButton {

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

    protected String mText;
    protected int mTextSize;
    protected int mTextStyle;
    protected int mTextColorNormal;
    protected int mTextColorPressed;
    protected int mTextColorDisabled;

    protected boolean mTextAllCaps;

    protected boolean mEnabled;
    protected int mElevation;

    protected RippleAttributes mRippleAttributes;

    protected DrawableAttributes mDrawableNormal;
    protected DrawableAttributes mDrawablePressed;
    protected DrawableAttributes mDrawableDisabled;

    public CustomizableButton(Context context) {
        super(context);
    }

    public CustomizableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomizableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        initAttributes();
        setupButton();
    }

    /**
     * Initialize attributes
     */

    private void initAttributes() {

        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.CustomizableButton, mDefStyleAttr, 0);

        // normal state
        mDrawableNormal = new DrawableAttributes();
        mDrawableNormal.setBackgroundColor(typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorNormal, DEFAULT_BACKGROUND_COLOR));
        mDrawableNormal.setBackgroundOpacity(typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityNormal, DEFAULT_BACKGROUND_OPACITY));
        mDrawableNormal.setBorderColor(typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorNormal, DEFAULT_BORDER_COLOR));
        mDrawableNormal.setBorderThickness((int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessNormal, DEFAULT_BORDER_THICKNESS));
        mDrawableNormal.setBorderRadius((int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusNormal, DEFAULT_BORDER_RADIUS));
        mTextColorNormal = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorNormal, ColorUtil.getTextColorFromBackgroundColor(mDrawableNormal.getBackgroundColor()));


        // pressed/focused state
        mDrawablePressed = new DrawableAttributes();
        mDrawablePressed.setBackgroundColor(typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorPressed, ColorUtil.darkenLightenColor(mDrawableNormal.getBackgroundColor(), DEFAULT_COLOR_FACTOR)));
        mDrawablePressed.setBackgroundOpacity(typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityPressed, DEFAULT_BACKGROUND_OPACITY));
        mDrawablePressed.setBorderColor(typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorPressed, mDrawableNormal.getBackgroundColor()));
        mDrawablePressed.setBorderThickness((int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessPressed, mDrawableNormal.getBorderThickness()));
        mDrawablePressed.setBorderRadius((int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusPressed, mDrawableNormal.getBorderRadius()));
        mTextColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorPressed, ColorUtil.getTextColorFromBackgroundColor(mDrawablePressed.getBackgroundColor()));


        // disabled state
        mDrawableDisabled = new DrawableAttributes();
        mDrawableDisabled.setBackgroundColor(typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorDisabled, DEFAULT_DISABLED_BACKGROUND_COLOR));
        mDrawableDisabled.setBackgroundOpacity(typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundOpacityDisabled, DEFAULT_BACKGROUND_OPACITY));
        mDrawableDisabled.setBorderColor(typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorDisabled, DEFAULT_DISABLED_BORDER_COLOR));
        mDrawableDisabled.setBorderThickness((int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessDisabled, mDrawableNormal.getBorderThickness()));
        mDrawableDisabled.setBorderRadius((int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusDisabled, mDrawableNormal.getBorderRadius()));
        mTextColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorDisabled, DEFAULT_DISABLED_TEXT_COLOR);

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

        mElevation = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_elevation, DEFAULT_ELEVATION);

        mRippleAttributes = new RippleAttributes();
        mRippleAttributes.setUseRippleEffect(typedArray.getBoolean(R.styleable.CustomizableButton_cb_useRippleEffect, DEFAULT_USE_RIPPLE_EFFECT));
        mRippleAttributes.setRippleColor(typedArray.getColor(R.styleable.CustomizableButton_cb_rippleColor, DEFAULT_RIPPLE_COLOR));
        mRippleAttributes.setRippleOpacity(typedArray.getFloat(R.styleable.CustomizableButton_cb_rippleOpacity, DEFAULT_RIPPLE_OPACITY));

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

        if (mEnabled && mRippleAttributes.isUseRippleEffect() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setButtonBackground(DrawableFactory.getRippleDrawable(mDrawableNormal, mRippleAttributes));
        } else {
            setButtonBackground(getButtonBackgrounds());
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
        setTextStyle(mTextStyle);

    }


    private Drawable getButtonBackgrounds() {

        Drawable drawableNormal = DrawableFactory.getBackgroundDrawable(mDrawableNormal);
        Drawable drawablePressed = DrawableFactory.getBackgroundDrawable(mDrawablePressed);
        Drawable drawableDisabled = DrawableFactory.getBackgroundDrawable(mDrawableDisabled);

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_enabled}, drawablePressed);
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled}, drawablePressed);
        states.addState(new int[] {-android.R.attr.state_enabled}, drawableDisabled);
        states.addState(new int[] {}, drawableNormal);

        return states;
    }

    private ColorStateList getButtonTextColors() {

        if (mRippleAttributes.isUseRippleEffect() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
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


    public int getTextColorNormal() {
        return mTextColorNormal;
    }

    public void setTextColorNormal(int color) {
        this.mTextColorNormal = color;
        setupButton();
    }

    public int getBackgroundColorNormal() {
        return mDrawableNormal.getBackgroundColor();
    }

    public void setBackgroundColorNormal(int color) {
        mDrawableNormal.setBackgroundColor(color);
        setupButton();
    }

    public float getBackgroundOpacityNormal() {
        return mDrawableNormal.getBackgroundOpacity();
    }

    public void setBackgroundOpacityNormal(float opacity) {
        if (opacity >= 0 && opacity <= 1) {
            mDrawableNormal.setBackgroundOpacity(opacity);
            setupButton();
        }
    }

    public int getBorderColorNormal() {
        return mDrawableNormal.getBorderColor();
    }

    public void setBorderColorNormal(int color) {
        mDrawableNormal.setBorderColor(color);
        setupButton();
    }

    public int getBorderThicknessNormal() {
        return mDrawableNormal.getBorderThickness();
    }

    public void setBorderThicknessNormal(int thickness) {
        if (thickness >= 0) {
            mDrawableNormal.setBorderThickness(thickness);
            setupButton();
        }
    }

    public int getBorderRadiusNormal() {
        return mDrawableNormal.getBorderRadius();
    }

    public void setBorderRadiusNormal(int radius) {
        if (radius >= 0) {
            mDrawableNormal.setBorderRadius(radius);
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
        return mDrawablePressed.getBackgroundColor();
    }

    public void setBackgroundColorPressed(int color) {
        mDrawablePressed.setBackgroundColor(color);
        setupButton();
    }

    public float getBackgroundOpacityPressed() {
        return mDrawablePressed.getBackgroundOpacity();
    }

    public void setBackgroundOpacityPressed(float opacity) {
        if (opacity >= 0 && opacity < 1) {
            mDrawablePressed.setBackgroundOpacity(opacity);
            setupButton();
        }
    }

    public int getBorderColorPressed() {
        return mDrawablePressed.getBorderColor();
    }

    public void setBorderColorPressed(int color) {
        mDrawablePressed.setBorderColor(color);
        setupButton();

    }

    public int getBorderThicknessPressed() {
        return mDrawablePressed.getBorderThickness();
    }

    public void setBorderThicknessPressed(int thickness) {
        if (thickness >= 0) {
            mDrawablePressed.setBorderThickness(thickness);
            setupButton();
        }
    }

    public int getBorderRadiusPressed() {
        return mDrawablePressed.getBorderRadius();
    }

    public void setBorderRadiusPressed(int radius) {
        if (radius >= 0) {
            mDrawablePressed.setBorderRadius(radius);
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
        return mDrawableDisabled.getBackgroundColor();
    }

    public void setBackgroundColorDisabled(int color) {
        mDrawableDisabled.setBackgroundColor(color);
        setupButton();
    }

    public float getBackgroundOpacityDisabled() {
        return mDrawableDisabled.getBackgroundOpacity();
    }

    public void setBackgroundOpacityDisabled(float opacity) {
        if (opacity >= 0 && opacity <= 1) {
            mDrawableDisabled.setBackgroundOpacity(opacity);
            setupButton();
        }
    }

    public int getBorderColorDisabled() {
        return mDrawableDisabled.getBorderColor();
    }

    public void setBorderColorDisabled(int color) {
        mDrawableDisabled.setBorderColor(color);
        setupButton();
    }

    public int getBorderThicknessDisabled() {
        return mDrawableDisabled.getBorderThickness();
    }

    public void setBorderThicknessDisabled(int thickness) {
        if (thickness >= 0) {
            mDrawableDisabled.setBorderThickness(thickness);
            setupButton();
        }
    }

    public int getBorderRadiusDisabled() {
        return mDrawableDisabled.getBorderRadius();
    }

    public void setBorderRadiusDisabled(int radius) {
        if (radius >= 0) {
            mDrawableDisabled.setBorderRadius(radius);
            setupButton();
        }
    }

    public int getTextStyle() {
        return mTextStyle;
    }

    public void setTextStyle(int textStyle) {
        this.setTypeface(this.getTypeface(), textStyle);
    }

    public boolean isTextAllCaps() {
        return mTextAllCaps;
    }

}
