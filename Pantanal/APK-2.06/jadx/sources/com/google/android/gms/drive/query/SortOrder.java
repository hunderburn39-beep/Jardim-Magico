package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.internal.FieldWithSortOrder;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SortOrder implements SafeParcelable {
    public static final Parcelable.Creator<SortOrder> CREATOR = new b();
    final List<FieldWithSortOrder> GF;
    final int xH;

    SortOrder(int versionCode, List<FieldWithSortOrder> sortingFields) {
        this.xH = versionCode;
        this.GF = sortingFields;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        b.a(this, out, flags);
    }
}
