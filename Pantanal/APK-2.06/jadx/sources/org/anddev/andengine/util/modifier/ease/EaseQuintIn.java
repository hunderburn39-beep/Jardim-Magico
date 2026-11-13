package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseQuintIn implements IEaseFunction {
    private static EaseQuintIn INSTANCE;

    private EaseQuintIn() {
    }

    public static EaseQuintIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuintIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return pPercentage * pPercentage * pPercentage * pPercentage * pPercentage;
    }
}
