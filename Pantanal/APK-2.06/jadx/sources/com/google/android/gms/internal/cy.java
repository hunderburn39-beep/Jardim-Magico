package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class cy implements Parcelable.Creator<cx> {
    static void a(cx cxVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, cxVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, cxVar.pf, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, (Parcelable) cxVar.pg, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) cxVar.kN, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, cxVar.kH, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, (Parcelable) cxVar.applicationInfo, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, (Parcelable) cxVar.ph, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, cxVar.pi, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, cxVar.pj, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, cxVar.pk, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, (Parcelable) cxVar.kK, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, cxVar.pl, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public cx createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        Bundle bundleP = null;
        ah ahVar = null;
        ak akVar = null;
        String strN = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        dx dxVar = null;
        Bundle bundleP2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iN);
                    break;
                case 3:
                    ahVar = (ah) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ah.CREATOR);
                    break;
                case 4:
                    akVar = (ak) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ak.CREATOR);
                    break;
                case 5:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    applicationInfo = (ApplicationInfo) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ApplicationInfo.CREATOR);
                    break;
                case 7:
                    packageInfo = (PackageInfo) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, PackageInfo.CREATOR);
                    break;
                case 8:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 9:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 10:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 11:
                    dxVar = (dx) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, dx.CREATOR);
                    break;
                case 12:
                    bundleP2 = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new cx(iG, bundleP, ahVar, akVar, strN, applicationInfo, packageInfo, strN2, strN3, strN4, dxVar, bundleP2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public cx[] newArray(int i) {
        return new cx[i];
    }
}
