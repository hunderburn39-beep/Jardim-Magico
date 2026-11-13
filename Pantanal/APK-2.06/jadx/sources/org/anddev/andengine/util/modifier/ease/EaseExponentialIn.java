package org.anddev.andengine.util.modifier.ease;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class EaseExponentialIn implements IEaseFunction {
    private static EaseExponentialIn INSTANCE;

    private EaseExponentialIn() {
    }

    public static EaseExponentialIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseExponentialIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return (float) (pPercentage == BitmapDescriptorFactory.HUE_RED ? 0.0d : Math.pow(2.0d, 10.0f * (pPercentage - 1.0f)) - 0.0010000000474974513d);
    }
}
