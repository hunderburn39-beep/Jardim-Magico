package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class i implements Parcelable.Creator<NotFilter> {
    static void a(NotFilter notFilter, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, notFilter.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, (Parcelable) notFilter.GT, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aP, reason: merged with bridge method [inline-methods] */
    public NotFilter[] newArray(int i) {
        return new NotFilter[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: al, reason: merged with bridge method [inline-methods] */
    public NotFilter createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        FilterHolder filterHolder = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    filterHolder = (FilterHolder) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, FilterHolder.CREATOR);
                    break;
                case 1000:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new NotFilter(iG, filterHolder);
    }
}
