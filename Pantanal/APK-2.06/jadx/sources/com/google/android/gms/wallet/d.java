package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class d implements SafeParcelable {
    public static final Parcelable.Creator<d> CREATOR = new e();
    LoyaltyWalletObject abg;
    private final int xH;

    d() {
        this.xH = 1;
    }

    d(int i, LoyaltyWalletObject loyaltyWalletObject) {
        this.xH = i;
        this.abg = loyaltyWalletObject;
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
        e.a(this, dest, flags);
    }
}
