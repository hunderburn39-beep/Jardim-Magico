package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class StreetViewPanoramaCameraCreator implements Parcelable.Creator<StreetViewPanoramaCamera> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(StreetViewPanoramaCamera streetViewPanoramaCamera, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, streetViewPanoramaCamera.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, streetViewPanoramaCamera.zoom);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, streetViewPanoramaCamera.tilt);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, streetViewPanoramaCamera.bearing);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public StreetViewPanoramaCamera createFromParcel(Parcel parcel) {
        float fK = BitmapDescriptorFactory.HUE_RED;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        float fK2 = 0.0f;
        int iG = 0;
        float fK3 = 0.0f;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    fK2 = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 3:
                    fK3 = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 4:
                    fK = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new StreetViewPanoramaCamera(iG, fK2, fK3, fK);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public StreetViewPanoramaCamera[] newArray(int size) {
        return new StreetViewPanoramaCamera[size];
    }
}
