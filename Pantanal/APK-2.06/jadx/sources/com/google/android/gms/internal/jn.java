package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class jn implements Parcelable.Creator<jm> {
    static void a(jm jmVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, jmVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, jmVar.add, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, jmVar.ade, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 4, jmVar.adf, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: br, reason: merged with bridge method [inline-methods] */
    public jm createFromParcel(Parcel parcel) {
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        ArrayList arrayListFs = gi.fs();
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
                    arrayListFs = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, jk.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new jm(iG, strN2, strN, arrayListFs);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cF, reason: merged with bridge method [inline-methods] */
    public jm[] newArray(int i) {
        return new jm[i];
    }
}
