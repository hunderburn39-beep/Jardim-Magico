package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class jq implements Parcelable.Creator<jp> {
    static void a(jp jpVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, jpVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, jpVar.adh);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, jpVar.adi, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, jpVar.adj);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, jpVar.adk, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, jpVar.adl);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, jpVar.adm);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bs, reason: merged with bridge method [inline-methods] */
    public jp createFromParcel(Parcel parcel) {
        String strN = null;
        int iG = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        double dL = 0.0d;
        long jI = 0;
        int iG2 = -1;
        String strN2 = null;
        int iG3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    dL = com.google.android.gms.common.internal.safeparcel.a.l(parcel, iN);
                    break;
                case 5:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    jI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 7:
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
        return new jp(iG3, iG, strN2, dL, strN, jI, iG2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cG, reason: merged with bridge method [inline-methods] */
    public jp[] newArray(int i) {
        return new jp[i];
    }
}
