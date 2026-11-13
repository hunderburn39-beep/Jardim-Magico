package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class js implements SafeParcelable {
    public static final Parcelable.Creator<js> CREATOR = new jt();
    String adn;
    String pm;
    private final int xH;

    js() {
        this.xH = 1;
    }

    js(int i, String str, String str2) {
        this.xH = i;
        this.adn = str;
        this.pm = str2;
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
        jt.a(this, dest, flags);
    }
}
