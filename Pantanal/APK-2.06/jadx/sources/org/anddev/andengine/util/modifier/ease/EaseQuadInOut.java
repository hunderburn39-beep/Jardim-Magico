package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseQuadInOut implements IEaseFunction {
    private static EaseQuadInOut INSTANCE;

    private EaseQuadInOut() {
    }

    public static EaseQuadInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuadInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseQuadIn.getValue(2.0f * percentage) * 0.5f : (EaseQuadOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
