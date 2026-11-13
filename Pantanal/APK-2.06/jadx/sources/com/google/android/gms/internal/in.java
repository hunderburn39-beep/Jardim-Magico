package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class in implements Parcelable.Creator<ih.c> {
    static void a(ih.c cVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = cVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, cVar.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, cVar.getUrl(), true);
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aS, reason: merged with bridge method [inline-methods] */
    public ih.c createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        int iG = 0;
        String strN = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(2);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ih.c(hashSet, iG, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bV, reason: merged with bridge method [inline-methods] */
    public ih.c[] newArray(int i) {
        return new ih.c[i];
    }
}
