package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

/* JADX INFO: loaded from: classes.dex */
public class StreetViewPanoramaOptionsCreator implements Parcelable.Creator<StreetViewPanoramaOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(StreetViewPanoramaOptions streetViewPanoramaOptions, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.c(parcel, 1, streetViewPanoramaOptions.getVersionCode());
        b.a(parcel, 2, (Parcelable) streetViewPanoramaOptions.getStreetViewPanoramaCamera(), i, false);
        b.a(parcel, 3, streetViewPanoramaOptions.getPanoramaId(), false);
        b.a(parcel, 4, (Parcelable) streetViewPanoramaOptions.getPosition(), i, false);
        b.a(parcel, 5, streetViewPanoramaOptions.getRadius(), false);
        b.a(parcel, 6, streetViewPanoramaOptions.it());
        b.a(parcel, 7, streetViewPanoramaOptions.il());
        b.a(parcel, 8, streetViewPanoramaOptions.iu());
        b.a(parcel, 9, streetViewPanoramaOptions.iv());
        b.a(parcel, 10, streetViewPanoramaOptions.ih());
        b.F(parcel, iP);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public StreetViewPanoramaOptions createFromParcel(Parcel parcel) {
        Integer numH = null;
        byte bE = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        byte bE2 = 0;
        byte bE3 = 0;
        byte bE4 = 0;
        byte bE5 = 0;
        LatLng latLng = null;
        String strN = null;
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        int iG = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    streetViewPanoramaCamera = (StreetViewPanoramaCamera) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, StreetViewPanoramaCamera.CREATOR);
                    break;
                case 3:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    latLng = (LatLng) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, LatLng.CREATOR);
                    break;
                case 5:
                    numH = com.google.android.gms.common.internal.safeparcel.a.h(parcel, iN);
                    break;
                case 6:
                    bE5 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 7:
                    bE4 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 8:
                    bE3 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 9:
                    bE2 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 10:
                    bE = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new StreetViewPanoramaOptions(iG, streetViewPanoramaCamera, strN, latLng, numH, bE5, bE4, bE3, bE2, bE);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public StreetViewPanoramaOptions[] newArray(int size) {
        return new StreetViewPanoramaOptions[size];
    }
}
