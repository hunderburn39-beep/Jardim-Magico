package org.anddev.andengine.sensor;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class BaseSensorData {
    protected int mAccuracy;
    protected int mDisplayRotation;
    protected final float[] mValues;

    public BaseSensorData(int pValueCount, int pDisplayRotation) {
        this.mValues = new float[pValueCount];
        this.mDisplayRotation = pDisplayRotation;
    }

    public float[] getValues() {
        return this.mValues;
    }

    public void setValues(float[] pValues) {
        System.arraycopy(pValues, 0, this.mValues, 0, pValues.length);
    }

    public void setAccuracy(int pAccuracy) {
        this.mAccuracy = pAccuracy;
    }

    public int getAccuracy() {
        return this.mAccuracy;
    }

    public String toString() {
        return "Values: " + Arrays.toString(this.mValues);
    }
}
