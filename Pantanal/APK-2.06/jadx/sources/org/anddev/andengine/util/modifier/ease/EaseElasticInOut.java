package org.anddev.andengine.util.modifier.ease;

import org.anddev.andengine.util.constants.MathConstants;

/* JADX INFO: loaded from: classes.dex */
public class EaseElasticInOut implements IEaseFunction, MathConstants {
    private static EaseElasticInOut INSTANCE;

    private EaseElasticInOut() {
    }

    public static EaseElasticInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseElasticInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return percentage < 0.5f ? EaseElasticIn.getValue(2.0f * pSecondsElapsed, pDuration, 2.0f * percentage) * 0.5f : (EaseElasticOut.getValue((pSecondsElapsed * 2.0f) - pDuration, pDuration, (2.0f * percentage) - 1.0f) * 0.5f) + 0.5f;
    }
}
