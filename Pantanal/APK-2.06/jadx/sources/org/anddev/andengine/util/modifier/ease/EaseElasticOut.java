package org.anddev.andengine.util.modifier.ease;

import android.util.FloatMath;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.util.constants.MathConstants;

/* JADX INFO: loaded from: classes.dex */
public class EaseElasticOut implements IEaseFunction, MathConstants {
    private static EaseElasticOut INSTANCE;

    private EaseElasticOut() {
    }

    public static EaseElasticOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseElasticOut();
        }
        return INSTANCE;
    }

    @Override // org.anddev.andengine.util.modifier.ease.IEaseFunction
    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed, pDuration, pSecondsElapsed / pDuration);
    }

    public static float getValue(float pSecondsElapsed, float pDuration, float pPercentageDone) {
        if (pSecondsElapsed == BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        if (pSecondsElapsed == pDuration) {
            return 1.0f;
        }
        float p = pDuration * 0.3f;
        float s = p / 4.0f;
        return (((float) Math.pow(2.0d, (-10.0f) * pPercentageDone)) * FloatMath.sin((((pPercentageDone * pDuration) - s) * 6.2831855f) / p)) + 1.0f;
    }
}
