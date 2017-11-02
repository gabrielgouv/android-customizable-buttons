package com.github.gabrielgouv.customizablebuttons;

import android.content.res.Resources;


public final class DimensionUtil {

    public static int pxToDip(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dipToPx(float dip) {
        return (int) (dip * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToSp(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    public static int spToPx(final float sp) {
        return (int) (sp * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

}
