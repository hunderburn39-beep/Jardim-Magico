package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class DetectedActivity implements SafeParcelable {
    public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 8;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;
    int NS;
    int NT;
    private final int xH;

    public DetectedActivity(int activityType, int confidence) {
        this.xH = 1;
        this.NS = activityType;
        this.NT = confidence;
    }

    public DetectedActivity(int versionCode, int activityType, int confidence) {
        this.xH = versionCode;
        this.NS = activityType;
        this.NT = confidence;
    }

    private int bv(int i) {
        if (i > 8) {
            return 4;
        }
        return i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getConfidence() {
        return this.NT;
    }

    public int getType() {
        return bv(this.NS);
    }

    public int getVersionCode() {
        return this.xH;
    }

    public String toString() {
        return "DetectedActivity [type=" + getType() + ", confidence=" + this.NT + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        DetectedActivityCreator.a(this, out, flags);
    }
}
