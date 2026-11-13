package org.anddev.andengine.engine.camera;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class BoundCamera extends Camera {
    private float mBoundsCenterX;
    private float mBoundsCenterY;
    protected boolean mBoundsEnabled;
    private float mBoundsHeight;
    private float mBoundsMaxX;
    private float mBoundsMaxY;
    private float mBoundsMinX;
    private float mBoundsMinY;
    private float mBoundsWidth;

    public BoundCamera(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight);
    }

    public BoundCamera(float pX, float pY, float pWidth, float pHeight, float pBoundMinX, float pBoundMaxX, float pBoundMinY, float pBoundMaxY) {
        super(pX, pY, pWidth, pHeight);
        setBounds(pBoundMinX, pBoundMaxX, pBoundMinY, pBoundMaxY);
        this.mBoundsEnabled = true;
    }

    public boolean isBoundsEnabled() {
        return this.mBoundsEnabled;
    }

    public void setBoundsEnabled(boolean pBoundsEnabled) {
        this.mBoundsEnabled = pBoundsEnabled;
    }

    public void setBounds(float pBoundMinX, float pBoundMaxX, float pBoundMinY, float pBoundMaxY) {
        this.mBoundsMinX = pBoundMinX;
        this.mBoundsMaxX = pBoundMaxX;
        this.mBoundsMinY = pBoundMinY;
        this.mBoundsMaxY = pBoundMaxY;
        this.mBoundsWidth = this.mBoundsMaxX - this.mBoundsMinX;
        this.mBoundsHeight = this.mBoundsMaxY - this.mBoundsMinY;
        this.mBoundsCenterX = this.mBoundsMinX + (this.mBoundsWidth * 0.5f);
        this.mBoundsCenterY = this.mBoundsMinY + (this.mBoundsHeight * 0.5f);
    }

    public float getBoundsWidth() {
        return this.mBoundsWidth;
    }

    public float getBoundsHeight() {
        return this.mBoundsHeight;
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    public void setCenter(float pCenterX, float pCenterY) {
        super.setCenter(pCenterX, pCenterY);
        if (this.mBoundsEnabled) {
            ensureInBounds();
        }
    }

    protected void ensureInBounds() {
        super.setCenter(determineBoundedX(), determineBoundedY());
    }

    private float determineBoundedX() {
        if (this.mBoundsWidth < getWidth()) {
            return this.mBoundsCenterX;
        }
        float currentCenterX = getCenterX();
        float minXBoundExceededAmount = this.mBoundsMinX - getMinX();
        boolean minXBoundExceeded = minXBoundExceededAmount > BitmapDescriptorFactory.HUE_RED;
        float maxXBoundExceededAmount = getMaxX() - this.mBoundsMaxX;
        boolean maxXBoundExceeded = maxXBoundExceededAmount > BitmapDescriptorFactory.HUE_RED;
        if (minXBoundExceeded) {
            if (maxXBoundExceeded) {
                return (currentCenterX - maxXBoundExceededAmount) + minXBoundExceededAmount;
            }
            return currentCenterX + minXBoundExceededAmount;
        }
        if (maxXBoundExceeded) {
            return currentCenterX - maxXBoundExceededAmount;
        }
        return currentCenterX;
    }

    private float determineBoundedY() {
        if (this.mBoundsHeight < getHeight()) {
            return this.mBoundsCenterY;
        }
        float currentCenterY = getCenterY();
        float minYBoundExceededAmount = this.mBoundsMinY - getMinY();
        boolean minYBoundExceeded = minYBoundExceededAmount > BitmapDescriptorFactory.HUE_RED;
        float maxYBoundExceededAmount = getMaxY() - this.mBoundsMaxY;
        boolean maxYBoundExceeded = maxYBoundExceededAmount > BitmapDescriptorFactory.HUE_RED;
        if (minYBoundExceeded) {
            if (maxYBoundExceeded) {
                return (currentCenterY - maxYBoundExceededAmount) + minYBoundExceededAmount;
            }
            return currentCenterY + minYBoundExceededAmount;
        }
        if (maxYBoundExceeded) {
            return currentCenterY - maxYBoundExceededAmount;
        }
        return currentCenterY;
    }
}
