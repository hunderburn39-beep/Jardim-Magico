package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.query.internal.LogicalFilter;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<Query> {
    static void a(Query query, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, query.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, (Parcelable) query.GA, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, query.GB, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) query.GC, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aG, reason: merged with bridge method [inline-methods] */
    public Query[] newArray(int i) {
        return new Query[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ac, reason: merged with bridge method [inline-methods] */
    public Query createFromParcel(Parcel parcel) {
        SortOrder sortOrder;
        String strN;
        LogicalFilter logicalFilter;
        int iG;
        SortOrder sortOrder2 = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int i = 0;
        String str = null;
        LogicalFilter logicalFilter2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = i;
                    String str2 = str;
                    logicalFilter = (LogicalFilter) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, LogicalFilter.CREATOR);
                    sortOrder = sortOrder2;
                    strN = str2;
                    break;
                case 3:
                    logicalFilter = logicalFilter2;
                    iG = i;
                    SortOrder sortOrder3 = sortOrder2;
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    sortOrder = sortOrder3;
                    break;
                case 4:
                    sortOrder = (SortOrder) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, SortOrder.CREATOR);
                    strN = str;
                    logicalFilter = logicalFilter2;
                    iG = i;
                    break;
                case 1000:
                    SortOrder sortOrder4 = sortOrder2;
                    strN = str;
                    logicalFilter = logicalFilter2;
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    sortOrder = sortOrder4;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    sortOrder = sortOrder2;
                    strN = str;
                    logicalFilter = logicalFilter2;
                    iG = i;
                    break;
            }
            i = iG;
            logicalFilter2 = logicalFilter;
            str = strN;
            sortOrder2 = sortOrder;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new Query(i, logicalFilter2, str, sortOrder2);
    }
}
