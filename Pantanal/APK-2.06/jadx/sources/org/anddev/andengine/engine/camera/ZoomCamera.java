package org.anddev.andengine.engine.camera;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class ZoomCamera extends BoundCamera {
    protected float mZoomFactor;

    public ZoomCamera(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight);
        this.mZoomFactor = 1.0f;
    }

    public float getZoomFactor() {
        return this.mZoomFactor;
    }

    public void setZoomFactor(float pZoomFactor) {
        this.mZoomFactor = pZoomFactor;
        if (this.mBoundsEnabled) {
            ensureInBounds();
        }
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    public float getMinX() {
        if (this.mZoomFactor == 1.0f) {
            return super.getMinX();
        }
        float centerX = getCenterX();
        return centerX - ((centerX - super.getMinX()) / this.mZoomFactor);
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    public float getMaxX() {
        if (this.mZoomFactor == 1.0f) {
            return super.getMaxX();
        }
        float centerX = getCenterX();
        return ((super.getMaxX() - centerX) / this.mZoomFactor) + centerX;
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    public float getMinY() {
        if (this.mZoomFactor == 1.0f) {
            return super.getMinY();
        }
        float centerY = getCenterY();
        return centerY - ((centerY - super.getMinY()) / this.mZoomFactor);
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    public float getMaxY() {
        if (this.mZoomFactor == 1.0f) {
            return super.getMaxY();
        }
        float centerY = getCenterY();
        return ((super.getMaxY() - centerY) / this.mZoomFactor) + centerY;
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    public float getWidth() {
        return super.getWidth() / this.mZoomFactor;
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    public float getHeight() {
        return super.getHeight() / this.mZoomFactor;
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    protected void applySceneToCameraSceneOffset(TouchEvent pSceneTouchEvent) {
        float zoomFactor = this.mZoomFactor;
        if (zoomFactor != 1.0f) {
            float scaleCenterX = getCenterX();
            float scaleCenterY = getCenterY();
            VERTICES_TOUCH_TMP[0] = pSceneTouchEvent.getX();
            VERTICES_TOUCH_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.scaleAroundCenter(VERTICES_TOUCH_TMP, zoomFactor, zoomFactor, scaleCenterX, scaleCenterY);
            pSceneTouchEvent.set(VERTICES_TOUCH_TMP[0], VERTICES_TOUCH_TMP[1]);
        }
        super.applySceneToCameraSceneOffset(pSceneTouchEvent);
    }

    @Override // org.anddev.andengine.engine.camera.Camera
    protected void unapplySceneToCameraSceneOffset(TouchEvent pCameraSceneTouchEvent) {
        super.unapplySceneToCameraSceneOffset(pCameraSceneTouchEvent);
        float zoomFactor = this.mZoomFactor;
        if (zoomFactor != 1.0f) {
            float scaleCenterX = getCenterX();
            float scaleCenterY = getCenterY();
            VERTICES_TOUCH_TMP[0] = pCameraSceneTouchEvent.getX();
            VERTICES_TOUCH_TMP[1] = pCameraSceneTouchEvent.getY();
            MathUtils.revertScaleAroundCenter(VERTICES_TOUCH_TMP, zoomFactor, zoomFactor, scaleCenterX, scaleCenterY);
            pCameraSceneTouchEvent.set(VERTICES_TOUCH_TMP[0], VERTICES_TOUCH_TMP[1]);
        }
    }
}
