package org.anddev.andengine.engine.handler.timer;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.engine.handler.IUpdateHandler;

/* JADX INFO: loaded from: classes.dex */
public class TimerHandler implements IUpdateHandler {
    private boolean mAutoReset;
    protected final ITimerCallback mTimerCallback;
    private boolean mTimerCallbackTriggered;
    private float mTimerSeconds;
    private float mTimerSecondsElapsed;

    public TimerHandler(float pTimerSeconds, ITimerCallback pTimerCallback) {
        this(pTimerSeconds, false, pTimerCallback);
    }

    public TimerHandler(float pTimerSeconds, boolean pAutoReset, ITimerCallback pTimerCallback) {
        this.mTimerSeconds = pTimerSeconds;
        this.mAutoReset = pAutoReset;
        this.mTimerCallback = pTimerCallback;
    }

    public boolean isAutoReset() {
        return this.mAutoReset;
    }

    public void setAutoReset(boolean pAutoReset) {
        this.mAutoReset = pAutoReset;
    }

    public void setTimerSeconds(float pTimerSeconds) {
        this.mTimerSeconds = pTimerSeconds;
    }

    public float getTimerSeconds() {
        return this.mTimerSeconds;
    }

    public float getTimerSecondsElapsed() {
        return this.mTimerSecondsElapsed;
    }

    public boolean isTimerCallbackTriggered() {
        return this.mTimerCallbackTriggered;
    }

    public void setTimerCallbackTriggered(boolean pTimerCallbackTriggered) {
        this.mTimerCallbackTriggered = pTimerCallbackTriggered;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        if (this.mAutoReset) {
            this.mTimerSecondsElapsed += pSecondsElapsed;
            while (this.mTimerSecondsElapsed >= this.mTimerSeconds) {
                this.mTimerSecondsElapsed -= this.mTimerSeconds;
                this.mTimerCallback.onTimePassed(this);
            }
            return;
        }
        if (!this.mTimerCallbackTriggered) {
            this.mTimerSecondsElapsed += pSecondsElapsed;
            if (this.mTimerSecondsElapsed >= this.mTimerSeconds) {
                this.mTimerCallbackTriggered = true;
                this.mTimerCallback.onTimePassed(this);
            }
        }
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mTimerCallbackTriggered = false;
        this.mTimerSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
    }
}
