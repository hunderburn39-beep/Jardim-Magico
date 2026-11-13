package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class b implements Parcelable.Creator<UserAddress> {
    static void a(UserAddress userAddress, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, userAddress.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, userAddress.name, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, userAddress.NB, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, userAddress.NC, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, userAddress.ND, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, userAddress.NE, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, userAddress.NF, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, userAddress.NG, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, userAddress.NH, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, userAddress.qd, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, userAddress.NI, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, userAddress.NJ, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 13, userAddress.NK, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, userAddress.NL);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 15, userAddress.NM, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 16, userAddress.NN, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aA, reason: merged with bridge method [inline-methods] */
    public UserAddress createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        String strN7 = null;
        String strN8 = null;
        String strN9 = null;
        String strN10 = null;
        String strN11 = null;
        String strN12 = null;
        boolean zC = false;
        String strN13 = null;
        String strN14 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 5:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 7:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 8:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 9:
                    strN8 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 10:
                    strN9 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 11:
                    strN10 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 12:
                    strN11 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 13:
                    strN12 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 14:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 15:
                    strN13 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 16:
                    strN14 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new UserAddress(iG, strN, strN2, strN3, strN4, strN5, strN6, strN7, strN8, strN9, strN10, strN11, strN12, zC, strN13, strN14);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bu, reason: merged with bridge method [inline-methods] */
    public UserAddress[] newArray(int i) {
        return new UserAddress[i];
    }
}
