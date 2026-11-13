package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseExponentialInOut implements IEaseFunction {
    private static EaseExponentialInOut INSTANCE;

    private EaseExponentialInOut() {
    }

    public static EaseExponentialInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseExponentialInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseExponentialIn.getValue(2.0f * percentage) * 0.5f : (EaseExponentialOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
