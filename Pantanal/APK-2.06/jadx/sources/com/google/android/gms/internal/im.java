package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class im implements Parcelable.Creator<ih.b.C0076b> {
    static void a(ih.b.C0076b c0076b, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = c0076b.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, c0076b.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, c0076b.getHeight());
        }
        if (setJa.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, c0076b.getUrl(), true);
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, c0076b.getWidth());
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aR, reason: merged with bridge method [inline-methods] */
    public ih.b.C0076b createFromParcel(Parcel parcel) {
        int iG = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        String strN = null;
        int iG2 = 0;
        int iG3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(2);
                    break;
                case 3:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(3);
                    break;
                case 4:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(4);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ih.b.C0076b(hashSet, iG3, iG2, strN, iG);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bU, reason: merged with bridge method [inline-methods] */
    public ih.b.C0076b[] newArray(int i) {
        return new ih.b.C0076b[i];
    }
}
