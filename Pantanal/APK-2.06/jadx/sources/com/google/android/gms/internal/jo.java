package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class jo implements SafeParcelable {
    public static final Parcelable.Creator<jo> CREATOR = new jr();
    ju abJ;
    jp adg;
    String label;
    String type;
    private final int xH;

    jo() {
        this.xH = 1;
    }

    jo(int i, String str, jp jpVar, String str2, ju juVar) {
        this.xH = i;
        this.label = str;
        this.adg = jpVar;
        this.type = str2;
        this.abJ = juVar;
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
        jr.a(this, dest, flags);
    }
}
