package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class LocationRequestCreator implements Parcelable.Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LocationRequest locationRequest, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, locationRequest.mPriority);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, locationRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, locationRequest.Oc);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, locationRequest.Od);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, locationRequest.Oe);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, locationRequest.NV);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, locationRequest.Of);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, locationRequest.Og);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public LocationRequest createFromParcel(Parcel parcel) {
        boolean zC = false;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 102;
        long jI = 3600000;
        long jI2 = 600000;
        long jI3 = Long.MAX_VALUE;
        int iG2 = Integer.MAX_VALUE;
        float fK = BitmapDescriptorFactory.HUE_RED;
        int iG3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    jI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 3:
                    jI2 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 4:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 5:
                    jI3 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iN);
                    break;
                case 6:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 7:
                    fK = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 1000:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new LocationRequest(iG3, iG, jI, jI2, zC, jI3, iG2, fK);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public LocationRequest[] newArray(int size) {
        return new LocationRequest[size];
    }
}
