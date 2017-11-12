package com.github.gabrielgouv.customizablebuttons

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet

abstract class BaseButton : AppCompatButton {

    protected var mContext: Context
    protected lateinit var mAttrs: AttributeSet
    protected var mDefStyleAttr: Int = 0

    constructor(context: Context) : super(context) {
        mContext = context
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        mAttrs = attrs
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        mAttrs = attrs
        mDefStyleAttr = defStyleAttr
        this.init()
    }

    abstract fun init()

    protected fun setButtonBackground(drawable: Drawable) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.background = drawable
        } else {
            this.setBackgroundDrawable(drawable)
        }

    }

}