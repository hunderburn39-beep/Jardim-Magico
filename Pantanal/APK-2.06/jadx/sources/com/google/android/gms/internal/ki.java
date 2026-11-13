package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ki implements SafeParcelable, com.google.android.gms.wearable.e {
    public static final Parcelable.Creator<ki> CREATOR = new kj();
    private final int Eu;
    private final byte[] Nf;
    private final String adF;
    private final String adG;
    final int xH;

    ki(int i, int i2, String str, byte[] bArr, String str2) {
        this.xH = i;
        this.Eu = i2;
        this.adF = str;
        this.Nf = bArr;
        this.adG = str2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int fA() {
        return this.Eu;
    }

    public byte[] getData() {
        return this.Nf;
    }

    public String getPath() {
        return this.adF;
    }

    public String getSource() {
        return this.adG;
    }

    public String toString() {
        return "MessageEventParcelable[" + this.Eu + "," + this.adF + (this.Nf == null ? "null" : Integer.valueOf(this.Nf.length)) + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        kj.a(this, dest, flags);
    }
}
