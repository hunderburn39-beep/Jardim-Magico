package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class ProxyCard implements SafeParcelable {
    public static final Parcelable.Creator<ProxyCard> CREATOR = new o();
    String ack;
    String acl;
    int acm;
    int acn;
    private final int xH;

    ProxyCard(int versionCode, String pan, String cvn, int expirationMonth, int expirationYear) {
        this.xH = versionCode;
        this.ack = pan;
        this.acl = cvn;
        this.acm = expirationMonth;
        this.acn = expirationYear;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getCvn() {
        return this.acl;
    }

    public int getExpirationMonth() {
        return this.acm;
    }

    public int getExpirationYear() {
        return this.acn;
    }

    public String getPan() {
        return this.ack;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        o.a(this, out, flags);
    }
}
