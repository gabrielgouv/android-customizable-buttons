package com.github.gabrielgouv.customizablebuttons;

import android.content.res.ColorStateList;
import android.graphics.Color;

/**
 * Created by Gabriel Gouveia on 25/10/2017.
 */

public final class ColorUtil {

    public static ColorStateList getRippleColorFromColor(int color, int alpha) {

        ColorStateList c = ColorStateList.valueOf(color);
        c.withAlpha(alpha);

        return c;

    }

    public static int darkLightColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);

        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    public static int manipulateAlpha(int color, int alpha) {
        int r = Math.round(Color.red(color));
        int g = Math.round(Color.green(color));
        int b = Math.round(Color.blue(color));

        return Color.argb(Math.min(alpha, 255),
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

}
