package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class OnListParentsResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnListParentsResponse> CREATOR = new ae();
    final DataHolder FK;
    final int xH;

    OnListParentsResponse(int versionCode, DataHolder parents) {
        this.xH = versionCode;
        this.FK = parents;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DataHolder fP() {
        return this.FK;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        ae.a(this, dest, flags);
    }
}
