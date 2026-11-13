package org.anddev.andengine.util.modifier.ease;

import android.util.FloatMath;
import org.anddev.andengine.util.constants.MathConstants;

/* JADX INFO: loaded from: classes.dex */
public class EaseSineIn implements IEaseFunction, MathConstants {
    private static EaseSineIn INSTANCE;

    private EaseSineIn() {
    }

    public static EaseSineIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseSineIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return (-FloatMath.cos(1.5707964f * pPercentage)) + 1.0f;
    }
}
