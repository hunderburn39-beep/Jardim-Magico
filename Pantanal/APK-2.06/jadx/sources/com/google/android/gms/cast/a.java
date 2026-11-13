package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<ApplicationMetadata> {
    static void a(ApplicationMetadata applicationMetadata, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, applicationMetadata.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, applicationMetadata.getApplicationId(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, applicationMetadata.getName(), false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 4, applicationMetadata.getImages(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, applicationMetadata.xK, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, applicationMetadata.getSenderAppIdentifier(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, (Parcelable) applicationMetadata.dz(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public ApplicationMetadata createFromParcel(Parcel parcel) {
        Uri uri = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        ArrayList<String> arrayListA = null;
        ArrayList arrayListC = null;
        String strN2 = null;
        String strN3 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, WebImage.CREATOR);
                    break;
                case 5:
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iN);
                    break;
                case 6:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 7:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Uri.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ApplicationMetadata(iG, strN3, strN2, arrayListC, arrayListA, strN, uri);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: w, reason: merged with bridge method [inline-methods] */
    public ApplicationMetadata[] newArray(int i) {
        return new ApplicationMetadata[i];
    }
}
