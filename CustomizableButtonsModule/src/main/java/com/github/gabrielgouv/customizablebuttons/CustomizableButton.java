package com.github.gabrielgouv.customizablebuttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class CustomizableButton extends AppCompatButton {

    public static final int DEFAULT_RIPPLE_ALPHA = 40;
    public static final int DEFAULT_TEXT_COLOR = 0xff808080;
    public static final int DEFAULT_TEXT_SIZE = 15;
    public static final int DEFAULT_BACKGROUND_COLOR = 0xffcccccc;
    public static final int DEFAULT_RIPPLE_COLOR = 0xffffffff;
    public static final int DEFAULT_BORDER_COLOR = 0xff000000;
    public static final int DEFAULT_BORDER_THICKNESS = 0;
    public static final int DEFAULT_BORDER_RADIUS = 2;
    public static final float DEFAULT_COLOR_FACTOR = 0.9f;
    public static final int DEFAULT_DISABLED_BACKGROUND_COLOR = 0xffefefef;
    public static final int DEFAULT_DISABLED_TEXT_COLOR = 0xffbdbdbd;
    public static final int DEFAULT_ELEVATION = 0;
    public static final boolean DEFAULT_USE_RIPPLE_EFFECT = false;
    public static final boolean DEFAULT_BUTTON_ENABLED = true;
    public static final boolean DEFAULT_OUTLINE = false;
    public static final int DEFAULT_ALPHA = 255;

    private Context mContext;
    private AttributeSet mAttrs;
    private int mDefStyleAttr;

    private String mText;
    private boolean mUseRippleEffect;
    private int mRippleColor;
    private int mRippleAlpha;
    private boolean mEnabled;
    private int mTextSize;
    private boolean mOutline;

    private int mTextColor;
    private int mBackgroundColor;
    private int mBorderColor;
    private int mBorderThickness;
    private int mBorderRadius;
    private int mElevation;

    private int mTextColorPressed;
    private int mBackgroundColorPressed;
    private int mBorderColorPressed;
    private int mBorderThicknessPressed;
    private int mBorderRadiusPressed;
    private int mElevationPressed;

    private int mTextColorDisabled;
    private int mBackgroundColorDisabled;
    private int mBorderColorDisabled;
    private int mBorderThicknessDisabled;
    private int mBorderRadiusDisabled;
    private int mElevationDisabled;

    private GradientDrawable mDrawableDefault;
    private GradientDrawable mDrawablePressed;
    private GradientDrawable mDrawableDisabled;

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

    private void initAttributes() {

        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.CustomizableButton, mDefStyleAttr, 0);

        mText = typedArray.getString(R.styleable.CustomizableButton_cb_text);
        if (mText == null)
            mText = typedArray.getString(R.styleable.CustomizableButton_android_text);

        mTextSize = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_textSize, DEFAULT_TEXT_SIZE);
        mUseRippleEffect = typedArray.getBoolean(R.styleable.CustomizableButton_cb_useRippleEffect, DEFAULT_USE_RIPPLE_EFFECT);
        mRippleColor = typedArray.getColor(R.styleable.CustomizableButton_cb_rippleColor, DEFAULT_RIPPLE_COLOR);
        mRippleAlpha = typedArray.getInt(R.styleable.CustomizableButton_cb_rippleAlpha, DEFAULT_RIPPLE_ALPHA);
        mEnabled = typedArray.getBoolean(R.styleable.CustomizableButton_android_enabled, DEFAULT_BUTTON_ENABLED);
        mOutline = typedArray.getBoolean(R.styleable.CustomizableButton_cb_outline, DEFAULT_OUTLINE);

        // default
        mTextColor = typedArray.getColor(R.styleable.CustomizableButton_cb_textColor, DEFAULT_TEXT_COLOR);
        if (!mOutline)
            mBackgroundColor = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColor, DEFAULT_BACKGROUND_COLOR);
        mBorderColor = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColor, DEFAULT_BORDER_COLOR);
        mBorderThickness = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThickness, DEFAULT_BORDER_THICKNESS);
        mBorderRadius = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadius, DEFAULT_BORDER_RADIUS);
        mElevation = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_elevation, DEFAULT_ELEVATION);

        // pressed
        mTextColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorPressed, mTextColor);
        mBackgroundColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorPressed, ColorUtil.darkLightColor(mBackgroundColor, DEFAULT_COLOR_FACTOR));
        mBorderColorPressed = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorPressed, mBorderColor);
        mBorderThicknessPressed = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessPressed, mBorderThickness);
        mBorderRadiusPressed = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusPressed, mBorderRadius);
        mElevationPressed = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_elevationPressed, mElevation);

        // disabled
        mTextColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_textColorDisabled, DEFAULT_DISABLED_TEXT_COLOR);
        mBackgroundColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_backgroundColorDisabled, DEFAULT_DISABLED_BACKGROUND_COLOR);
        mBorderColorDisabled = typedArray.getColor(R.styleable.CustomizableButton_cb_borderColorDisabled, DEFAULT_BORDER_COLOR);
        mBorderThicknessDisabled = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderThicknessDisabled, DEFAULT_BORDER_THICKNESS);
        mBorderRadiusDisabled = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_borderRadiusDisabled, DEFAULT_BORDER_RADIUS);
        mElevationDisabled = (int) typedArray.getDimension(R.styleable.CustomizableButton_cb_elevationDisabled, mElevation);

        typedArray.recycle();

    }

    /**
     * Setup the button
     */

    private void setupButton() {

        setupButtonDefault();
        setButtonText();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }

        if (mOutline) {
            mBackgroundColor = Color.TRANSPARENT;
            mBackgroundColorPressed = ColorUtil.manipulateAlpha(mBorderColor, DEFAULT_RIPPLE_ALPHA);
        }

        if (mEnabled) {

            if (mUseRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setButtonBackground(getRippleDrawable());
                return;
            }

            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            setupButtonPressed();
                            break;

                        case MotionEvent.ACTION_UP:
                            setupButtonDefault();
                            break;

                    }

                    performClick();

                    return false;

                }
            });

        } else {

            setupButtonDisabled();

        }

    }

    @Override
    public boolean performClick() {

        return super.performClick();

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
     * Set the button text
     */

    private void setButtonText() {

        this.setText(mText);
        this.setTextSize(mTextSize);

    }

    /**
     * Setup the button for default state
     */

    private void setupButtonDefault() {

        mDrawableDefault = new GradientDrawable();
        mDrawableDefault.setCornerRadii(new float[] {mBorderRadius, mBorderRadius, mBorderRadius, mBorderRadius,
                mBorderRadius, mBorderRadius, mBorderRadius, mBorderRadius});
        mDrawableDefault.setStroke(mBorderThickness, mBorderColor);
        mDrawableDefault.setColor(mBackgroundColor);

        setButtonBackground(mDrawableDefault);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(mElevation);

        this.setTextColor(mTextColor);

    }

    /**
     * Setup the button for pressed state
     */

    private void setupButtonPressed() {

        mDrawablePressed = new GradientDrawable();
        mDrawablePressed.setCornerRadii(new float[] {mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed,
                mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed, mBorderRadiusPressed});
        mDrawablePressed.setStroke(mBorderThicknessPressed, mBorderColorPressed);
        mDrawablePressed.setColor(mBackgroundColorPressed);

        setButtonBackground(mDrawablePressed);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(mElevationPressed);

        this.setTextColor(mTextColorPressed);

    }

    /**
     * Setup the button for disabled state
     */

    private void setupButtonDisabled() {

        mDrawableDisabled = new GradientDrawable();
        mDrawableDisabled.setCornerRadii(new float[] {mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled,
                mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled, mBorderRadiusDisabled});
        mDrawableDisabled.setStroke(mBorderThicknessDisabled, mBorderColorDisabled);
        mDrawableDisabled.setColor(mBackgroundColorDisabled);

        setButtonBackground(mDrawableDisabled);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(mElevationDisabled);

        this.setTextColor(mTextColorDisabled);

    }


    /**
     * @return RippleDrawable
     * Generates Ripple Effect for the button
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable() {

        // Create a mask for GradientDrawable
        GradientDrawable mask = new GradientDrawable();

        if (mDrawableDefault.getConstantState() != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = (GradientDrawable) mDrawableDefault.getConstantState().newDrawable();
            mask.setColor(Color.WHITE);
        }

        if (mOutline)
            mRippleColor = mBorderColor;

        return new RippleDrawable(ColorUtil.getRippleColorFromColor(mRippleColor, mRippleAlpha), mDrawableDefault, mask);

    }

}
