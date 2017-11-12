package com.github.gabrielgouv.customizablebuttons.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import com.github.gabrielgouv.customizablebuttons.attribute.DrawableAttributes
import com.github.gabrielgouv.customizablebuttons.attribute.RippleAttributes

object DrawableFactory {

    fun getBackgroundDrawable(drawableAttributes: DrawableAttributes): Drawable {

        val drawable = GradientDrawable()

        val cornerRadius = drawableAttributes.borderRadius.toFloat()

        drawable.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius)

        drawable.setStroke(drawableAttributes.borderThickness, drawableAttributes.borderColor)
        drawable.setColor(ColorUtil.useOpacity(drawableAttributes.backgroundColor, drawableAttributes.backgroundOpacity))

        return drawable

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun getRippleDrawable(drawableAttributes: DrawableAttributes, rippleAttributes: RippleAttributes): Drawable {

        val drawableNormal = DrawableFactory.getBackgroundDrawable(drawableAttributes)
        var mask = GradientDrawable()

        if (drawableNormal.constantState != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = drawableNormal.constantState!!.newDrawable() as GradientDrawable
            mask.setColor(Color.WHITE)
        }

        return RippleDrawable(ColorUtil.getRippleColorFromColor(rippleAttributes.rippleColor, rippleAttributes.rippleOpacity), drawableNormal, mask)

    }

}
