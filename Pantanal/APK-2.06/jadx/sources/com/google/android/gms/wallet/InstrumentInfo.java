package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class InstrumentInfo implements SafeParcelable {
    public static final Parcelable.Creator<InstrumentInfo> CREATOR = new h();
    private String abt;
    private String abu;
    private final int xH;

    InstrumentInfo(int versionCode, String instrumentType, String instrumentDetails) {
        this.xH = versionCode;
        this.abt = instrumentType;
        this.abu = instrumentDetails;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getInstrumentDetails() {
        return this.abu;
    }

    public String getInstrumentType() {
        return this.abt;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        h.a(this, out, flags);
    }
}
