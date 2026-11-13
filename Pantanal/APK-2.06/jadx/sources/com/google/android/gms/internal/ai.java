package com.google.android.gms.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ai implements Parcelable.Creator<ah> {
    static void a(ah ahVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, ahVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, ahVar.lH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, ahVar.extras, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, ahVar.lI);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, ahVar.lJ, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, ahVar.lK);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, ahVar.lL);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, ahVar.lM);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, ahVar.lN, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, (Parcelable) ahVar.lO, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, (Parcelable) ahVar.lP, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, ahVar.lQ, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public ah createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        long jI = 0;
        Bundle bundleP = null;
        int iG2 = 0;
        ArrayList<String> arrayListA = null;
        boolean zC = false;
        int iG3 = 0;
        boolean zC2 = false;
        String strN = null;
        av avVar = null;
        Location location = null;
        String strN2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    jI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 3:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iN);
                    break;
                case 4:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 5:
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iN);
                    break;
                case 6:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 7:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 8:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 9:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 10:
                    avVar = (av) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, av.CREATOR);
                    break;
                case 11:
                    location = (Location) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Location.CREATOR);
                    break;
                case 12:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ah(iG, jI, bundleP, iG2, arrayListA, zC, iG3, zC2, strN, avVar, location, strN2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public ah[] newArray(int i) {
        return new ah[i];
    }
}
