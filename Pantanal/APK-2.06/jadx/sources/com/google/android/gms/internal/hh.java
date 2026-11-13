package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class hh implements Parcelable.Creator<hg> {
    static void a(hg hgVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 1, hgVar.OA, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, hgVar.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, hgVar.hW(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, hgVar.hX());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aD, reason: merged with bridge method [inline-methods] */
    public hg createFromParcel(Parcel parcel) {
        String strN = null;
        boolean zC = false;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        ArrayList arrayListC = null;
        int iG = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, hm.CREATOR);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
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
        return new hg(iG, arrayListC, strN, zC);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bE, reason: merged with bridge method [inline-methods] */
    public hg[] newArray(int i) {
        return new hg[i];
    }
}
