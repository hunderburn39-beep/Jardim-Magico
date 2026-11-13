package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ef implements Parcelable {

    @Deprecated
    public static final Parcelable.Creator<ef> CREATOR = new Parcelable.Creator<ef>() { // from class: com.google.android.gms.internal.ef.1
        @Override // android.os.Parcelable.Creator
        @Deprecated
        /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
        public ef createFromParcel(Parcel parcel) {
            return new ef(parcel);
        }

        @Override // android.os.Parcelable.Creator
        @Deprecated
        /* JADX INFO: renamed from: u, reason: merged with bridge method [inline-methods] */
        public ef[] newArray(int i) {
            return new ef[i];
        }
    };
    private String mValue;
    private String wp;
    private String wq;

    @Deprecated
    public ef() {
    }

    @Deprecated
    ef(Parcel parcel) {
        readFromParcel(parcel);
    }

    public ef(String str, String str2, String str3) {
        this.wp = str;
        this.wq = str2;
        this.mValue = str3;
    }

    @Deprecated
    private void readFromParcel(Parcel in) {
        this.wp = in.readString();
        this.wq = in.readString();
        this.mValue = in.readString();
    }

    @Override // android.os.Parcelable
    @Deprecated
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.wp;
    }

    public String getValue() {
        return this.mValue;
    }

    @Override // android.os.Parcelable
    @Deprecated
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.wp);
        out.writeString(this.wq);
        out.writeString(this.mValue);
    }
}
