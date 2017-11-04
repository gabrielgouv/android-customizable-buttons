package com.github.gabrielgouv.customizablebuttons.attribute;


public class DrawableAttributes {

    private int mBackgroundColor;
    private float mBackgroundOpacity;
    private int mBorderColor;
    private int mBorderThickness;
    private int mBorderRadius;

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    public float getBackgroundOpacity() {
        return mBackgroundOpacity;
    }

    public void setBackgroundOpacity(float backgroundOpacity) {
        this.mBackgroundOpacity = backgroundOpacity;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
    }

    public int getBorderThickness() {
        return mBorderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.mBorderThickness = borderThickness;
    }

    public int getBorderRadius() {
        return mBorderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.mBorderRadius = borderRadius;
    }
}
