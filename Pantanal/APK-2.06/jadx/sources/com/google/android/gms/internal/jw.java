package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class jw implements SafeParcelable {
    public static final Parcelable.Creator<jw> CREATOR = new jx();
    String adq;
    String description;
    private final int xH;

    jw() {
        this.xH = 1;
    }

    jw(int i, String str, String str2) {
        this.xH = i;
        this.adq = str;
        this.description = str2;
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
        jx.a(this, dest, flags);
    }
}
