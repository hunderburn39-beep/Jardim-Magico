package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class MarkerOptionsCreator implements Parcelable.Creator<MarkerOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(MarkerOptions markerOptions, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, markerOptions.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) markerOptions.getPosition(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, markerOptions.getTitle(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, markerOptions.getSnippet(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, markerOptions.iE(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, markerOptions.getAnchorU());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, markerOptions.getAnchorV());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, markerOptions.isDraggable());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, markerOptions.isVisible());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, markerOptions.isFlat());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, markerOptions.getRotation());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, markerOptions.getInfoWindowAnchorU());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 13, markerOptions.getInfoWindowAnchorV());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, markerOptions.getAlpha());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public MarkerOptions createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        LatLng latLng = null;
        String strN = null;
        String strN2 = null;
        IBinder iBinderO = null;
        float fK = BitmapDescriptorFactory.HUE_RED;
        float fK2 = BitmapDescriptorFactory.HUE_RED;
        boolean zC = false;
        boolean zC2 = false;
        boolean zC3 = false;
        float fK3 = BitmapDescriptorFactory.HUE_RED;
        float fK4 = 0.5f;
        float fK5 = BitmapDescriptorFactory.HUE_RED;
        float fK6 = 1.0f;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    latLng = (LatLng) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, LatLng.CREATOR);
                    break;
                case 3:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 5:
                    iBinderO = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iN);
                    break;
                case 6:
                    fK = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 7:
                    fK2 = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 8:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 9:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 10:
                    zC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 11:
                    fK3 = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 12:
                    fK4 = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 13:
                    fK5 = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                case 14:
                    fK6 = com.google.android.gms.common.internal.safeparcel.a.k(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new MarkerOptions(iG, latLng, strN, strN2, iBinderO, fK, fK2, zC, zC2, zC3, fK3, fK4, fK5, fK6);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public MarkerOptions[] newArray(int size) {
        return new MarkerOptions[size];
    }
}
