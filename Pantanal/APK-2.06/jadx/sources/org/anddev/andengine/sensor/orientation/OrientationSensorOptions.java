package org.anddev.andengine.sensor.orientation;

import org.anddev.andengine.sensor.SensorDelay;

/* JADX INFO: loaded from: classes.dex */
public class OrientationSensorOptions {
    final SensorDelay mSensorDelay;

    public OrientationSensorOptions(SensorDelay pSensorDelay) {
        this.mSensorDelay = pSensorDelay;
    }

    public SensorDelay getSensorDelay() {
        return this.mSensorDelay;
    }
}
