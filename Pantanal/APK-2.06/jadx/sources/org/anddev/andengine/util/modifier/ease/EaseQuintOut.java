package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseQuintOut implements IEaseFunction {
    private static EaseQuintOut INSTANCE;

    private EaseQuintOut() {
    }

    public static EaseQuintOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuintOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        float t = pPercentage - 1.0f;
        return (t * t * t * t * t) + 1.0f;
    }
}
