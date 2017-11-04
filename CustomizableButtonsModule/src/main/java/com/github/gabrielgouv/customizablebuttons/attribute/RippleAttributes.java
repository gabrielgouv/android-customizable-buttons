package com.github.gabrielgouv.customizablebuttons.attribute;


public class RippleAttributes {

    private boolean mUseRippleEffect;
    private int mRippleColor;
    private float mRippleOpacity;

    public boolean isUseRippleEffect() {
        return mUseRippleEffect;
    }

    public void setUseRippleEffect(boolean useRippleEffect) {
        this.mUseRippleEffect = useRippleEffect;
    }

    public int getRippleColor() {
        return mRippleColor;
    }

    public void setRippleColor(int rippleColor) {
        this.mRippleColor = rippleColor;
    }

    public float getRippleOpacity() {
        return mRippleOpacity;
    }

    public void setRippleOpacity(float rippleOpacity) {
        this.mRippleOpacity = rippleOpacity;
    }
}
