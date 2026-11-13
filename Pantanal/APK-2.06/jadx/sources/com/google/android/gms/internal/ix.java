package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class ix implements SafeParcelable {
    public static final Parcelable.Creator<ix> CREATOR = new iy();
    String[] act;
    byte[][] acu;
    private final int xH;

    ix() {
        this(1, new String[0], new byte[0][]);
    }

    ix(int i, String[] strArr, byte[][] bArr) {
        this.xH = i;
        this.act = strArr;
        this.acu = bArr;
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
        iy.a(this, out, flags);
    }
}
