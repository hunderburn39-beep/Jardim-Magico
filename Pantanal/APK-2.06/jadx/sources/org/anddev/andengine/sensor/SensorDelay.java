package org.anddev.andengine.sensor;

/* JADX INFO: loaded from: classes.dex */
public enum SensorDelay {
    NORMAL(3),
    UI(2),
    GAME(1),
    FASTEST(0);

    private final int mDelay;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static SensorDelay[] valuesCustom() {
        SensorDelay[] sensorDelayArrValuesCustom = values();
        int length = sensorDelayArrValuesCustom.length;
        SensorDelay[] sensorDelayArr = new SensorDelay[length];
        System.arraycopy(sensorDelayArrValuesCustom, 0, sensorDelayArr, 0, length);
        return sensorDelayArr;
    }

    SensorDelay(int pDelay) {
        this.mDelay = pDelay;
    }

    public int getDelay() {
        return this.mDelay;
    }
}
