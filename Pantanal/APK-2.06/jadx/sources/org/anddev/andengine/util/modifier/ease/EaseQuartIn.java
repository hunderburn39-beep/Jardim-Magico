package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseQuartIn implements IEaseFunction {
    private static EaseQuartIn INSTANCE;

    private EaseQuartIn() {
    }

    public static EaseQuartIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuartIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return pPercentage * pPercentage * pPercentage * pPercentage;
    }
}
