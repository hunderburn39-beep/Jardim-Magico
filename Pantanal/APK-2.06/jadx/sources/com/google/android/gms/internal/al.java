package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class al implements Parcelable.Creator<ak> {
    static void a(ak akVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, akVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, akVar.lS, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, akVar.height);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, akVar.heightPixels);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, akVar.lT);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, akVar.width);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, akVar.widthPixels);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, (Parcelable[]) akVar.lU, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public ak createFromParcel(Parcel parcel) {
        ak[] akVarArr = null;
        int iG = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG2 = 0;
        boolean zC = false;
        int iG3 = 0;
        int iG4 = 0;
        String strN = null;
        int iG5 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG5 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    iG4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 4:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 5:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 6:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 7:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 8:
                    akVarArr = (ak[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN, ak.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ak(iG5, strN, iG4, iG3, zC, iG2, iG, akVarArr);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public ak[] newArray(int i) {
        return new ak[i];
    }
}
