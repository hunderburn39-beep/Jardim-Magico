package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class iv implements SafeParcelable {
    public static final Parcelable.Creator<iv> CREATOR = new iw();
    int[] acs;
    private final int xH;

    iv() {
        this(1, null);
    }

    iv(int i, int[] iArr) {
        this.xH = i;
        this.acs = iArr;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        iw.a(this, out, flags);
    }
}
