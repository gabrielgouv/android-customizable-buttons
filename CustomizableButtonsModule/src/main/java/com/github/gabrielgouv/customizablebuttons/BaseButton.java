package com.github.gabrielgouv.customizablebuttons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public abstract class BaseButton extends AppCompatButton {

    protected Context mContext;
    protected AttributeSet mAttrs;
    protected int mDefStyleAttr;

    public BaseButton(Context context) {

        super(context);
        mContext = context;
        init();

    }

    public BaseButton(Context context, AttributeSet attrs) {

        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        init();

    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttrs = attrs;
        mDefStyleAttr = defStyleAttr;
        init();

    }

    protected abstract void init();

    protected void setButtonBackground(Drawable drawable) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(drawable);
        } else {
            this.setBackgroundDrawable(drawable);
        }

    }

}
