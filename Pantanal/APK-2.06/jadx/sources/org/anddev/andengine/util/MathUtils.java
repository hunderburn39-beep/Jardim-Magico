package org.anddev.andengine.util;

import android.util.FloatMath;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Random;
import org.anddev.andengine.util.constants.MathConstants;

/* JADX INFO: loaded from: classes.dex */
public class MathUtils implements MathConstants {
    public static Random RANDOM = new Random(System.nanoTime());

    public static float atan2(float dY, float dX) {
        return (float) Math.atan2(dY, dX);
    }

    public static final float radToDeg(float pRad) {
        return 57.295776f * pRad;
    }

    public static final float degToRad(float pDegree) {
        return 0.017453292f * pDegree;
    }

    public static final int randomSign() {
        return RANDOM.nextBoolean() ? 1 : -1;
    }

    public static final float random(float pMin, float pMax) {
        return (RANDOM.nextFloat() * (pMax - pMin)) + pMin;
    }

    public static final int random(int pMin, int pMax) {
        return RANDOM.nextInt((pMax - pMin) + 1) + pMin;
    }

    public static final boolean isPowerOfTwo(int n) {
        return n != 0 && ((n + (-1)) & n) == 0;
    }

    public static final int nextPowerOfTwo(float f) {
        return nextPowerOfTwo((int) Math.ceil(f));
    }

    public static final int nextPowerOfTwo(int n) {
        if (n == 0) {
            return 1;
        }
        int k = n - 1;
        for (int i = 1; i < 32; i <<= 1) {
            k |= k >> i;
        }
        return k + 1;
    }

    public static final int sum(int[] pValues) {
        int sum = 0;
        for (int i = pValues.length - 1; i >= 0; i--) {
            sum += pValues[i];
        }
        return sum;
    }

    public static final void arraySumInternal(int[] pValues) {
        int valueCount = pValues.length;
        for (int i = 1; i < valueCount; i++) {
            pValues[i] = pValues[i - 1] + pValues[i];
        }
    }

    public static final void arraySumInternal(long[] pValues) {
        int valueCount = pValues.length;
        for (int i = 1; i < valueCount; i++) {
            pValues[i] = pValues[i - 1] + pValues[i];
        }
    }

    public static final void arraySumInternal(long[] pValues, long pFactor) {
        pValues[0] = pValues[0] * pFactor;
        int valueCount = pValues.length;
        for (int i = 1; i < valueCount; i++) {
            pValues[i] = pValues[i - 1] + (pValues[i] * pFactor);
        }
    }

    public static final void arraySumInto(long[] pValues, long[] pTargetValues, long pFactor) {
        pTargetValues[0] = pValues[0] * pFactor;
        int valueCount = pValues.length;
        for (int i = 1; i < valueCount; i++) {
            pTargetValues[i] = pTargetValues[i - 1] + (pValues[i] * pFactor);
        }
    }

    public static final float arraySum(float[] pValues) {
        float sum = BitmapDescriptorFactory.HUE_RED;
        for (float f : pValues) {
            sum += f;
        }
        return sum;
    }

    public static final float arrayAverage(float[] pValues) {
        return arraySum(pValues) / pValues.length;
    }

    public static float[] rotateAroundCenter(float[] pVertices, float pRotation, float pRotationCenterX, float pRotationCenterY) {
        if (pRotation != BitmapDescriptorFactory.HUE_RED) {
            float rotationRad = degToRad(pRotation);
            float sinRotationRad = FloatMath.sin(rotationRad);
            float cosRotationInRad = FloatMath.cos(rotationRad);
            for (int i = pVertices.length - 2; i >= 0; i -= 2) {
                float pX = pVertices[i];
                float pY = pVertices[i + 1];
                pVertices[i] = (((pX - pRotationCenterX) * cosRotationInRad) - ((pY - pRotationCenterY) * sinRotationRad)) + pRotationCenterX;
                pVertices[i + 1] = ((pX - pRotationCenterX) * sinRotationRad) + ((pY - pRotationCenterY) * cosRotationInRad) + pRotationCenterY;
            }
        }
        return pVertices;
    }

    public static float[] scaleAroundCenter(float[] pVertices, float pScaleX, float pScaleY, float pScaleCenterX, float pScaleCenterY) {
        if (pScaleX != 1.0f || pScaleY != 1.0f) {
            for (int i = pVertices.length - 2; i >= 0; i -= 2) {
                pVertices[i] = ((pVertices[i] - pScaleCenterX) * pScaleX) + pScaleCenterX;
                pVertices[i + 1] = ((pVertices[i + 1] - pScaleCenterY) * pScaleY) + pScaleCenterY;
            }
        }
        return pVertices;
    }

    public static float[] rotateAndScaleAroundCenter(float[] pVertices, float pRotation, float pRotationCenterX, float pRotationCenterY, float pScaleX, float pScaleY, float pScaleCenterX, float pScaleCenterY) {
        rotateAroundCenter(pVertices, pRotation, pRotationCenterX, pRotationCenterY);
        return scaleAroundCenter(pVertices, pScaleX, pScaleY, pScaleCenterX, pScaleCenterY);
    }

    public static float[] revertScaleAroundCenter(float[] pVertices, float pScaleX, float pScaleY, float pScaleCenterX, float pScaleCenterY) {
        return scaleAroundCenter(pVertices, 1.0f / pScaleX, 1.0f / pScaleY, pScaleCenterX, pScaleCenterY);
    }

    public static float[] revertRotateAroundCenter(float[] pVertices, float pRotation, float pRotationCenterX, float pRotationCenterY) {
        return rotateAroundCenter(pVertices, -pRotation, pRotationCenterX, pRotationCenterY);
    }

    public static float[] revertRotateAndScaleAroundCenter(float[] pVertices, float pRotation, float pRotationCenterX, float pRotationCenterY, float pScaleX, float pScaleY, float pScaleCenterX, float pScaleCenterY) {
        revertScaleAroundCenter(pVertices, pScaleX, pScaleY, pScaleCenterX, pScaleCenterY);
        return revertRotateAroundCenter(pVertices, pRotation, pRotationCenterX, pRotationCenterY);
    }

    public static int bringToBounds(int pMinValue, int pMaxValue, int pValue) {
        return Math.max(pMinValue, Math.min(pMaxValue, pValue));
    }

    public static float bringToBounds(float pMinValue, float pMaxValue, float pValue) {
        return Math.max(pMinValue, Math.min(pMaxValue, pValue));
    }

    public static float distance(float pX1, float pY1, float pX2, float pY2) {
        float dX = pX2 - pX1;
        float dY = pY2 - pY1;
        return FloatMath.sqrt((dX * dX) + (dY * dY));
    }
}
