package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class kk implements SafeParcelable, com.google.android.gms.wearable.f {
    public static final Parcelable.Creator<kk> CREATOR = new kl();
    private final String HA;
    private final String wp;
    final int xH;

    kk(int i, String str, String str2) {
        this.xH = i;
        this.wp = str;
        this.HA = str2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (!(o instanceof kk)) {
            return false;
        }
        kk kkVar = (kk) o;
        return kkVar.wp.equals(this.wp) && kkVar.HA.equals(this.HA);
    }

    public String getDisplayName() {
        return this.HA;
    }

    public String getId() {
        return this.wp;
    }

    public int hashCode() {
        return ((this.wp.hashCode() + 629) * 37) + this.HA.hashCode();
    }

    public String toString() {
        return "NodeParcelable{" + this.wp + "," + this.HA + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        kl.a(this, dest, flags);
    }
}
