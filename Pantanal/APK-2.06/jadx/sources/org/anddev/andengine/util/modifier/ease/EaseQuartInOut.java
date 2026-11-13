package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseQuartInOut implements IEaseFunction {
    private static EaseQuartInOut INSTANCE;

    private EaseQuartInOut() {
    }

    public static EaseQuartInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuartInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseQuartIn.getValue(2.0f * percentage) * 0.5f : (EaseQuartOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
