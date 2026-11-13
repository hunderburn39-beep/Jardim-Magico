package org.anddev.andengine.sensor.location;

/* JADX INFO: loaded from: classes.dex */
public enum LocationProviderStatus {
    AVAILABLE,
    OUT_OF_SERVICE,
    TEMPORARILY_UNAVAILABLE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static LocationProviderStatus[] valuesCustom() {
        LocationProviderStatus[] locationProviderStatusArrValuesCustom = values();
        int length = locationProviderStatusArrValuesCustom.length;
        LocationProviderStatus[] locationProviderStatusArr = new LocationProviderStatus[length];
        System.arraycopy(locationProviderStatusArrValuesCustom, 0, locationProviderStatusArr, 0, length);
        return locationProviderStatusArr;
    }
}
