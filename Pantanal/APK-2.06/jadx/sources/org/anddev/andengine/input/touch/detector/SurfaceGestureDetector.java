package org.anddev.andengine.input.touch.detector;

import android.view.GestureDetector;
import android.view.MotionEvent;
import org.anddev.andengine.input.touch.TouchEvent;

/* JADX INFO: loaded from: classes.dex */
public abstract class SurfaceGestureDetector extends BaseDetector {
    private static final float SWIPE_MIN_DISTANCE_DEFAULT = 120.0f;
    private final GestureDetector mGestureDetector;

    protected abstract boolean onDoubleTap();

    protected abstract boolean onSingleTap();

    protected abstract boolean onSwipeDown();

    protected abstract boolean onSwipeLeft();

    protected abstract boolean onSwipeRight();

    protected abstract boolean onSwipeUp();

    public SurfaceGestureDetector() {
        this(120.0f);
    }

    public SurfaceGestureDetector(float pSwipeMinDistance) {
        this.mGestureDetector = new GestureDetector(new InnerOnGestureDetectorListener(pSwipeMinDistance));
    }

    @Override // org.anddev.andengine.input.touch.detector.BaseDetector
    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        return this.mGestureDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());
    }

    private class InnerOnGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        private final float mSwipeMinDistance;

        public InnerOnGestureDetectorListener(float pSwipeMinDistance) {
            this.mSwipeMinDistance = pSwipeMinDistance;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent pMotionEvent) {
            return SurfaceGestureDetector.this.onSingleTap();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent pMotionEvent) {
            return SurfaceGestureDetector.this.onDoubleTap();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent pMotionEventStart, MotionEvent pMotionEventEnd, float pVelocityX, float pVelocityY) {
            float swipeMinDistance = this.mSwipeMinDistance;
            boolean isHorizontalFling = Math.abs(pVelocityX) > Math.abs(pVelocityY);
            if (isHorizontalFling) {
                if (pMotionEventStart.getX() - pMotionEventEnd.getX() > swipeMinDistance) {
                    return SurfaceGestureDetector.this.onSwipeLeft();
                }
                if (pMotionEventEnd.getX() - pMotionEventStart.getX() > swipeMinDistance) {
                    return SurfaceGestureDetector.this.onSwipeRight();
                }
                return false;
            }
            if (pMotionEventStart.getY() - pMotionEventEnd.getY() > swipeMinDistance) {
                return SurfaceGestureDetector.this.onSwipeUp();
            }
            if (pMotionEventEnd.getY() - pMotionEventStart.getY() > swipeMinDistance) {
                return SurfaceGestureDetector.this.onSwipeDown();
            }
            return false;
        }
    }

    public static class SurfaceGestureDetectorAdapter extends SurfaceGestureDetector {
        @Override // org.anddev.andengine.input.touch.detector.SurfaceGestureDetector
        protected boolean onDoubleTap() {
            return false;
        }

        @Override // org.anddev.andengine.input.touch.detector.SurfaceGestureDetector
        protected boolean onSingleTap() {
            return false;
        }

        @Override // org.anddev.andengine.input.touch.detector.SurfaceGestureDetector
        protected boolean onSwipeDown() {
            return false;
        }

        @Override // org.anddev.andengine.input.touch.detector.SurfaceGestureDetector
        protected boolean onSwipeLeft() {
            return false;
        }

        @Override // org.anddev.andengine.input.touch.detector.SurfaceGestureDetector
        protected boolean onSwipeRight() {
            return false;
        }

        @Override // org.anddev.andengine.input.touch.detector.SurfaceGestureDetector
        protected boolean onSwipeUp() {
            return false;
        }
    }
}
