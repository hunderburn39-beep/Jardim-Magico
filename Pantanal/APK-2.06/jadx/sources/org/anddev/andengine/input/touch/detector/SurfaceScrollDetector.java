package org.anddev.andengine.input.touch.detector;

import org.anddev.andengine.input.touch.TouchEvent;

/* JADX INFO: loaded from: classes.dex */
public class SurfaceScrollDetector extends ScrollDetector {
    public SurfaceScrollDetector(float pTriggerScrollMinimumDistance, ScrollDetector.IScrollDetectorListener pScrollDetectorListener) {
        super(pTriggerScrollMinimumDistance, pScrollDetectorListener);
    }

    public SurfaceScrollDetector(ScrollDetector.IScrollDetectorListener pScrollDetectorListener) {
        super(pScrollDetectorListener);
    }

    @Override // org.anddev.andengine.input.touch.detector.ScrollDetector
    protected float getX(TouchEvent pTouchEvent) {
        return pTouchEvent.getMotionEvent().getX();
    }

    @Override // org.anddev.andengine.input.touch.detector.ScrollDetector
    protected float getY(TouchEvent pTouchEvent) {
        return pTouchEvent.getMotionEvent().getY();
    }
}
