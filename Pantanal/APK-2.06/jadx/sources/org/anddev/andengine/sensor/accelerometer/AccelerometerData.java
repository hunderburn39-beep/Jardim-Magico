package org.anddev.andengine.sensor.accelerometer;

import java.util.Arrays;
import org.anddev.andengine.sensor.BaseSensorData;

/* JADX INFO: loaded from: classes.dex */
public class AccelerometerData extends BaseSensorData {
    private static final IAxisSwap[] AXISSWAPS = new IAxisSwap[4];

    private interface IAxisSwap {
        void swapAxis(float[] fArr);
    }

    static {
        AXISSWAPS[0] = new IAxisSwap() { // from class: org.anddev.andengine.sensor.accelerometer.AccelerometerData.1
            @Override // org.anddev.andengine.sensor.accelerometer.AccelerometerData.IAxisSwap
            public void swapAxis(float[] pValues) {
                float x = -pValues[0];
                float y = pValues[1];
                pValues[0] = x;
                pValues[1] = y;
            }
        };
        AXISSWAPS[1] = new IAxisSwap() { // from class: org.anddev.andengine.sensor.accelerometer.AccelerometerData.2
            @Override // org.anddev.andengine.sensor.accelerometer.AccelerometerData.IAxisSwap
            public void swapAxis(float[] pValues) {
                float x = pValues[1];
                float y = pValues[0];
                pValues[0] = x;
                pValues[1] = y;
            }
        };
        AXISSWAPS[2] = new IAxisSwap() { // from class: org.anddev.andengine.sensor.accelerometer.AccelerometerData.3
            @Override // org.anddev.andengine.sensor.accelerometer.AccelerometerData.IAxisSwap
            public void swapAxis(float[] pValues) {
                float x = pValues[0];
                float y = -pValues[1];
                pValues[0] = x;
                pValues[1] = y;
            }
        };
        AXISSWAPS[3] = new IAxisSwap() { // from class: org.anddev.andengine.sensor.accelerometer.AccelerometerData.4
            @Override // org.anddev.andengine.sensor.accelerometer.AccelerometerData.IAxisSwap
            public void swapAxis(float[] pValues) {
                float x = -pValues[1];
                float y = -pValues[0];
                pValues[0] = x;
                pValues[1] = y;
            }
        };
    }

    public AccelerometerData(int pDisplayOrientation) {
        super(3, pDisplayOrientation);
    }

    public float getX() {
        return this.mValues[0];
    }

    public float getY() {
        return this.mValues[1];
    }

    public float getZ() {
        return this.mValues[2];
    }

    public void setX(float pX) {
        this.mValues[0] = pX;
    }

    public void setY(float pY) {
        this.mValues[1] = pY;
    }

    public void setZ(float pZ) {
        this.mValues[2] = pZ;
    }

    @Override // org.anddev.andengine.sensor.BaseSensorData
    public void setValues(float[] pValues) {
        super.setValues(pValues);
        AXISSWAPS[this.mDisplayRotation].swapAxis(this.mValues);
    }

    @Override // org.anddev.andengine.sensor.BaseSensorData
    public String toString() {
        return "Accelerometer: " + Arrays.toString(this.mValues);
    }
}
