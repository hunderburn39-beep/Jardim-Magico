package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseQuadOut implements IEaseFunction {
    private static EaseQuadOut INSTANCE;

    private EaseQuadOut() {
    }

    public static EaseQuadOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuadOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return (-pPercentage) * (pPercentage - 2.0f);
    }
}
