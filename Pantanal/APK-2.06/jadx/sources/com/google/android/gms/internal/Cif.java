package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.google.android.gms.internal.if, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public class Cif implements Parcelable.Creator<ie> {
    static void a(ie ieVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = ieVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, ieVar.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, ieVar.getId(), true);
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) ieVar.jr(), i, true);
        }
        if (setJa.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, ieVar.getStartDate(), true);
        }
        if (setJa.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, (Parcelable) ieVar.js(), i, true);
        }
        if (setJa.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, ieVar.getType(), true);
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aM, reason: merged with bridge method [inline-methods] */
    public ie createFromParcel(Parcel parcel) {
        String strN = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        int iG = 0;
        ic icVar = null;
        String strN2 = null;
        ic icVar2 = null;
        String strN3 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(2);
                    break;
                case 3:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
                case 4:
                    ic icVar3 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(4);
                    icVar2 = icVar3;
                    break;
                case 5:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(5);
                    break;
                case 6:
                    ic icVar4 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(6);
                    icVar = icVar4;
                    break;
                case 7:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(7);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ie(hashSet, iG, strN3, icVar2, strN2, icVar, strN);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bP, reason: merged with bridge method [inline-methods] */
    public ie[] newArray(int i) {
        return new ie[i];
    }
}
