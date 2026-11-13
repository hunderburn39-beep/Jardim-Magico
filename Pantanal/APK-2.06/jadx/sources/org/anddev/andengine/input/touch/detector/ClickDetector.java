package org.anddev.andengine.input.touch.detector;

import org.anddev.andengine.input.touch.TouchEvent;

/* JADX INFO: loaded from: classes.dex */
public class ClickDetector extends BaseDetector {
    private static final long TRIGGER_CLICK_MAXIMUM_MILLISECONDS_DEFAULT = 200;
    private final IClickDetectorListener mClickDetectorListener;
    private long mDownTimeMilliseconds;
    private long mTriggerClickMaximumMilliseconds;

    public interface IClickDetectorListener {
        void onClick(ClickDetector clickDetector, TouchEvent touchEvent);
    }

    public ClickDetector(IClickDetectorListener pClickDetectorListener) {
        this(TRIGGER_CLICK_MAXIMUM_MILLISECONDS_DEFAULT, pClickDetectorListener);
    }

    public ClickDetector(long pTriggerClickMaximumMilliseconds, IClickDetectorListener pClickDetectorListener) {
        this.mDownTimeMilliseconds = Long.MIN_VALUE;
        this.mTriggerClickMaximumMilliseconds = pTriggerClickMaximumMilliseconds;
        this.mClickDetectorListener = pClickDetectorListener;
    }

    public long getTriggerClickMaximumMilliseconds() {
        return this.mTriggerClickMaximumMilliseconds;
    }

    public void setTriggerClickMaximumMilliseconds(long pClickMaximumMilliseconds) {
        this.mTriggerClickMaximumMilliseconds = pClickMaximumMilliseconds;
    }

    @Override // org.anddev.andengine.input.touch.detector.BaseDetector
    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                this.mDownTimeMilliseconds = pSceneTouchEvent.getMotionEvent().getDownTime();
                return true;
            case 1:
            case 3:
                long upTimeMilliseconds = pSceneTouchEvent.getMotionEvent().getEventTime();
                if (upTimeMilliseconds - this.mDownTimeMilliseconds > this.mTriggerClickMaximumMilliseconds) {
                    return true;
                }
                this.mDownTimeMilliseconds = Long.MIN_VALUE;
                this.mClickDetectorListener.onClick(this, pSceneTouchEvent);
                return true;
            case 2:
            default:
                return false;
        }
    }
}
