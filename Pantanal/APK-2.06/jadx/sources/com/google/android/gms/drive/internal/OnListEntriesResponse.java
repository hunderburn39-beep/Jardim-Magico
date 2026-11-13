package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class OnListEntriesResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnListEntriesResponse> CREATOR = new ad();
    final DataHolder FJ;
    final boolean Fg;
    final int xH;

    OnListEntriesResponse(int versionCode, DataHolder entries, boolean moreEntriesMayExist) {
        this.xH = versionCode;
        this.FJ = entries;
        this.Fg = moreEntriesMayExist;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DataHolder fN() {
        return this.FJ;
    }

    public boolean fO() {
        return this.Fg;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        ad.a(this, dest, flags);
    }
}
