package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class c implements Parcelable.Creator<FieldWithSortOrder> {
    static void a(FieldWithSortOrder fieldWithSortOrder, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, fieldWithSortOrder.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, fieldWithSortOrder.FM, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, fieldWithSortOrder.GJ);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aK, reason: merged with bridge method [inline-methods] */
    public FieldWithSortOrder[] newArray(int i) {
        return new FieldWithSortOrder[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ag, reason: merged with bridge method [inline-methods] */
    public FieldWithSortOrder createFromParcel(Parcel parcel) {
        boolean zC = false;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        String strN = null;
        int iG = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 2:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
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
        return new FieldWithSortOrder(iG, strN, zC);
    }
}
