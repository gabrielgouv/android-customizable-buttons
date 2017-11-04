package com.github.gabrielgouv.customizablebuttons.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.github.gabrielgouv.customizablebuttons.attribute.DrawableAttributes;


public final class DrawableFactory {

    public static Drawable getBackgroundDrawable(DrawableAttributes drawableAttributes) {

        GradientDrawable drawable = new GradientDrawable();

        float cornerRadius = drawableAttributes.getBorderRadius();

        drawable.setCornerRadii(new float[] {cornerRadius, cornerRadius, cornerRadius, cornerRadius,
                cornerRadius, cornerRadius, cornerRadius, cornerRadius});

        drawable.setStroke(drawableAttributes.getBorderThickness(), drawableAttributes.getBorderColor());
        drawable.setColor(ColorUtil.useOpacity(drawableAttributes.getBackgroundColor(), drawableAttributes.getBackgroundOpacity()));

        return drawable;

    }

}
