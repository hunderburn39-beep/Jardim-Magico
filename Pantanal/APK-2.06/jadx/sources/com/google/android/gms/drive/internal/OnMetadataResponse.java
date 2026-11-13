package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: loaded from: classes.dex */
public class OnMetadataResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnMetadataResponse> CREATOR = new af();
    final MetadataBundle EZ;
    final int xH;

    OnMetadataResponse(int versionCode, MetadataBundle metadata) {
        this.xH = versionCode;
        this.EZ = metadata;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MetadataBundle fQ() {
        return this.EZ;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        af.a(this, dest, flags);
    }
}
