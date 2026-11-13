package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class g implements Parcelable.Creator<LogicalFilter> {
    static void a(LogicalFilter logicalFilter, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, logicalFilter.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, (Parcelable) logicalFilter.GG, i, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 2, logicalFilter.GS, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aN, reason: merged with bridge method [inline-methods] */
    public LogicalFilter[] newArray(int i) {
        return new LogicalFilter[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aj, reason: merged with bridge method [inline-methods] */
    public LogicalFilter createFromParcel(Parcel parcel) {
        ArrayList arrayListC;
        Operator operator;
        int iG;
        ArrayList arrayList = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int i = 0;
        Operator operator2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    Operator operator3 = (Operator) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Operator.CREATOR);
                    iG = i;
                    arrayListC = arrayList;
                    operator = operator3;
                    break;
                case 2:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, FilterHolder.CREATOR);
                    operator = operator2;
                    iG = i;
                    break;
                case 1000:
                    ArrayList arrayList2 = arrayList;
                    operator = operator2;
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    arrayListC = arrayList2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    arrayListC = arrayList;
                    operator = operator2;
                    iG = i;
                    break;
            }
            i = iG;
            operator2 = operator;
            arrayList = arrayListC;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new LogicalFilter(i, operator2, arrayList);
    }
}
