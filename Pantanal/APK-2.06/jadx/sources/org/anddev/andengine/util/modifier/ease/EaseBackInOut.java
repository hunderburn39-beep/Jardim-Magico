package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseBackInOut implements IEaseFunction {
    private static EaseBackInOut INSTANCE;

    private EaseBackInOut() {
    }

    public static EaseBackInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseBackInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseBackIn.getValue(2.0f * percentage) * 0.5f : (EaseBackOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
