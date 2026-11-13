package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class hl implements Parcelable.Creator<hk> {
    static void a(hk hkVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, hkVar.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) hkVar.hZ(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, hkVar.getInterval());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, hkVar.getPriority());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aF, reason: merged with bridge method [inline-methods] */
    public hk createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        hg hgVar = null;
        long jI = hk.OF;
        int iG2 = 102;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 2:
                    hgVar = (hg) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, hg.CREATOR);
                    break;
                case 3:
                    jI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 4:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
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
        return new hk(iG, hgVar, jI, iG2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bG, reason: merged with bridge method [inline-methods] */
    public hk[] newArray(int i) {
        return new hk[i];
    }
}
