package org.anddev.andengine.util.modifier.ease;

import android.util.FloatMath;

/* JADX INFO: loaded from: classes.dex */
public class EaseCircularIn implements IEaseFunction {
    private static EaseCircularIn INSTANCE;

    private EaseCircularIn() {
    }

    public static EaseCircularIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseCircularIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return -(FloatMath.sqrt(1.0f - (pPercentage * pPercentage)) - 1.0f);
    }
}
