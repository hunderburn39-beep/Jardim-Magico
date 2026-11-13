package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseLinear implements IEaseFunction {
    private static EaseLinear INSTANCE;

    private EaseLinear() {
    }

    public static EaseLinear getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseLinear();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return pSecondsElapsed / pDuration;
    }
}
