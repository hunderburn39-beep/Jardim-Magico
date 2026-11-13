package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class fy implements Parcelable.Creator<fx> {
    static void a(fx fxVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, fxVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 2, fxVar.eV(), false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: T, reason: merged with bridge method [inline-methods] */
    public fx[] newArray(int i) {
        return new fx[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: r, reason: merged with bridge method [inline-methods] */
    public fx createFromParcel(Parcel parcel) {
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
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, fx.a.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new fx(iG, arrayListC);
    }
}
