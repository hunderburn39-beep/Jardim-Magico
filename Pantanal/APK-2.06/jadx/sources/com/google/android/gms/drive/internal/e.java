package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.Contents;

/* JADX INFO: loaded from: classes.dex */
public class e implements Parcelable.Creator<CloseContentsRequest> {
    static void a(CloseContentsRequest closeContentsRequest, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, closeContentsRequest.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) closeContentsRequest.EX, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, closeContentsRequest.EY, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: F, reason: merged with bridge method [inline-methods] */
    public CloseContentsRequest createFromParcel(Parcel parcel) {
        Boolean boolD;
        Contents contents;
        int iG;
        Boolean bool = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int i = 0;
        Contents contents2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    Boolean bool2 = bool;
                    contents = contents2;
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    boolD = bool2;
                    break;
                case 2:
                    Contents contents3 = (Contents) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Contents.CREATOR);
                    iG = i;
                    boolD = bool;
                    contents = contents3;
                    break;
                case 3:
                    boolD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iN);
                    contents = contents2;
                    iG = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    boolD = bool;
                    contents = contents2;
                    iG = i;
                    break;
            }
            i = iG;
            contents2 = contents;
            bool = boolD;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new CloseContentsRequest(i, contents2, bool);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aj, reason: merged with bridge method [inline-methods] */
    public CloseContentsRequest[] newArray(int i) {
        return new CloseContentsRequest[i];
    }
}
