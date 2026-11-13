package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class c implements Parcelable.Creator<b> {
    static void a(b bVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, bVar.Oh);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, bVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, bVar.Oi);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, bVar.Oj);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aB, reason: merged with bridge method [inline-methods] */
    public b createFromParcel(Parcel parcel) {
        int iG = 1;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG2 = 0;
        long jI = 0;
        int iG3 = 1;
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
                    jI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 1000:
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
        return new b(iG2, iG3, iG, jI);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bA, reason: merged with bridge method [inline-methods] */
    public b[] newArray(int i) {
        return new b[i];
    }
}
