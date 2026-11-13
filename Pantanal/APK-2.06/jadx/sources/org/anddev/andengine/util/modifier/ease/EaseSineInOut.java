package org.anddev.andengine.util.modifier.ease;

import android.util.FloatMath;

/* JADX INFO: loaded from: classes.dex */
public class EaseSineInOut implements IEaseFunction {
    private static EaseSineInOut INSTANCE;

    private EaseSineInOut() {
    }

    public static EaseSineInOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseSineInOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        float percentage = pSecondsElapsed / pDuration;
        return (-0.5f) * (FloatMath.cos(3.1415927f * percentage) - 1.0f);
    }
}
