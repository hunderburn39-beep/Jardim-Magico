package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class fp implements Parcelable.Creator<fc.a> {
    static void a(fc.a aVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, aVar.getAccountName(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, aVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, aVar.eE(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, aVar.eD());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, aVar.eG(), false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: Q, reason: merged with bridge method [inline-methods] */
    public fc.a[] newArray(int i) {
        return new fc.a[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: m, reason: merged with bridge method [inline-methods] */
    public fc.a createFromParcel(Parcel parcel) {
        int iG = 0;
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        ArrayList<String> arrayListA = null;
        String strN2 = null;
        int iG2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 2:
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iN);
                    break;
                case 3:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 4:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 1000:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new fc.a(iG2, strN2, arrayListA, iG, strN);
    }
}
