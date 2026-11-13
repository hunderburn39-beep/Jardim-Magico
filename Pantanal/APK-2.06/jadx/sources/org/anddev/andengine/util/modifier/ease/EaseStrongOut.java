package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseStrongOut implements IEaseFunction {
    private static EaseStrongOut INSTANCE;

    private EaseStrongOut() {
    }

    public static EaseStrongOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseStrongOut();
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
