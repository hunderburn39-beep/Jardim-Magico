package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class cd implements Parcelable.Creator<ce> {
    static void a(ce ceVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, ceVar.versionCode);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) ceVar.og, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, ceVar.aO(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, ceVar.aP(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, ceVar.aQ(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, ceVar.aR(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, ceVar.ol, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, ceVar.om);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, ceVar.on, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, ceVar.aT(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 11, ceVar.orientation);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 12, ceVar.op);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 13, ceVar.nO, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, (Parcelable) ceVar.kK, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 15, ceVar.aS(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 16, ceVar.or, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public ce createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        cb cbVar = null;
        IBinder iBinderO = null;
        IBinder iBinderO2 = null;
        IBinder iBinderO3 = null;
        IBinder iBinderO4 = null;
        String strN = null;
        boolean zC = false;
        String strN2 = null;
        IBinder iBinderO5 = null;
        int iG2 = 0;
        int iG3 = 0;
        String strN3 = null;
        dx dxVar = null;
        IBinder iBinderO6 = null;
        String strN4 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    cbVar = (cb) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, cb.CREATOR);
                    break;
                case 3:
                    iBinderO = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iN);
                    break;
                case 4:
                    iBinderO2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iN);
                    break;
                case 5:
                    iBinderO3 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iN);
                    break;
                case 6:
                    iBinderO4 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iN);
                    break;
                case 7:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 8:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 9:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 10:
                    iBinderO5 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iN);
                    break;
                case 11:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 12:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 13:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 14:
                    dxVar = (dx) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, dx.CREATOR);
                    break;
                case 15:
                    iBinderO6 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iN);
                    break;
                case 16:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ce(iG, cbVar, iBinderO, iBinderO2, iBinderO3, iBinderO4, strN, zC, strN2, iBinderO5, iG2, iG3, strN3, dxVar, iBinderO6, strN4);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
    public ce[] newArray(int i) {
        return new ce[i];
    }
}
