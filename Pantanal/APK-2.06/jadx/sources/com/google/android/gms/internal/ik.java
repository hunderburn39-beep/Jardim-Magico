package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ik implements Parcelable.Creator<ih.b> {
    static void a(ih.b bVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = bVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, bVar.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) bVar.jE(), i, true);
        }
        if (setJa.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, (Parcelable) bVar.jF(), i, true);
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, bVar.getLayout());
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aP, reason: merged with bridge method [inline-methods] */
    public ih.b createFromParcel(Parcel parcel) {
        ih.b.C0076b c0076b = null;
        int iG = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        ih.b.a aVar = null;
        int iG2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                    ih.b.a aVar2 = (ih.b.a) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ih.b.a.CREATOR);
                    hashSet.add(2);
                    aVar = aVar2;
                    break;
                case 3:
                    ih.b.C0076b c0076b2 = (ih.b.C0076b) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ih.b.C0076b.CREATOR);
                    hashSet.add(3);
                    c0076b = c0076b2;
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
        return new ih.b(hashSet, iG2, aVar, c0076b, iG);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bS, reason: merged with bridge method [inline-methods] */
    public ih.b[] newArray(int i) {
        return new ih.b[i];
    }
}
