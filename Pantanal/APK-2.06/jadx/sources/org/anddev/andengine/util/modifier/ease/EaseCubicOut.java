package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseCubicOut implements IEaseFunction {
    private static EaseCubicOut INSTANCE;

    private EaseCubicOut() {
    }

    public static EaseCubicOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseCubicOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        float t = pPercentage - 1.0f;
        return (t * t * t) + 1.0f;
    }
}
