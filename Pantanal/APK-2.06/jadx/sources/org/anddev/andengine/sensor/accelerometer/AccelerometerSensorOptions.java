package org.anddev.andengine.sensor.accelerometer;

import org.anddev.andengine.sensor.SensorDelay;

/* JADX INFO: loaded from: classes.dex */
public class AccelerometerSensorOptions {
    final SensorDelay mSensorDelay;

    public AccelerometerSensorOptions(SensorDelay pSensorDelay) {
        this.mSensorDelay = pSensorDelay;
    }

    public SensorDelay getSensorDelay() {
        return this.mSensorDelay;
    }
}
