package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;

/* JADX INFO: loaded from: classes.dex */
public class NotFilter implements SafeParcelable, Filter {
    public static final Parcelable.Creator<NotFilter> CREATOR = new i();
    final FilterHolder GT;
    final int xH;

    NotFilter(int versionCode, FilterHolder toNegate) {
        this.xH = versionCode;
        this.GT = toNegate;
    }

    public NotFilter(Filter toNegate) {
        this(1, new FilterHolder(toNegate));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        i.a(this, out, flags);
    }
}
