package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseStrongIn implements IEaseFunction {
    private static EaseStrongIn INSTANCE;

    private EaseStrongIn() {
    }

    public static EaseStrongIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseStrongIn();
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
