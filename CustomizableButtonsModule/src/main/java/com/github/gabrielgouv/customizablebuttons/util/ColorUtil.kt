package com.github.gabrielgouv.customizablebuttons.util

import android.content.res.ColorStateList
import android.graphics.Color

object ColorUtil {

    fun getRippleColorFromColor(color: Int, opacity: Float): ColorStateList {

        return ColorStateList.valueOf(useOpacity(color, opacity))

    }

    fun darkenLightenColor(color: Int, factor: Float): Int {

        return Color.argb(Color.alpha(color),
                Math.min(Math.round(Color.red(color) * factor), 255),
                Math.min(Math.round(Color.green(color) * factor), 255),
                Math.min(Math.round(Color.blue(color) * factor), 255))

    }

    fun useOpacity(color: Int, opacity: Float): Int {

        return Color.argb(Math.min(Math.round(opacity * 255), 255),
                Math.min(Math.round(Color.red(color).toFloat()), 255),
                Math.min(Math.round(Color.green(color).toFloat()), 255),
                Math.min(Math.round(Color.blue(color).toFloat()), 255))

    }

    /**
     * Check if a color is dark or light
     * @see [http://en.wikipedia.org/wiki/HSL_and_HSV.Lightness]
     *
     *
     * @param color
     * @return true if the color is dark or false if light
     */
    fun isDarkColor(color: Int): Boolean {

        val r = Color.red(color) / 255f
        val g = Color.green(color) / 255f
        val b = Color.blue(color) / 255f

        val luma = r * 0.299f + g * 0.587f + b * 0.114f

        return luma < 0.5

    }

    fun getTextColorFromBackgroundColor(color: Int): Int {

        return if (isDarkColor(color)) Color.WHITE else Color.BLACK

    }

}

