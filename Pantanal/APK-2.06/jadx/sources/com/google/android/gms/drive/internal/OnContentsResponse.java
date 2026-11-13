package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.Contents;

/* JADX INFO: loaded from: classes.dex */
public class OnContentsResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnContentsResponse> CREATOR = new z();
    final Contents EA;
    final int xH;

    OnContentsResponse(int versionCode, Contents contents) {
        this.xH = versionCode;
        this.EA = contents;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Contents fI() {
        return this.EA;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        z.a(this, dest, flags);
    }
}
