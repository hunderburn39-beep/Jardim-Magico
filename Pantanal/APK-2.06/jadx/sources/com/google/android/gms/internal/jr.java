package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class jr implements Parcelable.Creator<jo> {
    static void a(jo joVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, joVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, joVar.label, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, (Parcelable) joVar.adg, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, joVar.type, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, (Parcelable) joVar.abJ, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bt, reason: merged with bridge method [inline-methods] */
    public jo createFromParcel(Parcel parcel) {
        ju juVar = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        jp jpVar = null;
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
                    jpVar = (jp) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, jp.CREATOR);
                    break;
                case 4:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 5:
                    juVar = (ju) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ju.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new jo(iG, strN2, jpVar, strN, juVar);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cH, reason: merged with bridge method [inline-methods] */
    public jo[] newArray(int i) {
        return new jo[i];
    }
}
