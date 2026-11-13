package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseExponentialOut implements IEaseFunction {
    private static EaseExponentialOut INSTANCE;

    private EaseExponentialOut() {
    }

    public static EaseExponentialOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseExponentialOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        if (pPercentage == 1.0f) {
            return 1.0f;
        }
        return 1.0f + (-((float) Math.pow(2.0d, (-10.0f) * pPercentage)));
    }
}
