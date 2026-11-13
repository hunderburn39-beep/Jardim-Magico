package org.anddev.andengine.engine.options;

/* JADX INFO: loaded from: classes.dex */
public enum WakeLockOptions {
    BRIGHT(26),
    SCREEN_BRIGHT(10),
    SCREEN_DIM(6),
    SCREEN_ON(-1);

    private final int mFlag;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static WakeLockOptions[] valuesCustom() {
        WakeLockOptions[] wakeLockOptionsArrValuesCustom = values();
        int length = wakeLockOptionsArrValuesCustom.length;
        WakeLockOptions[] wakeLockOptionsArr = new WakeLockOptions[length];
        System.arraycopy(wakeLockOptionsArrValuesCustom, 0, wakeLockOptionsArr, 0, length);
        return wakeLockOptionsArr;
    }

    WakeLockOptions(int pFlag) {
        this.mFlag = pFlag;
    }

    public int getFlag() {
        return this.mFlag;
    }
}
