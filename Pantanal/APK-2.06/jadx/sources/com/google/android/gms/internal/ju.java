package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class ju implements SafeParcelable {
    public static final Parcelable.Creator<ju> CREATOR = new jv();
    long ado;
    long adp;
    private final int xH;

    ju() {
        this.xH = 1;
    }

    ju(int i, long j, long j2) {
        this.xH = i;
        this.ado = j;
        this.adp = j2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        jv.a(this, dest, flags);
    }
}
