package org.anddev.andengine.sensor.location;

import android.location.Criteria;

/* JADX INFO: loaded from: classes.dex */
public class LocationSensorOptions extends Criteria {
    private static final long MINIMUMTRIGGERDISTANCE_DEFAULT = 10;
    private static final long MINIMUMTRIGGERTIME_DEFAULT = 1000;
    private boolean mEnabledOnly;
    private long mMinimumTriggerDistance;
    private long mMinimumTriggerTime;

    public LocationSensorOptions() {
        this.mEnabledOnly = true;
        this.mMinimumTriggerTime = 1000L;
        this.mMinimumTriggerDistance = MINIMUMTRIGGERDISTANCE_DEFAULT;
    }

    public LocationSensorOptions(int pAccuracy, boolean pAltitudeRequired, boolean pBearingRequired, boolean pCostAllowed, int pPowerRequirement, boolean pSpeedRequired, boolean pEnabledOnly, long pMinimumTriggerTime, long pMinimumTriggerDistance) {
        this.mEnabledOnly = true;
        this.mMinimumTriggerTime = 1000L;
        this.mMinimumTriggerDistance = MINIMUMTRIGGERDISTANCE_DEFAULT;
        this.mEnabledOnly = pEnabledOnly;
        this.mMinimumTriggerTime = pMinimumTriggerTime;
        this.mMinimumTriggerDistance = pMinimumTriggerDistance;
        setAccuracy(pAccuracy);
        setAltitudeRequired(pAltitudeRequired);
        setBearingRequired(pBearingRequired);
        setCostAllowed(pCostAllowed);
        setPowerRequirement(pPowerRequirement);
        setSpeedRequired(pSpeedRequired);
    }

    public void setEnabledOnly(boolean pEnabledOnly) {
        this.mEnabledOnly = pEnabledOnly;
    }

    public boolean isEnabledOnly() {
        return this.mEnabledOnly;
    }

    public long getMinimumTriggerTime() {
        return this.mMinimumTriggerTime;
    }

    public void setMinimumTriggerTime(long pMinimumTriggerTime) {
        this.mMinimumTriggerTime = pMinimumTriggerTime;
    }

    public long getMinimumTriggerDistance() {
        return this.mMinimumTriggerDistance;
    }

    public void setMinimumTriggerDistance(long pMinimumTriggerDistance) {
        this.mMinimumTriggerDistance = pMinimumTriggerDistance;
    }
}
