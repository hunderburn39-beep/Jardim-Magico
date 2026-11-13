package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class jm implements SafeParcelable {
    public static final Parcelable.Creator<jm> CREATOR = new jn();
    String add;
    String ade;
    ArrayList<jk> adf;
    private final int xH;

    jm() {
        this.xH = 1;
        this.adf = gi.fs();
    }

    jm(int i, String str, String str2, ArrayList<jk> arrayList) {
        this.xH = i;
        this.add = str;
        this.ade = str2;
        this.adf = arrayList;
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
        jn.a(this, dest, flags);
    }
}
