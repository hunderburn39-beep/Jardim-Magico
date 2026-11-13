package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class j implements Parcelable.Creator<h> {
    static void a(h hVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, hVar.getAccountName(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, hVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, hVar.iP(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, hVar.iQ(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, hVar.iR(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, hVar.iS(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, hVar.iT(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, hVar.iU(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, hVar.iV(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, (Parcelable) hVar.iW(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aK, reason: merged with bridge method [inline-methods] */
    public h createFromParcel(Parcel parcel) {
        PlusCommonExtras plusCommonExtras = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String[] strArrZ = null;
        String[] strArrZ2 = null;
        String[] strArrZ3 = null;
        String strN5 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 2:
                    strArrZ3 = com.google.android.gms.common.internal.safeparcel.a.z(parcel, iN);
                    break;
                case 3:
                    strArrZ2 = com.google.android.gms.common.internal.safeparcel.a.z(parcel, iN);
                    break;
                case 4:
                    strArrZ = com.google.android.gms.common.internal.safeparcel.a.z(parcel, iN);
                    break;
                case 5:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 7:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 8:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 9:
                    plusCommonExtras = (PlusCommonExtras) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, PlusCommonExtras.CREATOR);
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
        return new h(iG, strN5, strArrZ3, strArrZ2, strArrZ, strN4, strN3, strN2, strN, plusCommonExtras);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bN, reason: merged with bridge method [inline-methods] */
    public h[] newArray(int i) {
        return new h[i];
    }
}
