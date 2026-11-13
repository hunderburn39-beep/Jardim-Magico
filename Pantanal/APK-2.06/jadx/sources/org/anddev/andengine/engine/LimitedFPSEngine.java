package org.anddev.andengine.engine;

import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public class LimitedFPSEngine extends Engine {
    private final long mPreferredFrameLengthNanoseconds;

    public LimitedFPSEngine(EngineOptions pEngineOptions, int pFramesPerSecond) {
        super(pEngineOptions);
        this.mPreferredFrameLengthNanoseconds = TimeConstants.NANOSECONDSPERSECOND / ((long) pFramesPerSecond);
    }

    @Override // org.anddev.andengine.engine.Engine
    public void onUpdate(long pNanosecondsElapsed) throws InterruptedException {
        long preferredFrameLengthNanoseconds = this.mPreferredFrameLengthNanoseconds;
        long deltaFrameLengthNanoseconds = preferredFrameLengthNanoseconds - pNanosecondsElapsed;
        if (deltaFrameLengthNanoseconds <= 0) {
            super.onUpdate(pNanosecondsElapsed);
            return;
        }
        int sleepTimeMilliseconds = (int) (deltaFrameLengthNanoseconds / TimeConstants.NANOSECONDSPERMILLISECOND);
        Thread.sleep(sleepTimeMilliseconds);
        super.onUpdate(pNanosecondsElapsed + deltaFrameLengthNanoseconds);
    }
}
