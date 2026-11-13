package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ca implements Parcelable.Creator<cb> {
    static void a(cb cbVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, cbVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, cbVar.nN, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, cbVar.nO, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, cbVar.mimeType, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, cbVar.packageName, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, cbVar.nP, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, cbVar.nQ, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, cbVar.nR, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public cb createFromParcel(Parcel parcel) {
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        String strN7 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
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
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new cb(iG, strN7, strN6, strN5, strN4, strN3, strN2, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public cb[] newArray(int i) {
        return new cb[i];
    }
}
