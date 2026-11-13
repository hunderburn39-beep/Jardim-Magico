package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseBounceOut implements IEaseFunction {
    private static EaseBounceOut INSTANCE;

    private EaseBounceOut() {
    }

    public static EaseBounceOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseBounceOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        if (pPercentage < 0.36363637f) {
            return 7.5625f * pPercentage * pPercentage;
        }
        if (pPercentage < 0.72727275f) {
            float t = pPercentage - 0.54545456f;
            return (7.5625f * t * t) + 0.75f;
        }
        if (pPercentage < 0.90909094f) {
            float t2 = pPercentage - 0.8181818f;
            return (7.5625f * t2 * t2) + 0.9375f;
        }
        float t3 = pPercentage - 0.95454544f;
        return (7.5625f * t3 * t3) + 0.984375f;
    }
}
