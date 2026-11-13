package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class CountrySpecification implements SafeParcelable {
    public static final Parcelable.Creator<CountrySpecification> CREATOR = new a();
    String qd;
    private final int xH;

    CountrySpecification(int versionCode, String countryCode) {
        this.xH = versionCode;
        this.qd = countryCode;
    }

    public CountrySpecification(String countryCode) {
        this.xH = 1;
        this.qd = countryCode;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getCountryCode() {
        return this.qd;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        a.a(this, dest, flags);
    }
}
