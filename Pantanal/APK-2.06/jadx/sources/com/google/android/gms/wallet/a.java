package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<Address> {
    static void a(Address address, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, address.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, address.name, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, address.NB, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, address.NC, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, address.ND, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, address.qd, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, address.aba, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, address.abb, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, address.NI, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, address.NK, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, address.NL);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, address.NM, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aX, reason: merged with bridge method [inline-methods] */
    public Address createFromParcel(Parcel parcel) {
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
        boolean zC = false;
        String strN10 = null;
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
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 12:
                    strN10 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new Address(iG, strN, strN2, strN3, strN4, strN5, strN6, strN7, strN8, strN9, zC, strN10);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cj, reason: merged with bridge method [inline-methods] */
    public Address[] newArray(int i) {
        return new Address[i];
    }
}
