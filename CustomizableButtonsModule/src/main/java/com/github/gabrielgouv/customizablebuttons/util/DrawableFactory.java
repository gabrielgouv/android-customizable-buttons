package com.github.gabrielgouv.customizablebuttons.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.github.gabrielgouv.customizablebuttons.attribute.DrawableAttributes;
import com.github.gabrielgouv.customizablebuttons.attribute.RippleAttributes;


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getRippleDrawable(DrawableAttributes drawableAttributes, RippleAttributes rippleAttributes) {

        Drawable drawableNormal = DrawableFactory.getBackgroundDrawable(drawableAttributes);
        GradientDrawable mask = new GradientDrawable();

        if (drawableNormal.getConstantState() != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = (GradientDrawable) drawableNormal.getConstantState().newDrawable();
            mask.setColor(Color.WHITE);
        }

        return new RippleDrawable(ColorUtil.getRippleColorFromColor(rippleAttributes.getRippleColor(), rippleAttributes.getRippleOpacity()), drawableNormal, mask);

    }

}
