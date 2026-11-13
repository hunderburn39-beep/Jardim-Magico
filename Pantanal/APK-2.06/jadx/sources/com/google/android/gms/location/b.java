package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;

/* JADX INFO: loaded from: classes.dex */
public class b implements SafeParcelable {
    public static final c CREATOR = new c();
    int Oh;
    int Oi;
    long Oj;
    private final int xH;

    b(int i, int i2, int i3, long j) {
        this.xH = i;
        this.Oh = i2;
        this.Oi = i3;
        this.Oj = j;
    }

    private String by(int i) {
        switch (i) {
            case 0:
                return "STATUS_SUCCESSFUL";
            case 1:
            default:
                return "STATUS_UNKNOWN";
            case 2:
                return "STATUS_TIMED_OUT_ON_SCAN";
            case 3:
                return "STATUS_NO_INFO_IN_DATABASE";
            case 4:
                return "STATUS_INVALID_SCAN";
            case 5:
                return "STATUS_UNABLE_TO_QUERY_DATABASE";
            case 6:
                return "STATUS_SCANS_DISABLED_IN_SETTINGS";
            case 7:
                return "STATUS_LOCATION_DISABLED_IN_SETTINGS";
            case 8:
                return "STATUS_IN_PROGRESS";
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof b)) {
            return false;
        }
        b bVar = (b) other;
        return this.Oh == bVar.Oh && this.Oi == bVar.Oi && this.Oj == bVar.Oj;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Integer.valueOf(this.Oh), Integer.valueOf(this.Oi), Long.valueOf(this.Oj));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LocationStatus[cell status: ").append(by(this.Oh));
        sb.append(", wifi status: ").append(by(this.Oi));
        sb.append(", elapsed realtime ns: ").append(this.Oj);
        sb.append(']');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        c.a(this, parcel, flags);
    }
}
