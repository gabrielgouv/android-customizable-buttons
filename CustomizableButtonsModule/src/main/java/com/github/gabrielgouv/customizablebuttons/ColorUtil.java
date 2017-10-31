package com.github.gabrielgouv.customizablebuttons;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;


public final class ColorUtil {

    static ColorStateList getRippleColorFromColor(int color, float opacity) {

        return ColorStateList.valueOf(useOpacity(color, opacity));

    }

    static int darkenLightenColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);

        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    static int useOpacity(int color, float opacity) {
        int a = Math.round(opacity * 255);
        int r = Math.round(Color.red(color));
        int g = Math.round(Color.green(color));
        int b = Math.round(Color.blue(color));

        return Color.argb(Math.min(a, 255),
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    /**
     * Check if a color is dark or light
     * @see [http://en.wikipedia.org/wiki/HSL_and_HSV#Lightness]
     *
     * @param color
     * @return true if the color is dark or false if light
     */
    static boolean isDarkColor(int color) {

        float r = Color.red(color) / 255f;
        float g = Color.green(color) / 255f;
        float b = Color.blue(color) / 255f;

        float luma = (r * 0.299f) + (g * 0.587f) + (b * 0.114f);

        return luma < 0.5;

    }

    static int getTextColorFromBackgroundColor(int color) {

        return isDarkColor(color) ? Color.WHITE : Color.BLACK;

    }

}
