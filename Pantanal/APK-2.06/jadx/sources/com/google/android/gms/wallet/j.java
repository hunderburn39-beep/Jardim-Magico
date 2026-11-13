package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.jm;
import com.google.android.gms.internal.jo;
import com.google.android.gms.internal.js;
import com.google.android.gms.internal.ju;
import com.google.android.gms.internal.jw;
import com.google.android.gms.internal.jy;
import com.google.android.gms.maps.model.LatLng;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class j implements Parcelable.Creator<LoyaltyWalletObject> {
    static void a(LoyaltyWalletObject loyaltyWalletObject, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, loyaltyWalletObject.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, loyaltyWalletObject.eC, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, loyaltyWalletObject.abz, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, loyaltyWalletObject.abA, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, loyaltyWalletObject.abB, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, loyaltyWalletObject.abC, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, loyaltyWalletObject.abD, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, loyaltyWalletObject.abE, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, loyaltyWalletObject.abF, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, loyaltyWalletObject.abG, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, loyaltyWalletObject.abH, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 12, loyaltyWalletObject.state);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 13, loyaltyWalletObject.abI, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, (Parcelable) loyaltyWalletObject.abJ, i, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 15, loyaltyWalletObject.abK, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 17, loyaltyWalletObject.abM, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 16, loyaltyWalletObject.abL, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 19, loyaltyWalletObject.abO);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 18, loyaltyWalletObject.abN, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 21, loyaltyWalletObject.abQ, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 20, loyaltyWalletObject.abP, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 23, (Parcelable) loyaltyWalletObject.abS, i, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 22, loyaltyWalletObject.abR, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bf, reason: merged with bridge method [inline-methods] */
    public LoyaltyWalletObject createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        String strN7 = null;
        String strN8 = null;
        String strN9 = null;
        String strN10 = null;
        int iG2 = 0;
        ArrayList arrayListFs = gi.fs();
        ju juVar = null;
        ArrayList arrayListFs2 = gi.fs();
        String strN11 = null;
        String strN12 = null;
        ArrayList arrayListFs3 = gi.fs();
        boolean zC = false;
        ArrayList arrayListFs4 = gi.fs();
        ArrayList arrayListFs5 = gi.fs();
        ArrayList arrayListFs6 = gi.fs();
        jo joVar = null;
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
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 5:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 7:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 8:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 9:
                    strN8 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 10:
                    strN9 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 11:
                    strN10 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 12:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 13:
                    arrayListFs = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, jy.CREATOR);
                    break;
                case 14:
                    juVar = (ju) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ju.CREATOR);
                    break;
                case 15:
                    arrayListFs2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, LatLng.CREATOR);
                    break;
                case 16:
                    strN11 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 17:
                    strN12 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 18:
                    arrayListFs3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, jm.CREATOR);
                    break;
                case 19:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                    arrayListFs4 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, jw.CREATOR);
                    break;
                case 21:
                    arrayListFs5 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, js.CREATOR);
                    break;
                case 22:
                    arrayListFs6 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, jw.CREATOR);
                    break;
                case 23:
                    joVar = (jo) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, jo.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new LoyaltyWalletObject(iG, strN, strN2, strN3, strN4, strN5, strN6, strN7, strN8, strN9, strN10, iG2, arrayListFs, juVar, arrayListFs2, strN11, strN12, arrayListFs3, zC, arrayListFs4, arrayListFs5, arrayListFs6, joVar);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cr, reason: merged with bridge method [inline-methods] */
    public LoyaltyWalletObject[] newArray(int i) {
        return new LoyaltyWalletObject[i];
    }
}
