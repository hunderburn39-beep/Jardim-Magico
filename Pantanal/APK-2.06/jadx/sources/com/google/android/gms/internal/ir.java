package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ir implements Parcelable.Creator<ih.h> {
    static void a(ih.h hVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = hVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, hVar.getVersionCode());
        }
        if (setJa.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, hVar.jN());
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, hVar.getValue(), true);
        }
        if (setJa.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, hVar.getLabel(), true);
        }
        if (setJa.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, hVar.getType());
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aW, reason: merged with bridge method [inline-methods] */
    public ih.h createFromParcel(Parcel parcel) {
        String strN = null;
        int iG = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        int iG2 = 0;
        String strN2 = null;
        int iG3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
                case 3:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(3);
                    break;
                case 4:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(4);
                    break;
                case 5:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(5);
                    break;
                case 6:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(6);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ih.h(hashSet, iG3, strN2, iG2, strN, iG);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bZ, reason: merged with bridge method [inline-methods] */
    public ih.h[] newArray(int i) {
        return new ih.h[i];
    }
}
