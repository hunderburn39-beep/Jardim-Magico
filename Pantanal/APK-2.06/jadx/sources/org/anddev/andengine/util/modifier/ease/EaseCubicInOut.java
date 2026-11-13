package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseCubicInOut implements IEaseFunction {
    private static EaseCubicInOut INSTANCE;

    private EaseCubicInOut() {
    }

    public static EaseCubicInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseCubicInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseCubicIn.getValue(2.0f * percentage) * 0.5f : (EaseCubicOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
