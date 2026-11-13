package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class hp implements Parcelable.Creator<ho> {
    static void a(ho hoVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, hoVar.getName(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, hoVar.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) hoVar.ia(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, hoVar.getAddress(), false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 4, hoVar.ib(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, hoVar.getPhoneNumber(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, hoVar.ic(), false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aH, reason: merged with bridge method [inline-methods] */
    public ho createFromParcel(Parcel parcel) {
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN2 = null;
        ArrayList arrayListC = null;
        String strN3 = null;
        LatLng latLng = null;
        String strN4 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 2:
                    latLng = (LatLng) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, LatLng.CREATOR);
                    break;
                case 3:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, hm.CREATOR);
                    break;
                case 5:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
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
        return new ho(iG, strN4, latLng, strN3, arrayListC, strN2, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bI, reason: merged with bridge method [inline-methods] */
    public ho[] newArray(int i) {
        return new ho[i];
    }
}
