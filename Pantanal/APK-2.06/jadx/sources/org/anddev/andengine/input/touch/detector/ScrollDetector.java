package org.anddev.andengine.input.touch.detector;

import org.anddev.andengine.input.touch.TouchEvent;

/* JADX INFO: loaded from: classes.dex */
public class ScrollDetector extends BaseDetector {
    private static final float TRIGGER_SCROLL_MINIMUM_DISTANCE_DEFAULT = 10.0f;
    private float mLastX;
    private float mLastY;
    private final IScrollDetectorListener mScrollDetectorListener;
    private float mTriggerScrollMinimumDistance;
    private boolean mTriggered;

    public interface IScrollDetectorListener {
        void onScroll(ScrollDetector scrollDetector, TouchEvent touchEvent, float f, float f2);
    }

    public ScrollDetector(IScrollDetectorListener pScrollDetectorListener) {
        this(TRIGGER_SCROLL_MINIMUM_DISTANCE_DEFAULT, pScrollDetectorListener);
    }

    public ScrollDetector(float pTriggerScrollMinimumDistance, IScrollDetectorListener pScrollDetectorListener) {
        this.mTriggerScrollMinimumDistance = pTriggerScrollMinimumDistance;
        this.mScrollDetectorListener = pScrollDetectorListener;
    }

    public float getTriggerScrollMinimumDistance() {
        return this.mTriggerScrollMinimumDistance;
    }

    public void setTriggerScrollMinimumDistance(float pTriggerScrollMinimumDistance) {
        this.mTriggerScrollMinimumDistance = pTriggerScrollMinimumDistance;
    }

    @Override // org.anddev.andengine.input.touch.detector.BaseDetector
    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        float touchX = getX(pSceneTouchEvent);
        float touchY = getY(pSceneTouchEvent);
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                this.mLastX = touchX;
                this.mLastY = touchY;
                this.mTriggered = false;
                return true;
            case 1:
            case 2:
            case 3:
                float distanceX = touchX - this.mLastX;
                float distanceY = touchY - this.mLastY;
                float triggerScrollMinimumDistance = this.mTriggerScrollMinimumDistance;
                if (!this.mTriggered && Math.abs(distanceX) <= triggerScrollMinimumDistance && Math.abs(distanceY) <= triggerScrollMinimumDistance) {
                    return true;
                }
                this.mScrollDetectorListener.onScroll(this, pSceneTouchEvent, distanceX, distanceY);
                this.mLastX = touchX;
                this.mLastY = touchY;
                this.mTriggered = true;
                return true;
            default:
                return false;
        }
    }

    protected float getX(TouchEvent pTouchEvent) {
        return pTouchEvent.getX();
    }

    protected float getY(TouchEvent pTouchEvent) {
        return pTouchEvent.getY();
    }
}
