package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class kj implements Parcelable.Creator<ki> {
    static void a(ki kiVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, kiVar.xH);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, kiVar.fA());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, kiVar.getPath(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, kiVar.getData(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, kiVar.getSource(), false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: by, reason: merged with bridge method [inline-methods] */
    public ki createFromParcel(Parcel parcel) {
        int iG = 0;
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        byte[] bArrQ = null;
        String strN2 = null;
        int iG2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    bArrQ = com.google.android.gms.common.internal.safeparcel.a.q(parcel, iN);
                    break;
                case 5:
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
        return new ki(iG2, iG, strN2, bArrQ, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cN, reason: merged with bridge method [inline-methods] */
    public ki[] newArray(int i) {
        return new ki[i];
    }
}
