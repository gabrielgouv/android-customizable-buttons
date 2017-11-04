package com.github.gabrielgouv.customizablebuttons.util;

import android.content.res.ColorStateList;
import android.graphics.Color;


public final class ColorUtil {

    public static ColorStateList getRippleColorFromColor(int color, float opacity) {

        return ColorStateList.valueOf(useOpacity(color, opacity));

    }

    public static int darkenLightenColor(int color, float factor) {

        return Color.argb(Color.alpha(color),
                Math.min(Math.round(Color.red(color) * factor), 255),
                Math.min(Math.round(Color.green(color) * factor), 255),
                Math.min(Math.round(Color.blue(color) * factor), 255));

    }

    public static int useOpacity(int color, float opacity) {

        return Color.argb(Math.min(Math.round(opacity * 255), 255),
                Math.min(Math.round(Color.red(color)), 255),
                Math.min(Math.round(Color.green(color)), 255),
                Math.min(Math.round(Color.blue(color)), 255));

    }

    /**
     * Check if a color is dark or light
     * @see [http://en.wikipedia.org/wiki/HSL_and_HSV#Lightness]
     *
     * @param color
     * @return true if the color is dark or false if light
     */
    public static boolean isDarkColor(int color) {

        float r = Color.red(color) / 255f;
        float g = Color.green(color) / 255f;
        float b = Color.blue(color) / 255f;

        float luma = (r * 0.299f) + (g * 0.587f) + (b * 0.114f);

        return luma < 0.5;

    }

    public static int getTextColorFromBackgroundColor(int color) {

        return isDarkColor(color) ? Color.WHITE : Color.BLACK;

    }

}
