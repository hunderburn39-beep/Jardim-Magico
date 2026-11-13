package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class jk implements SafeParcelable {
    public static final Parcelable.Creator<jk> CREATOR = new jl();
    String label;
    String value;
    private final int xH;

    jk() {
        this.xH = 1;
    }

    jk(int i, String str, String str2) {
        this.xH = i;
        this.label = str;
        this.value = str2;
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
        jl.a(this, dest, flags);
    }
}
