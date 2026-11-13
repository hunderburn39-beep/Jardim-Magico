package org.anddev.andengine.input.touch.detector;

import android.os.SystemClock;
import android.view.MotionEvent;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.input.touch.TouchEvent;

/* JADX INFO: loaded from: classes.dex */
public class HoldDetector extends BaseDetector implements IUpdateHandler {
    private static final float TIME_BETWEEN_UPDATES_DEFAULT = 0.1f;
    private static final float TRIGGER_HOLD_MAXIMUM_DISTANCE_DEFAULT = 10.0f;
    private static final long TRIGGER_HOLD_MINIMUM_MILLISECONDS_DEFAULT = 200;
    private long mDownTimeMilliseconds;
    private float mDownX;
    private float mDownY;
    private final IHoldDetectorListener mHoldDetectorListener;
    private float mHoldX;
    private float mHoldY;
    private boolean mMaximumDistanceExceeded;
    private final TimerHandler mTimerHandler;
    private float mTriggerHoldMaximumDistance;
    private long mTriggerHoldMinimumMilliseconds;
    private boolean mTriggerOnHold;
    private boolean mTriggerOnHoldFinished;

    public interface IHoldDetectorListener {
        void onHold(HoldDetector holdDetector, long j, float f, float f2);

        void onHoldFinished(HoldDetector holdDetector, long j, float f, float f2);
    }

    public HoldDetector(IHoldDetectorListener pClickDetectorListener) {
        this(TRIGGER_HOLD_MINIMUM_MILLISECONDS_DEFAULT, TRIGGER_HOLD_MAXIMUM_DISTANCE_DEFAULT, TIME_BETWEEN_UPDATES_DEFAULT, pClickDetectorListener);
    }

    public HoldDetector(long pTriggerHoldMinimumMilliseconds, float pTriggerHoldMaximumDistance, float pTimeBetweenUpdates, IHoldDetectorListener pClickDetectorListener) {
        this.mDownTimeMilliseconds = Long.MIN_VALUE;
        this.mMaximumDistanceExceeded = false;
        this.mTriggerOnHold = false;
        this.mTriggerOnHoldFinished = false;
        this.mTriggerHoldMinimumMilliseconds = pTriggerHoldMinimumMilliseconds;
        this.mTriggerHoldMaximumDistance = pTriggerHoldMaximumDistance;
        this.mHoldDetectorListener = pClickDetectorListener;
        this.mTimerHandler = new TimerHandler(pTimeBetweenUpdates, true, new ITimerCallback() { // from class: org.anddev.andengine.input.touch.detector.HoldDetector.1
            @Override // org.anddev.andengine.engine.handler.timer.ITimerCallback
            public void onTimePassed(TimerHandler pTimerHandler) {
                HoldDetector.this.fireListener();
            }
        });
    }

    public long getTriggerHoldMinimumMilliseconds() {
        return this.mTriggerHoldMinimumMilliseconds;
    }

    public void setTriggerHoldMinimumMilliseconds(long pTriggerHoldMinimumMilliseconds) {
        this.mTriggerHoldMinimumMilliseconds = pTriggerHoldMinimumMilliseconds;
    }

    public float getTriggerHoldMaximumDistance() {
        return this.mTriggerHoldMaximumDistance;
    }

    public void setTriggerHoldMaximumDistance(float pTriggerHoldMaximumDistance) {
        this.mTriggerHoldMaximumDistance = pTriggerHoldMaximumDistance;
    }

    public boolean isHolding() {
        return this.mTriggerOnHold;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        this.mTimerHandler.onUpdate(pSecondsElapsed);
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mTimerHandler.reset();
    }

    @Override // org.anddev.andengine.input.touch.detector.BaseDetector
    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        MotionEvent motionEvent = pSceneTouchEvent.getMotionEvent();
        this.mHoldX = pSceneTouchEvent.getX();
        this.mHoldY = pSceneTouchEvent.getY();
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                this.mDownTimeMilliseconds = motionEvent.getDownTime();
                this.mDownX = motionEvent.getX();
                this.mDownY = motionEvent.getY();
                this.mMaximumDistanceExceeded = false;
                return true;
            case 1:
            case 3:
                long upTimeMilliseconds = motionEvent.getEventTime();
                float triggerHoldMaximumDistance = this.mTriggerHoldMaximumDistance;
                this.mMaximumDistanceExceeded = this.mMaximumDistanceExceeded || Math.abs(this.mDownX - motionEvent.getX()) > triggerHoldMaximumDistance || Math.abs(this.mDownY - motionEvent.getY()) > triggerHoldMaximumDistance;
                if (!this.mTriggerOnHold && this.mMaximumDistanceExceeded) {
                    return true;
                }
                long holdTimeMilliseconds = upTimeMilliseconds - this.mDownTimeMilliseconds;
                if (holdTimeMilliseconds < this.mTriggerHoldMinimumMilliseconds) {
                    return true;
                }
                this.mTriggerOnHoldFinished = true;
                return true;
            case 2:
                long currentTimeMilliseconds = motionEvent.getEventTime();
                float triggerHoldMaximumDistance2 = this.mTriggerHoldMaximumDistance;
                this.mMaximumDistanceExceeded = this.mMaximumDistanceExceeded || Math.abs(this.mDownX - motionEvent.getX()) > triggerHoldMaximumDistance2 || Math.abs(this.mDownY - motionEvent.getY()) > triggerHoldMaximumDistance2;
                if (!this.mTriggerOnHold && this.mMaximumDistanceExceeded) {
                    return true;
                }
                long holdTimeMilliseconds2 = currentTimeMilliseconds - this.mDownTimeMilliseconds;
                if (holdTimeMilliseconds2 < this.mTriggerHoldMinimumMilliseconds) {
                    return true;
                }
                this.mTriggerOnHold = true;
                return true;
            default:
                return false;
        }
    }

    protected void fireListener() {
        if (this.mTriggerOnHoldFinished) {
            this.mHoldDetectorListener.onHoldFinished(this, SystemClock.uptimeMillis() - this.mDownTimeMilliseconds, this.mHoldX, this.mHoldY);
            this.mTriggerOnHoldFinished = false;
            this.mTriggerOnHold = false;
        } else if (this.mTriggerOnHold) {
            this.mHoldDetectorListener.onHold(this, SystemClock.uptimeMillis() - this.mDownTimeMilliseconds, this.mHoldX, this.mHoldY);
        }
    }
}
