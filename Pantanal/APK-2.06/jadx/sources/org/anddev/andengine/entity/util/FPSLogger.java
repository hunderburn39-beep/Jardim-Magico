package org.anddev.andengine.entity.util;

import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public class FPSLogger extends AverageFPSCounter {
    protected float mLongestFrame;
    protected float mShortestFrame;

    public FPSLogger() {
        this.mShortestFrame = Float.MAX_VALUE;
        this.mLongestFrame = Float.MIN_VALUE;
    }

    public FPSLogger(float pAverageDuration) {
        super(pAverageDuration);
        this.mShortestFrame = Float.MAX_VALUE;
        this.mLongestFrame = Float.MIN_VALUE;
    }

    @Override // org.anddev.andengine.entity.util.AverageFPSCounter
    protected void onHandleAverageDurationElapsed(float pFPS) {
        onLogFPS();
        this.mLongestFrame = Float.MIN_VALUE;
        this.mShortestFrame = Float.MAX_VALUE;
    }

    @Override // org.anddev.andengine.entity.util.AverageFPSCounter, org.anddev.andengine.entity.util.FPSCounter, org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        this.mShortestFrame = Math.min(this.mShortestFrame, pSecondsElapsed);
        this.mLongestFrame = Math.max(this.mLongestFrame, pSecondsElapsed);
    }

    @Override // org.anddev.andengine.entity.util.FPSCounter, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        this.mShortestFrame = Float.MAX_VALUE;
        this.mLongestFrame = Float.MIN_VALUE;
    }

    protected void onLogFPS() {
        Debug.d(String.format("FPS: %.2f (MIN: %.0f ms | MAX: %.0f ms)", Float.valueOf(this.mFrames / this.mSecondsElapsed), Float.valueOf(this.mShortestFrame * 1000.0f), Float.valueOf(this.mLongestFrame * 1000.0f)));
    }
}
