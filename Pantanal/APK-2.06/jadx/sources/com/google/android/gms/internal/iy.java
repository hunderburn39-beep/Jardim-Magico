package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class iy implements Parcelable.Creator<ix> {
    static void a(ix ixVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, ixVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, ixVar.act, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, ixVar.acu, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bm, reason: merged with bridge method [inline-methods] */
    public ix createFromParcel(Parcel parcel) {
        String[] strArrZ = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        byte[][] bArrR = (byte[][]) null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strArrZ = com.google.android.gms.common.internal.safeparcel.a.z(parcel, iN);
                    break;
                case 3:
                    bArrR = com.google.android.gms.common.internal.safeparcel.a.r(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ix(iG, strArrZ, bArrR);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cy, reason: merged with bridge method [inline-methods] */
    public ix[] newArray(int i) {
        return new ix[i];
    }
}
