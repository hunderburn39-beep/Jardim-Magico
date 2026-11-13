package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class dy implements Parcelable.Creator<dx> {
    static void a(dx dxVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, dxVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, dxVar.rq, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, dxVar.rr);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, dxVar.rs);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, dxVar.rt);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public dx createFromParcel(Parcel parcel) {
        boolean zC = false;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        String strN = null;
        int iG = 0;
        int iG2 = 0;
        int iG3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 4:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 5:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new dx(iG3, strN, iG2, iG, zC);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: o, reason: merged with bridge method [inline-methods] */
    public dx[] newArray(int i) {
        return new dx[i];
    }
}
