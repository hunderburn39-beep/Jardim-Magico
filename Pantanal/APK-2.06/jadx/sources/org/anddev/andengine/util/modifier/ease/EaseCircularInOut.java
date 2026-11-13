package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseCircularInOut implements IEaseFunction {
    private static EaseCircularInOut INSTANCE;

    private EaseCircularInOut() {
    }

    public static EaseCircularInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseCircularInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseCircularIn.getValue(2.0f * percentage) * 0.5f : (EaseCircularOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
