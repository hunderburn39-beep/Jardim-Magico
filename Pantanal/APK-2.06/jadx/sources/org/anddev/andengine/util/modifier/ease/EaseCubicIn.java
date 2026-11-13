package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseCubicIn implements IEaseFunction {
    private static EaseCubicIn INSTANCE;

    private EaseCubicIn() {
    }

    public static EaseCubicIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseCubicIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return pPercentage * pPercentage * pPercentage;
    }
}
