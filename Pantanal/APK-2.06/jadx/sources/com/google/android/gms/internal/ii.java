package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public class ii implements Parcelable.Creator<ih> {
    static void a(ih ihVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = ihVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, ihVar.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, ihVar.getAboutMe(), true);
        }
        if (setJa.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, (Parcelable) ihVar.jv(), i, true);
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, ihVar.getBirthday(), true);
        }
        if (setJa.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, ihVar.getBraggingRights(), true);
        }
        if (setJa.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, ihVar.getCircledByCount());
        }
        if (setJa.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, (Parcelable) ihVar.jw(), i, true);
        }
        if (setJa.contains(8)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, ihVar.getCurrentLocation(), true);
        }
        if (setJa.contains(9)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, ihVar.getDisplayName(), true);
        }
        if (setJa.contains(12)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 12, ihVar.getGender());
        }
        if (setJa.contains(14)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, ihVar.getId(), true);
        }
        if (setJa.contains(15)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 15, (Parcelable) ihVar.jx(), i, true);
        }
        if (setJa.contains(16)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 16, ihVar.isPlusUser());
        }
        if (setJa.contains(19)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 19, (Parcelable) ihVar.jy(), i, true);
        }
        if (setJa.contains(18)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 18, ihVar.getLanguage(), true);
        }
        if (setJa.contains(21)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 21, ihVar.getObjectType());
        }
        if (setJa.contains(20)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 20, ihVar.getNickname(), true);
        }
        if (setJa.contains(23)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 23, ihVar.jA(), true);
        }
        if (setJa.contains(22)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 22, ihVar.jz(), true);
        }
        if (setJa.contains(25)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 25, ihVar.getRelationshipStatus());
        }
        if (setJa.contains(24)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 24, ihVar.getPlusOneCount());
        }
        if (setJa.contains(27)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 27, ihVar.getUrl(), true);
        }
        if (setJa.contains(26)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 26, ihVar.getTagline(), true);
        }
        if (setJa.contains(29)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 29, ihVar.isVerified());
        }
        if (setJa.contains(28)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 28, ihVar.jB(), true);
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aN, reason: merged with bridge method [inline-methods] */
    public ih createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        int iG = 0;
        String strN = null;
        ih.a aVar = null;
        String strN2 = null;
        String strN3 = null;
        int iG2 = 0;
        ih.b bVar = null;
        String strN4 = null;
        String strN5 = null;
        int iG3 = 0;
        String strN6 = null;
        ih.c cVar = null;
        boolean zC = false;
        String strN7 = null;
        ih.d dVar = null;
        String strN8 = null;
        int iG4 = 0;
        ArrayList arrayListC = null;
        ArrayList arrayListC2 = null;
        int iG5 = 0;
        int iG6 = 0;
        String strN9 = null;
        String strN10 = null;
        ArrayList arrayListC3 = null;
        boolean zC2 = false;
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
                case 3:
                    ih.a aVar2 = (ih.a) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ih.a.CREATOR);
                    hashSet.add(3);
                    aVar = aVar2;
                    break;
                case 4:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(4);
                    break;
                case 5:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(5);
                    break;
                case 6:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(6);
                    break;
                case 7:
                    ih.b bVar2 = (ih.b) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ih.b.CREATOR);
                    hashSet.add(7);
                    bVar = bVar2;
                    break;
                case 8:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(8);
                    break;
                case 9:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(9);
                    break;
                case 10:
                case 11:
                case 13:
                case 17:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
                case 12:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(12);
                    break;
                case 14:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(14);
                    break;
                case 15:
                    ih.c cVar2 = (ih.c) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ih.c.CREATOR);
                    hashSet.add(15);
                    cVar = cVar2;
                    break;
                case 16:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    hashSet.add(16);
                    break;
                case 18:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(18);
                    break;
                case 19:
                    ih.d dVar2 = (ih.d) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ih.d.CREATOR);
                    hashSet.add(19);
                    dVar = dVar2;
                    break;
                case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                    strN8 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(20);
                    break;
                case 21:
                    iG4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(21);
                    break;
                case 22:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ih.f.CREATOR);
                    hashSet.add(22);
                    break;
                case 23:
                    arrayListC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ih.g.CREATOR);
                    hashSet.add(23);
                    break;
                case TimeConstants.HOURSPERDAY /* 24 */:
                    iG5 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(24);
                    break;
                case 25:
                    iG6 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(25);
                    break;
                case 26:
                    strN9 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(26);
                    break;
                case 27:
                    strN10 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(27);
                    break;
                case 28:
                    arrayListC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ih.h.CREATOR);
                    hashSet.add(28);
                    break;
                case 29:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    hashSet.add(29);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ih(hashSet, iG, strN, aVar, strN2, strN3, iG2, bVar, strN4, strN5, iG3, strN6, cVar, zC, strN7, dVar, strN8, iG4, arrayListC, arrayListC2, iG5, iG6, strN9, strN10, arrayListC3, zC2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bQ, reason: merged with bridge method [inline-methods] */
    public ih[] newArray(int i) {
        return new ih[i];
    }
}
