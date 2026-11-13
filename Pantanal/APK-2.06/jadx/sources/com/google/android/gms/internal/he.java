package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class he implements Parcelable.Creator<hd> {
    static void a(hd hdVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, hdVar.getRequestId(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, hdVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, hdVar.getExpirationTime());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, hdVar.hS());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, hdVar.getLatitude());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, hdVar.getLongitude());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, hdVar.hT());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, hdVar.hU());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 8, hdVar.getNotificationResponsiveness());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 9, hdVar.hV());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aC, reason: merged with bridge method [inline-methods] */
    public hd createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        int iG2 = 0;
        short sF = 0;
        double dL = 0.0d;
        double dL2 = 0.0d;
        float fK = BitmapDescriptorFactory.HUE_RED;
        long jI = 0;
        int iG3 = 0;
        int iG4 = -1;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 2:
                    jI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 3:
                    sF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iN);
                    break;
                case 4:
                    dL = com.google.android.gms.common.internal.safeparcel.a.l(parcel, iN);
                    break;
                case 5:
                    dL2 = com.google.android.gms.common.internal.safeparcel.a.l(parcel, iN);
                    break;
                case 6:
                    fK = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 7:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 8:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 9:
                    iG4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
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
        return new hd(iG, strN, iG2, sF, dL, dL2, fK, jI, iG3, iG4);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bD, reason: merged with bridge method [inline-methods] */
    public hd[] newArray(int i) {
        return new hd[i];
    }
}
