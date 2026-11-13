package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseStrongInOut implements IEaseFunction {
    private static EaseStrongInOut INSTANCE;

    private EaseStrongInOut() {
    }

    public static EaseStrongInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseStrongInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseStrongIn.getValue(2.0f * percentage) * 0.5f : (EaseStrongOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
