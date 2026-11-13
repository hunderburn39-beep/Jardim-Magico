package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class aw implements Parcelable.Creator<av> {
    static void a(av avVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, avVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, avVar.mq);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, avVar.backgroundColor);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, avVar.mr);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 5, avVar.ms);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, avVar.mt);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, avVar.mu);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 8, avVar.mv);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 9, avVar.mw);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, avVar.mx, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 11, avVar.my);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, avVar.mz, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 13, avVar.mA);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 14, avVar.mB);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 15, avVar.mC, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public av createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        int iG2 = 0;
        int iG3 = 0;
        int iG4 = 0;
        int iG5 = 0;
        int iG6 = 0;
        int iG7 = 0;
        int iG8 = 0;
        int iG9 = 0;
        String strN = null;
        int iG10 = 0;
        String strN2 = null;
        int iG11 = 0;
        int iG12 = 0;
        String strN3 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 3:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 4:
                    iG4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 5:
                    iG5 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 6:
                    iG6 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 7:
                    iG7 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 8:
                    iG8 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 9:
                    iG9 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 10:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 11:
                    iG10 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 12:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 13:
                    iG11 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 14:
                    iG12 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 15:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new av(iG, iG2, iG3, iG4, iG5, iG6, iG7, iG8, iG9, strN, iG10, strN2, iG11, iG12, strN3);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public av[] newArray(int i) {
        return new av[i];
    }
}
