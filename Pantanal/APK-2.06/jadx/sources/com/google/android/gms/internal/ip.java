package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ip implements Parcelable.Creator<ih.f> {
    static void a(ih.f fVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = fVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, fVar.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, fVar.getDepartment(), true);
        }
        if (setJa.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, fVar.getDescription(), true);
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, fVar.getEndDate(), true);
        }
        if (setJa.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, fVar.getLocation(), true);
        }
        if (setJa.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, fVar.getName(), true);
        }
        if (setJa.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, fVar.isPrimary());
        }
        if (setJa.contains(8)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, fVar.getStartDate(), true);
        }
        if (setJa.contains(9)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, fVar.getTitle(), true);
        }
        if (setJa.contains(10)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 10, fVar.getType());
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aU, reason: merged with bridge method [inline-methods] */
    public ih.f createFromParcel(Parcel parcel) {
        int iG = 0;
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        String strN2 = null;
        boolean zC = false;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        String strN7 = null;
        int iG2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(2);
                    break;
                case 3:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(3);
                    break;
                case 4:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(4);
                    break;
                case 5:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(5);
                    break;
                case 6:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(6);
                    break;
                case 7:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    hashSet.add(7);
                    break;
                case 8:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(8);
                    break;
                case 9:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(9);
                    break;
                case 10:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(10);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ih.f(hashSet, iG2, strN7, strN6, strN5, strN4, strN3, zC, strN2, strN, iG);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bX, reason: merged with bridge method [inline-methods] */
    public ih.f[] newArray(int i) {
        return new ih.f[i];
    }
}
