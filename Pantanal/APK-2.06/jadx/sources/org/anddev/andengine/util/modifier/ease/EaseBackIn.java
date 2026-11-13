package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public class EaseBackIn implements IEaseFunction {
    private static EaseBackIn INSTANCE = null;
    private static final float OVERSHOOT_CONSTANT = 1.70158f;

    private EaseBackIn() {
    }

    public static EaseBackIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseBackIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return pPercentage * pPercentage * ((2.70158f * pPercentage) - OVERSHOOT_CONSTANT);
    }
}
