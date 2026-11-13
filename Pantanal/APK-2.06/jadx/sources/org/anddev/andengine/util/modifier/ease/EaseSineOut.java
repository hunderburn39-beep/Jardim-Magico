package org.anddev.andengine.util.modifier.ease;

import android.util.FloatMath;
import org.anddev.andengine.util.constants.MathConstants;

/* JADX INFO: loaded from: classes.dex */
public class EaseSineOut implements IEaseFunction, MathConstants {
    private static EaseSineOut INSTANCE;

    private EaseSineOut() {
    }

    public static EaseSineOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseSineOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return FloatMath.sin(1.5707964f * pPercentage);
    }
}
