package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class OfferWalletObject implements SafeParcelable {
    public static final Parcelable.Creator<OfferWalletObject> CREATOR = new n();
    String acj;
    String eC;
    private final int xH;

    OfferWalletObject() {
        this.xH = 2;
    }

    OfferWalletObject(int versionCode, String id, String redemptionCode) {
        this.xH = versionCode;
        this.eC = id;
        this.acj = redemptionCode;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.eC;
    }

    public String getRedemptionCode() {
        return this.acj;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        n.a(this, dest, flags);
    }
}
