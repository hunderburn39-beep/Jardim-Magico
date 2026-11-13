package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class b implements Parcelable.Creator<WebImage> {
    static void a(WebImage webImage, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, webImage.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) webImage.getUrl(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, webImage.getWidth());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, webImage.getHeight());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: K, reason: merged with bridge method [inline-methods] */
    public WebImage[] newArray(int i) {
        return new WebImage[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public WebImage createFromParcel(Parcel parcel) {
        int iG;
        int iG2;
        Uri uri;
        int iG3;
        int i = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        Uri uri2 = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    int i4 = i;
                    iG2 = i2;
                    uri = uri2;
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    iG = i4;
                    break;
                case 2:
                    iG3 = i3;
                    int i5 = i2;
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Uri.CREATOR);
                    iG = i;
                    iG2 = i5;
                    break;
                case 3:
                    uri = uri2;
                    iG3 = i3;
                    int i6 = i;
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    iG = i6;
                    break;
                case 4:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    iG2 = i2;
                    uri = uri2;
                    iG3 = i3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    iG = i;
                    iG2 = i2;
                    uri = uri2;
                    iG3 = i3;
                    break;
            }
            i3 = iG3;
            uri2 = uri;
            i2 = iG2;
            i = iG;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new WebImage(i3, uri2, i2, i);
    }
}
