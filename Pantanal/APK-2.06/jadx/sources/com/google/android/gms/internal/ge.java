package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ge implements Parcelable.Creator<gd> {
    static void a(gd gdVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, gdVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 2, gdVar.fn(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, gdVar.fo(), false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: X, reason: merged with bridge method [inline-methods] */
    public gd[] newArray(int i) {
        return new gd[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: v, reason: merged with bridge method [inline-methods] */
    public gd createFromParcel(Parcel parcel) {
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        ArrayList arrayListC = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, gd.a.CREATOR);
                    break;
                case 3:
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
        return new gd(iG, arrayListC, strN);
    }
}
