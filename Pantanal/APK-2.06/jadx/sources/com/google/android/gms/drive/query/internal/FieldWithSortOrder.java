package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class FieldWithSortOrder implements SafeParcelable {
    public static final c CREATOR = new c();
    final String FM;
    final boolean GJ;
    final int xH;

    FieldWithSortOrder(int versionCode, String fieldName, boolean isSortAscending) {
        this.xH = versionCode;
        this.FM = fieldName;
        this.GJ = isSortAscending;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        c.a(this, out, flags);
    }
}
