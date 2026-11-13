package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseBounceInOut implements IEaseFunction {
    private static EaseBounceInOut INSTANCE;

    private EaseBounceInOut() {
    }

    public static EaseBounceInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseBounceInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseBounceIn.getValue(2.0f * percentage) * 0.5f : (EaseBounceOut.getValue((percentage * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
