package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public final class LocationRequest implements SafeParcelable {
    public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    long NV;
    long Oc;
    long Od;
    boolean Oe;
    int Of;
    float Og;
    int mPriority;
    private final int xH;

    public LocationRequest() {
        this.xH = 1;
        this.mPriority = 102;
        this.Oc = 3600000L;
        this.Od = 600000L;
        this.Oe = false;
        this.NV = Long.MAX_VALUE;
        this.Of = Integer.MAX_VALUE;
        this.Og = BitmapDescriptorFactory.HUE_RED;
    }

    LocationRequest(int versionCode, int priority, long interval, long fastestInterval, boolean explicitFastestInterval, long expireAt, int numUpdates, float smallestDisplacement) {
        this.xH = versionCode;
        this.mPriority = priority;
        this.Oc = interval;
        this.Od = fastestInterval;
        this.Oe = explicitFastestInterval;
        this.NV = expireAt;
        this.Of = numUpdates;
        this.Og = smallestDisplacement;
    }

    private static void a(float f) {
        if (f < BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("invalid displacement: " + f);
        }
    }

    private static void bw(int i) {
        switch (i) {
            case 100:
            case 102:
            case PRIORITY_LOW_POWER /* 104 */:
            case PRIORITY_NO_POWER /* 105 */:
                return;
            case 101:
            case 103:
            default:
                throw new IllegalArgumentException("invalid quality: " + i);
        }
    }

    public static String bx(int i) {
        switch (i) {
            case 100:
                return "PRIORITY_HIGH_ACCURACY";
            case 101:
            case 103:
            default:
                return "???";
            case 102:
                return "PRIORITY_BALANCED_POWER_ACCURACY";
            case PRIORITY_LOW_POWER /* 104 */:
                return "PRIORITY_LOW_POWER";
            case PRIORITY_NO_POWER /* 105 */:
                return "PRIORITY_NO_POWER";
        }
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    private static void s(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("invalid interval: " + j);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocationRequest)) {
            return false;
        }
        LocationRequest locationRequest = (LocationRequest) object;
        return this.mPriority == locationRequest.mPriority && this.Oc == locationRequest.Oc && this.Od == locationRequest.Od && this.Oe == locationRequest.Oe && this.NV == locationRequest.NV && this.Of == locationRequest.Of && this.Og == locationRequest.Og;
    }

    public long getExpirationTime() {
        return this.NV;
    }

    public long getFastestInterval() {
        return this.Od;
    }

    public long getInterval() {
        return this.Oc;
    }

    public int getNumUpdates() {
        return this.Of;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public float getSmallestDisplacement() {
        return this.Og;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Integer.valueOf(this.mPriority), Long.valueOf(this.Oc), Long.valueOf(this.Od), Boolean.valueOf(this.Oe), Long.valueOf(this.NV), Integer.valueOf(this.Of), Float.valueOf(this.Og));
    }

    public LocationRequest setExpirationDuration(long millis) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (millis > Long.MAX_VALUE - jElapsedRealtime) {
            this.NV = Long.MAX_VALUE;
        } else {
            this.NV = jElapsedRealtime + millis;
        }
        if (this.NV < 0) {
            this.NV = 0L;
        }
        return this;
    }

    public LocationRequest setExpirationTime(long millis) {
        this.NV = millis;
        if (this.NV < 0) {
            this.NV = 0L;
        }
        return this;
    }

    public LocationRequest setFastestInterval(long millis) {
        s(millis);
        this.Oe = true;
        this.Od = millis;
        return this;
    }

    public LocationRequest setInterval(long millis) {
        s(millis);
        this.Oc = millis;
        if (!this.Oe) {
            this.Od = (long) (this.Oc / 6.0d);
        }
        return this;
    }

    public LocationRequest setNumUpdates(int numUpdates) {
        if (numUpdates <= 0) {
            throw new IllegalArgumentException("invalid numUpdates: " + numUpdates);
        }
        this.Of = numUpdates;
        return this;
    }

    public LocationRequest setPriority(int priority) {
        bw(priority);
        this.mPriority = priority;
        return this;
    }

    public LocationRequest setSmallestDisplacement(float smallestDisplacementMeters) {
        a(smallestDisplacementMeters);
        this.Og = smallestDisplacementMeters;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[").append(bx(this.mPriority));
        if (this.mPriority != 105) {
            sb.append(" requested=");
            sb.append(this.Oc + "ms");
        }
        sb.append(" fastest=");
        sb.append(this.Od + "ms");
        if (this.NV != Long.MAX_VALUE) {
            long jElapsedRealtime = this.NV - SystemClock.elapsedRealtime();
            sb.append(" expireIn=");
            sb.append(jElapsedRealtime + "ms");
        }
        if (this.Of != Integer.MAX_VALUE) {
            sb.append(" num=").append(this.Of);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        LocationRequestCreator.a(this, parcel, flags);
    }
}
