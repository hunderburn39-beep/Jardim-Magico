package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class gb implements Parcelable.Creator<ga.a> {
    static void a(ga.a aVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, aVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, aVar.eW());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, aVar.fc());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, aVar.eX());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, aVar.fd());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, aVar.fe(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, aVar.ff());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, aVar.fh(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, (Parcelable) aVar.fj(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: V, reason: merged with bridge method [inline-methods] */
    public ga.a[] newArray(int i) {
        return new ga.a[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: t, reason: merged with bridge method [inline-methods] */
    public ga.a createFromParcel(Parcel parcel) {
        fv fvVar = null;
        int iG = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        String strN = null;
        String strN2 = null;
        boolean zC = false;
        int iG2 = 0;
        boolean zC2 = false;
        int iG3 = 0;
        int iG4 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 3:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 4:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 5:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 6:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 7:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 8:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 9:
                    fvVar = (fv) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, fv.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ga.a(iG4, iG3, zC2, iG2, zC, strN2, iG, strN, fvVar);
    }
}
