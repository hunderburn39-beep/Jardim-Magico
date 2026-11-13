package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class io implements Parcelable.Creator<ih.d> {
    static void a(ih.d dVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = dVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, dVar.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, dVar.getFamilyName(), true);
        }
        if (setJa.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, dVar.getFormatted(), true);
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, dVar.getGivenName(), true);
        }
        if (setJa.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, dVar.getHonorificPrefix(), true);
        }
        if (setJa.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, dVar.getHonorificSuffix(), true);
        }
        if (setJa.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, dVar.getMiddleName(), true);
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aT, reason: merged with bridge method [inline-methods] */
    public ih.d createFromParcel(Parcel parcel) {
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        int iG = 0;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(2);
                    break;
                case 3:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(3);
                    break;
                case 4:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(4);
                    break;
                case 5:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(5);
                    break;
                case 6:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(6);
                    break;
                case 7:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(7);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ih.d(hashSet, iG, strN6, strN5, strN4, strN3, strN2, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bW, reason: merged with bridge method [inline-methods] */
    public ih.d[] newArray(int i) {
        return new ih.d[i];
    }
}
