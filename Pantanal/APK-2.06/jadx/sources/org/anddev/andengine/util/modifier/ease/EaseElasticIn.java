package org.anddev.andengine.util.modifier.ease;

import android.util.FloatMath;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.util.constants.MathConstants;

/* JADX INFO: loaded from: classes.dex */
public class EaseElasticIn implements IEaseFunction, MathConstants {
    private static EaseElasticIn INSTANCE;

    private EaseElasticIn() {
    }

    public static EaseElasticIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseElasticIn();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed, pDuration, pSecondsElapsed / pDuration);
    }

    public static float getValue(float pSecondsElapsed, float pDuration, float pPercentage) {
        if (pSecondsElapsed == BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        if (pSecondsElapsed == pDuration) {
            return 1.0f;
        }
        float p = pDuration * 0.3f;
        float s = p / 4.0f;
        float t = pPercentage - 1.0f;
        return (-((float) Math.pow(2.0d, 10.0f * t))) * FloatMath.sin((((t * pDuration) - s) * 6.2831855f) / p);
    }
}
