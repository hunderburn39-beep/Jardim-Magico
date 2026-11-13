package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.CameraPosition;

/* JADX INFO: loaded from: classes.dex */
public class GoogleMapOptionsCreator implements Parcelable.Creator<GoogleMapOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.c(parcel, 1, googleMapOptions.getVersionCode());
        b.a(parcel, 2, googleMapOptions.ig());
        b.a(parcel, 3, googleMapOptions.ih());
        b.c(parcel, 4, googleMapOptions.getMapType());
        b.a(parcel, 5, (Parcelable) googleMapOptions.getCamera(), i, false);
        b.a(parcel, 6, googleMapOptions.ii());
        b.a(parcel, 7, googleMapOptions.ij());
        b.a(parcel, 8, googleMapOptions.ik());
        b.a(parcel, 9, googleMapOptions.il());
        b.a(parcel, 10, googleMapOptions.im());
        b.a(parcel, 11, googleMapOptions.in());
        b.F(parcel, iP);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public GoogleMapOptions createFromParcel(Parcel parcel) {
        byte bE = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        CameraPosition cameraPosition = null;
        byte bE2 = 0;
        byte bE3 = 0;
        byte bE4 = 0;
        byte bE5 = 0;
        byte bE6 = 0;
        int iG = 0;
        byte bE7 = 0;
        byte bE8 = 0;
        int iG2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    bE8 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 3:
                    bE7 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 4:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 5:
                    cameraPosition = (CameraPosition) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, CameraPosition.CREATOR);
                    break;
                case 6:
                    bE6 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 7:
                    bE5 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 8:
                    bE4 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 9:
                    bE3 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 10:
                    bE2 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iN);
                    break;
                case 11:
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
        return new GoogleMapOptions(iG2, bE8, bE7, iG, cameraPosition, bE6, bE5, bE4, bE3, bE2, bE);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public GoogleMapOptions[] newArray(int size) {
        return new GoogleMapOptions[size];
    }
}
