package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class OnDownloadProgressResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnDownloadProgressResponse> CREATOR = new aa();
    final long FF;
    final long FG;
    final int xH;

    OnDownloadProgressResponse(int versionCode, long bytesLoaded, long bytesExpected) {
        this.xH = versionCode;
        this.FF = bytesLoaded;
        this.FG = bytesExpected;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long fJ() {
        return this.FF;
    }

    public long fK() {
        return this.FG;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        aa.a(this, dest, flags);
    }
}
