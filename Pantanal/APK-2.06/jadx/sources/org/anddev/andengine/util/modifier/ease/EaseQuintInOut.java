package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseQuintInOut implements IEaseFunction {
    private static EaseQuintInOut INSTANCE;

    private EaseQuintInOut() {
    }

    public static EaseQuintInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuintInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseQuintIn.getValue(2.0f * percentage) * 0.5f : (EaseQuintOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
