package org.anddev.andengine.engine.camera;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class SmoothCamera extends ZoomCamera {
    private float mMaxVelocityX;
    private float mMaxVelocityY;
    private float mMaxZoomFactorChange;
    private float mTargetCenterX;
    private float mTargetCenterY;
    private float mTargetZoomFactor;

    public SmoothCamera(float pX, float pY, float pWidth, float pHeight, float pMaxVelocityX, float pMaxVelocityY, float pMaxZoomFactorChange) {
        super(pX, pY, pWidth, pHeight);
        this.mMaxVelocityX = pMaxVelocityX;
        this.mMaxVelocityY = pMaxVelocityY;
        this.mMaxZoomFactorChange = pMaxZoomFactorChange;
        this.mTargetCenterX = getCenterX();
        this.mTargetCenterY = getCenterY();
        this.mTargetZoomFactor = 1.0f;
    }

    @Override // org.anddev.andengine.engine.camera.BoundCamera, org.anddev.andengine.engine.camera.Camera
    public void setCenter(float pCenterX, float pCenterY) {
        this.mTargetCenterX = pCenterX;
        this.mTargetCenterY = pCenterY;
    }

    public void setCenterDirect(float pCenterX, float pCenterY) {
        super.setCenter(pCenterX, pCenterY);
        this.mTargetCenterX = pCenterX;
        this.mTargetCenterY = pCenterY;
    }

    @Override // org.anddev.andengine.engine.camera.ZoomCamera
    public void setZoomFactor(float pZoomFactor) {
        if (this.mTargetZoomFactor != pZoomFactor) {
            if (this.mTargetZoomFactor == this.mZoomFactor) {
                this.mTargetZoomFactor = pZoomFactor;
                onSmoothZoomStarted();
            } else {
                this.mTargetZoomFactor = pZoomFactor;
            }
        }
    }

    public void setZoomFactorDirect(float pZoomFactor) {
        if (this.mTargetZoomFactor != this.mZoomFactor) {
            this.mTargetZoomFactor = pZoomFactor;
            super.setZoomFactor(pZoomFactor);
            onSmoothZoomFinished();
        } else {
            this.mTargetZoomFactor = pZoomFactor;
            super.setZoomFactor(pZoomFactor);
        }
    }

    public void setMaxVelocityX(float pMaxVelocityX) {
        this.mMaxVelocityX = pMaxVelocityX;
    }

    public void setMaxVelocityY(float pMaxVelocityY) {
        this.mMaxVelocityY = pMaxVelocityY;
    }

    public void setMaxVelocity(float pMaxVelocityX, float pMaxVelocityY) {
        this.mMaxVelocityX = pMaxVelocityX;
        this.mMaxVelocityY = pMaxVelocityY;
    }

    public void setMaxZoomFactorChange(float pMaxZoomFactorChange) {
        this.mMaxZoomFactorChange = pMaxZoomFactorChange;
    }

    protected void onSmoothZoomStarted() {
    }

    protected void onSmoothZoomFinished() {
    }

    @Override // org.anddev.andengine.engine.camera.Camera, org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        float currentCenterX = getCenterX();
        float currentCenterY = getCenterY();
        float targetCenterX = this.mTargetCenterX;
        float targetCenterY = this.mTargetCenterY;
        if (currentCenterX != targetCenterX || currentCenterY != targetCenterY) {
            float diffX = targetCenterX - currentCenterX;
            float dX = limitToMaxVelocityX(diffX, pSecondsElapsed);
            float diffY = targetCenterY - currentCenterY;
            float dY = limitToMaxVelocityY(diffY, pSecondsElapsed);
            super.setCenter(currentCenterX + dX, currentCenterY + dY);
        }
        float currentZoom = getZoomFactor();
        float targetZoomFactor = this.mTargetZoomFactor;
        if (currentZoom != targetZoomFactor) {
            float absoluteZoomDifference = targetZoomFactor - currentZoom;
            float zoomChange = limitToMaxZoomFactorChange(absoluteZoomDifference, pSecondsElapsed);
            super.setZoomFactor(currentZoom + zoomChange);
            if (this.mZoomFactor == this.mTargetZoomFactor) {
                onSmoothZoomFinished();
            }
        }
    }

    private float limitToMaxVelocityX(float pValue, float pSecondsElapsed) {
        return pValue > BitmapDescriptorFactory.HUE_RED ? Math.min(pValue, this.mMaxVelocityX * pSecondsElapsed) : Math.max(pValue, (-this.mMaxVelocityX) * pSecondsElapsed);
    }

    private float limitToMaxVelocityY(float pValue, float pSecondsElapsed) {
        return pValue > BitmapDescriptorFactory.HUE_RED ? Math.min(pValue, this.mMaxVelocityY * pSecondsElapsed) : Math.max(pValue, (-this.mMaxVelocityY) * pSecondsElapsed);
    }

    private float limitToMaxZoomFactorChange(float pValue, float pSecondsElapsed) {
        return pValue > BitmapDescriptorFactory.HUE_RED ? Math.min(pValue, this.mMaxZoomFactorChange * pSecondsElapsed) : Math.max(pValue, (-this.mMaxZoomFactorChange) * pSecondsElapsed);
    }
}
