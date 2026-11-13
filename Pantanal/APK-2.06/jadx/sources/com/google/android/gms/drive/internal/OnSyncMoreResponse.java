package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class OnSyncMoreResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnSyncMoreResponse> CREATOR = new ag();
    final boolean Fg;
    final int xH;

    OnSyncMoreResponse(int versionCode, boolean moreEntriesMayExist) {
        this.xH = versionCode;
        this.Fg = moreEntriesMayExist;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        ag.a(this, dest, flags);
    }
}
