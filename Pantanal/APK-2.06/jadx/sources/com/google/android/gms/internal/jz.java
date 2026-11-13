package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class jz implements Parcelable.Creator<jy> {
    static void a(jy jyVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, jyVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, jyVar.adn, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, jyVar.pm, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) jyVar.adr, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, (Parcelable) jyVar.ads, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, (Parcelable) jyVar.adt, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bx, reason: merged with bridge method [inline-methods] */
    public jy createFromParcel(Parcel parcel) {
        jw jwVar = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        jw jwVar2 = null;
        ju juVar = null;
        String strN = null;
        String strN2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    juVar = (ju) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ju.CREATOR);
                    break;
                case 5:
                    jwVar2 = (jw) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, jw.CREATOR);
                    break;
                case 6:
                    jwVar = (jw) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, jw.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new jy(iG, strN2, strN, juVar, jwVar2, jwVar);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cL, reason: merged with bridge method [inline-methods] */
    public jy[] newArray(int i) {
        return new jy[i];
    }
}
