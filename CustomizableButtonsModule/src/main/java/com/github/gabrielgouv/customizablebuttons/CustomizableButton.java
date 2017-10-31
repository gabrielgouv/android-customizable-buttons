package com.github.gabrielgouv.customizablebuttons;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class CustomizableButton extends AppCompatButton {

    public static final float DEFAULT_RIPPLE_OPACITY = 0.26f;
    public static final int DEFAULT_TEXT_SIZE = 15;
    public static final boolean DEFAULT_ALL_CAPS = false;
    public static final int DEFAULT_BACKGROUND_COLOR = 0xffcccccc;
    public static final int DEFAULT_RIPPLE_COLOR = 0xffffffff;
    public static final int DEFAULT_BORDER_COLOR = 0xff000000;
    public static final int DEFAULT_BORDER_THICKNESS = 0;
    public static final int DEFAULT_BORDER_RADIUS = 2;
    public static final float DEFAULT_COLOR_FACTOR = 0.9f;
    public static final int DEFAULT_DISABLED_BACKGROUND_COLOR = 0xffefefef;
    public static final int DEFAULT_DISABLED_TEXT_COLOR = 0xffbdbdbd;
    public static final int DEFAULT_DISABLED_BORDER_COLOR = 0xffcccccc;
    public static final int DEFAULT_ELEVATION = 0;
    public static final boolean DEFAULT_USE_RIPPLE_EFFECT = false;
    public static final boolean DEFAULT_BUTTON_ENABLED = true;
    public static final float DEFAULT_BACKGROUND_OPACITY = 1f;

    protected Context mContext;
    protected AttributeSet mAttrs;
    protected int mDefStyleAttr;

    protected String mText;
    protected int mTextSize;
    protected boolean mTextAllCaps;
    protected boolean mUseRippleEffect;
    protected int mRippleColor;
    protected float mRippleAlpha;
    protected boolean mEnabled;
    protected int mElevation;

    protected int mTextColor;
    protected int mBackgroundColor;
    protected float mBackgroundColorOpacity;
    protected int mBorderColor;
    protected int mBorderThickness;
    protected int mBorderRadius;

    protected int mTextColorPressed;
    protected int mBackgroundColorPressed;
    protected float mBackgroundColorOpacityPressed;
    protected int mBorderColorPressed;
    protected int mBorderThicknessPressed;
    protected int mBorderRadiusPressed;

    protected int mTextColorDisabled;
    protected int mBackgroundColorDisabled;
    protected float mBackgroundColorOpacityDisabled;
    protected int mBorderColorDisabled;
    protected int mBorderThicknessDisabled;
    protected int mBorderRadiusDisabled;

    public CustomizableButton(Context context) {

        super(context);
        mContext = context;
        initButton();

    }

    public CustomizableButton(Context context, AttributeSet attrs) {

        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        initButton();

    }

    public CustomizableButton(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttrs = attrs;
        mDefStyleAttr = defStyleAttr;
        initButton();

    }

    private void initButton() {

        initAttributes();
        setupButton();

    }

    /**
     * Initialize attributes
     */

    protected void initAttributes() {

        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.CustomizableButton, mDefStyleAttr, 0);

        mText = typedArray.getString(R.styleable.CustomizableButton_cb_text);
        if (mText == null)
            mText = typedArray.getString(R.styleable.CustomizableButton_android_text);
        mTextSize = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_textSize, DEFAULT_TEXT_SIZE);
        mTextAllCaps = typedArray.getBoolean(R.styleable.CustomizableButton_cb_textAllCaps, DEFAULT_ALL_CAPS);
        mEnabled = typedArray.getBoolean(R.styleable.CustomizableButton_android_enabled, DEFAULT_BUTTON_ENABLED);

        mUseRippleEffect = typedArray.getBoolean(R.styleable.CustomizableButton_cb_useRippleEffect, DEFAULT_USE_RIPPLE_EFFECT);
        mRippleColor = typedArray.getColor(R.styleable.CustomizableButton_cb_rippleColor, DEFAULT_RIPPLE_COLOR);
        mRippleAlpha = typedArray.getFloat(R.styleable.CustomizableButton_cb_rippleOpacity, DEFAULT_RIPPLE_OPACITY);
        mElevation = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_elevation, DEFAULT_ELEVATION);

        // default
        mBackgroundColor = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColor, DEFAULT_BACKGROUND_COLOR);
        mBackgroundColorOpacity = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundColorOpacity, DEFAULT_BACKGROUND_OPACITY);
        mTextColor = typedArray.getColor(R.styleable.CustomizableButton_cb_textColor, ColorUtil.getTextColorFromBackgroundColor(mBackgroundColor));
        mBorderColor = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColor, DEFAULT_BORDER_COLOR);
        mBorderThickness = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThickness, DEFAULT_BORDER_THICKNESS);
        mBorderRadius = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadius, DEFAULT_BORDER_RADIUS);

        // pressed
        mBackgroundColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorPressed, ColorUtil.darkenLightenColor(mBackgroundColor, DEFAULT_COLOR_FACTOR));
        mBackgroundColorOpacityPressed = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundColorOpacityPressed, DEFAULT_BACKGROUND_OPACITY);
        mTextColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorPressed, ColorUtil.getTextColorFromBackgroundColor(mBackgroundColorPressed));
        mBorderColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorPressed, mBorderColor);
        mBorderThicknessPressed = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessPressed, mBorderThickness);
        mBorderRadiusPressed = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusPressed, mBorderRadius);

        // disabled
        mBackgroundColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorDisabled, DEFAULT_DISABLED_BACKGROUND_COLOR);
        mBackgroundColorOpacityDisabled = typedArray.getFloat(R.styleable.CustomizableButton_cb_backgroundColorOpacityDisabled, DEFAULT_BACKGROUND_OPACITY);
        mTextColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorDisabled, DEFAULT_DISABLED_TEXT_COLOR);
        mBorderColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorDisabled, DEFAULT_DISABLED_BORDER_COLOR);
        mBorderThicknessDisabled = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessDisabled, mBorderThickness);
        mBorderRadiusDisabled = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusDisabled, mBorderRadius);

        typedArray.recycle();

    }

    /**
     * Setup the button
     */

    private void setupButton() {

        setButtonText();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }


        if (mUseRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setButtonBackground(getRippleDrawable());
        } else {
            setButtonBackground(getButtonBackgrounds());
        }

    }

    /**
     * @param drawable
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
        this.setTextSize(mTextSize);

    }

    /**
     * Setup the button for normal state
     */

    private Drawable getDrawableButtonDefault() {

        GradientDrawable drawableDefault = new GradientDrawable();

        drawableDefault.setCornerRadii(new float[] {mBorderRadius, mBorderRadius, mBorderRadius, mBorderRadius,
                mBorderRadius, mBorderRadius, mBorderRadius, mBorderRadius});

        drawableDefault.setStroke(mBorderThickness, mBorderColor);
        drawableDefault.setColor(ColorUtil.useOpacity(mBackgroundColor, mBackgroundColorOpacity));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(mElevation);

        return drawableDefault;

    }

    /**
     * Setup the button for pressed state
     */

    private Drawable getDrawableButtonPressed() {

        GradientDrawable drawablePressed = new GradientDrawable();

        drawablePressed.setCornerRadii(new float[] {mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed,
                mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed});

        drawablePressed.setStroke(mBorderThicknessPressed, mBorderColorPressed);
        drawablePressed.setColor(ColorUtil.useOpacity(mBackgroundColorPressed, mBackgroundColorOpacityPressed));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(mElevation);

        return drawablePressed;

    }

    /**
     * Setup the button for disabled state
     */

    private Drawable getDrawableButtonDisabled() {

        GradientDrawable drawableDisabled = new GradientDrawable();

        drawableDisabled.setCornerRadii(new float[] {mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled,
                mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled});


        drawableDisabled.setStroke(mBorderThicknessDisabled, mBorderColorDisabled);
        drawableDisabled.setColor(ColorUtil.useOpacity(mBackgroundColorDisabled, mBackgroundColorOpacityDisabled));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(mElevation);

        return drawableDisabled;

    }

    private Drawable getButtonBackgrounds() {
        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_enabled}, getDrawableButtonPressed());
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled}, getDrawableButtonPressed());
        states.addState(new int[] {-android.R.attr.state_enabled}, getDrawableButtonDisabled());
        states.addState(new int[] {}, getDrawableButtonDefault());

        return states;
    }

    private ColorStateList getButtonTextColors() {

        if (mUseRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mTextColorPressed = mTextColor;

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
                        mTextColor,
                        mTextColorPressed,
                        mTextColorDisabled,
                        mTextColor
                }
        );
    }

    /**
     * @return RippleDrawable
     * Create Ripple Effect for the button
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable() {

        Drawable drawableDefault = getDrawableButtonDefault();
        GradientDrawable mask = new GradientDrawable();

        if (drawableDefault.getConstantState() != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = (GradientDrawable) drawableDefault.getConstantState().newDrawable();
            mask.setColor(Color.WHITE);
        }

        if (mUseRippleEffect && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            mRippleColor = mBackgroundColorPressed;

        return new RippleDrawable(ColorUtil.getRippleColorFromColor(mRippleColor, mRippleAlpha), drawableDefault, mask);

    }

}
