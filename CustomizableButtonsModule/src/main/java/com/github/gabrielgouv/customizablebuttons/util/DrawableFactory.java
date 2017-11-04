package com.github.gabrielgouv.customizablebuttons.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;


public final class DrawableFactory {

    public static Drawable getBackgroundDrawable(float cornerRadius, int borderThickness, int borderColor, int backgroundColor, float backgroundOpacity) {

        GradientDrawable drawable = new GradientDrawable();

        drawable.setCornerRadii(new float[] {cornerRadius, cornerRadius, cornerRadius, cornerRadius,
                cornerRadius, cornerRadius, cornerRadius, cornerRadius});

        drawable.setStroke(borderThickness, borderColor);
        drawable.setColor(ColorUtil.useOpacity(backgroundColor, backgroundOpacity));

        return drawable;

    }

}
