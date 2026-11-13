package com.google.android.gms.plus.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;

/* JADX INFO: loaded from: classes.dex */
public class PlusCommonExtras implements SafeParcelable {
    private String Uh;
    private String Ui;
    private final int xH;
    public static String TAG = "PlusCommonExtras";
    public static final f CREATOR = new f();

    public PlusCommonExtras() {
        this.xH = 1;
        this.Uh = "";
        this.Ui = "";
    }

    PlusCommonExtras(int versionCode, String gpsrc, String clientCallingPackage) {
        this.xH = versionCode;
        this.Uh = gpsrc;
        this.Ui = clientCallingPackage;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlusCommonExtras)) {
            return false;
        }
        PlusCommonExtras plusCommonExtras = (PlusCommonExtras) obj;
        return this.xH == plusCommonExtras.xH && fo.equal(this.Uh, plusCommonExtras.Uh) && fo.equal(this.Ui, plusCommonExtras.Ui);
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Integer.valueOf(this.xH), this.Uh, this.Ui);
    }

    public String iN() {
        return this.Uh;
    }

    public String iO() {
        return this.Ui;
    }

    public void m(Bundle bundle) {
        bundle.putByteArray("android.gms.plus.internal.PlusCommonExtras.extraPlusCommon", com.google.android.gms.common.internal.safeparcel.c.a(this));
    }

    public String toString() {
        return fo.e(this).a("versionCode", Integer.valueOf(this.xH)).a("Gpsrc", this.Uh).a("ClientCallingPackage", this.Ui).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        f.a(this, out, flags);
    }
}
