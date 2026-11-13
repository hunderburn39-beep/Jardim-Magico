package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ht implements Parcelable.Creator<hs> {
    static void a(hs hsVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, hsVar.Rl, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, hsVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, hsVar.Rm, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aI, reason: merged with bridge method [inline-methods] */
    public hs createFromParcel(Parcel parcel) {
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 1000:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new hs(iG, strN2, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bJ, reason: merged with bridge method [inline-methods] */
    public hs[] newArray(int i) {
        return new hs[i];
    }
}
