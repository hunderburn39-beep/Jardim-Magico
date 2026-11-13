package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class i implements Parcelable.Creator<LineItem> {
    static void a(LineItem lineItem, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, lineItem.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, lineItem.description, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, lineItem.abv, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, lineItem.abw, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, lineItem.abc, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, lineItem.abx);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, lineItem.abd, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: be, reason: merged with bridge method [inline-methods] */
    public LineItem createFromParcel(Parcel parcel) {
        int iG = 0;
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        int iG2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 5:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 7:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new LineItem(iG2, strN5, strN4, strN3, strN2, iG, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cq, reason: merged with bridge method [inline-methods] */
    public LineItem[] newArray(int i) {
        return new LineItem[i];
    }
}
