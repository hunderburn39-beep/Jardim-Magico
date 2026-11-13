package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class da implements Parcelable.Creator<cz> {
    static void a(cz czVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, czVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, czVar.ol, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, czVar.pm, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, czVar.ne, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 5, czVar.errorCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, czVar.nf, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, czVar.pn);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, czVar.po);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, czVar.pp);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, czVar.pq, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, czVar.ni);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 12, czVar.orientation);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 13, czVar.pr, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, czVar.ps);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 15, czVar.pt, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 19, czVar.pv, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 18, czVar.pu);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 21, czVar.pw, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public cz createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        ArrayList<String> arrayListA = null;
        int iG2 = 0;
        ArrayList<String> arrayListA2 = null;
        long jI = 0;
        boolean zC = false;
        long jI2 = 0;
        ArrayList<String> arrayListA3 = null;
        long jI3 = 0;
        int iG3 = 0;
        String strN3 = null;
        long jI4 = 0;
        String strN4 = null;
        boolean zC2 = false;
        String strN5 = null;
        String strN6 = null;
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
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iN);
                    break;
                case 5:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 6:
                    arrayListA2 = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iN);
                    break;
                case 7:
                    jI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 8:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 9:
                    jI2 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 10:
                    arrayListA3 = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iN);
                    break;
                case 11:
                    jI3 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 12:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 13:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 14:
                    jI4 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 15:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 16:
                case 17:
                case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
                case 18:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 19:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 21:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new cz(iG, strN, strN2, arrayListA, iG2, arrayListA2, jI, zC, jI2, arrayListA3, jI3, iG3, strN3, jI4, strN4, zC2, strN5, strN6);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public cz[] newArray(int i) {
        return new cz[i];
    }
}
