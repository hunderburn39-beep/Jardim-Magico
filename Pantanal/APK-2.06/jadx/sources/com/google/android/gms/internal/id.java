package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.fyber.ads.banners.BannerAd;
import com.google.ads.AdSize;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.anddev.andengine.opengl.texture.compressed.pvr.PVRTexture;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public class id implements Parcelable.Creator<ic> {
    static void a(ic icVar, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        Set<Integer> setJa = icVar.ja();
        if (setJa.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, icVar.getVersionCode());
        }
        if (setJa.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) icVar.jb(), i, true);
        }
        if (setJa.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, icVar.getAdditionalName(), true);
        }
        if (setJa.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) icVar.jc(), i, true);
        }
        if (setJa.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, icVar.getAddressCountry(), true);
        }
        if (setJa.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, icVar.getAddressLocality(), true);
        }
        if (setJa.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, icVar.getAddressRegion(), true);
        }
        if (setJa.contains(8)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 8, icVar.jd(), true);
        }
        if (setJa.contains(9)) {
            com.google.android.gms.common.internal.safeparcel.b.c(parcel, 9, icVar.getAttendeeCount());
        }
        if (setJa.contains(10)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 10, icVar.je(), true);
        }
        if (setJa.contains(11)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, (Parcelable) icVar.jf(), i, true);
        }
        if (setJa.contains(12)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 12, icVar.jg(), true);
        }
        if (setJa.contains(13)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 13, icVar.getBestRating(), true);
        }
        if (setJa.contains(14)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, icVar.getBirthDate(), true);
        }
        if (setJa.contains(15)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 15, (Parcelable) icVar.jh(), i, true);
        }
        if (setJa.contains(17)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 17, icVar.getContentSize(), true);
        }
        if (setJa.contains(16)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 16, icVar.getCaption(), true);
        }
        if (setJa.contains(19)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 19, icVar.ji(), true);
        }
        if (setJa.contains(18)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 18, icVar.getContentUrl(), true);
        }
        if (setJa.contains(21)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 21, icVar.getDateModified(), true);
        }
        if (setJa.contains(20)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 20, icVar.getDateCreated(), true);
        }
        if (setJa.contains(23)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 23, icVar.getDescription(), true);
        }
        if (setJa.contains(22)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 22, icVar.getDatePublished(), true);
        }
        if (setJa.contains(25)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 25, icVar.getEmbedUrl(), true);
        }
        if (setJa.contains(24)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 24, icVar.getDuration(), true);
        }
        if (setJa.contains(27)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 27, icVar.getFamilyName(), true);
        }
        if (setJa.contains(26)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 26, icVar.getEndDate(), true);
        }
        if (setJa.contains(29)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 29, (Parcelable) icVar.jj(), i, true);
        }
        if (setJa.contains(28)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 28, icVar.getGender(), true);
        }
        if (setJa.contains(31)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 31, icVar.getHeight(), true);
        }
        if (setJa.contains(30)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 30, icVar.getGivenName(), true);
        }
        if (setJa.contains(34)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 34, (Parcelable) icVar.jk(), i, true);
        }
        if (setJa.contains(32)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 32, icVar.getId(), true);
        }
        if (setJa.contains(33)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 33, icVar.getImage(), true);
        }
        if (setJa.contains(38)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 38, icVar.getLongitude());
        }
        if (setJa.contains(39)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 39, icVar.getName(), true);
        }
        if (setJa.contains(36)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 36, icVar.getLatitude());
        }
        if (setJa.contains(37)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 37, (Parcelable) icVar.jl(), i, true);
        }
        if (setJa.contains(42)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 42, icVar.getPlayerType(), true);
        }
        if (setJa.contains(43)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 43, icVar.getPostOfficeBoxNumber(), true);
        }
        if (setJa.contains(40)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 40, (Parcelable) icVar.jm(), i, true);
        }
        if (setJa.contains(41)) {
            com.google.android.gms.common.internal.safeparcel.b.b(parcel, 41, icVar.jn(), true);
        }
        if (setJa.contains(46)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 46, (Parcelable) icVar.jo(), i, true);
        }
        if (setJa.contains(47)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 47, icVar.getStartDate(), true);
        }
        if (setJa.contains(44)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 44, icVar.getPostalCode(), true);
        }
        if (setJa.contains(45)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 45, icVar.getRatingValue(), true);
        }
        if (setJa.contains(51)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 51, icVar.getThumbnailUrl(), true);
        }
        if (setJa.contains(50)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 50, (Parcelable) icVar.jp(), i, true);
        }
        if (setJa.contains(49)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 49, icVar.getText(), true);
        }
        if (setJa.contains(48)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 48, icVar.getStreetAddress(), true);
        }
        if (setJa.contains(55)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 55, icVar.getWidth(), true);
        }
        if (setJa.contains(54)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 54, icVar.getUrl(), true);
        }
        if (setJa.contains(53)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 53, icVar.getType(), true);
        }
        if (setJa.contains(52)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 52, icVar.getTickerSymbol(), true);
        }
        if (setJa.contains(56)) {
            com.google.android.gms.common.internal.safeparcel.b.a(parcel, 56, icVar.getWorstRating(), true);
        }
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aL, reason: merged with bridge method [inline-methods] */
    public ic createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        HashSet hashSet = new HashSet();
        int iG = 0;
        ic icVar = null;
        ArrayList<String> arrayListA = null;
        ic icVar2 = null;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        ArrayList arrayListC = null;
        int iG2 = 0;
        ArrayList arrayListC2 = null;
        ic icVar3 = null;
        ArrayList arrayListC3 = null;
        String strN4 = null;
        String strN5 = null;
        ic icVar4 = null;
        String strN6 = null;
        String strN7 = null;
        String strN8 = null;
        ArrayList arrayListC4 = null;
        String strN9 = null;
        String strN10 = null;
        String strN11 = null;
        String strN12 = null;
        String strN13 = null;
        String strN14 = null;
        String strN15 = null;
        String strN16 = null;
        String strN17 = null;
        ic icVar5 = null;
        String strN18 = null;
        String strN19 = null;
        String strN20 = null;
        String strN21 = null;
        ic icVar6 = null;
        double dL = 0.0d;
        ic icVar7 = null;
        double dL2 = 0.0d;
        String strN22 = null;
        ic icVar8 = null;
        ArrayList arrayListC5 = null;
        String strN23 = null;
        String strN24 = null;
        String strN25 = null;
        String strN26 = null;
        ic icVar9 = null;
        String strN27 = null;
        String strN28 = null;
        String strN29 = null;
        ic icVar10 = null;
        String strN30 = null;
        String strN31 = null;
        String strN32 = null;
        String strN33 = null;
        String strN34 = null;
        String strN35 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(1);
                    break;
                case 2:
                    ic icVar11 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(2);
                    icVar = icVar11;
                    break;
                case 3:
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iN);
                    hashSet.add(3);
                    break;
                case 4:
                    ic icVar12 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(4);
                    icVar2 = icVar12;
                    break;
                case 5:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(5);
                    break;
                case 6:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(6);
                    break;
                case 7:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(7);
                    break;
                case 8:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ic.CREATOR);
                    hashSet.add(8);
                    break;
                case 9:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    hashSet.add(9);
                    break;
                case 10:
                    arrayListC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ic.CREATOR);
                    hashSet.add(10);
                    break;
                case 11:
                    ic icVar13 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(11);
                    icVar3 = icVar13;
                    break;
                case 12:
                    arrayListC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ic.CREATOR);
                    hashSet.add(12);
                    break;
                case 13:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(13);
                    break;
                case 14:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(14);
                    break;
                case 15:
                    ic icVar14 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(15);
                    icVar4 = icVar14;
                    break;
                case 16:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(16);
                    break;
                case 17:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(17);
                    break;
                case 18:
                    strN8 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(18);
                    break;
                case 19:
                    arrayListC4 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ic.CREATOR);
                    hashSet.add(19);
                    break;
                case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                    strN9 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(20);
                    break;
                case 21:
                    strN10 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(21);
                    break;
                case 22:
                    strN11 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(22);
                    break;
                case 23:
                    strN12 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(23);
                    break;
                case TimeConstants.HOURSPERDAY /* 24 */:
                    strN13 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(24);
                    break;
                case 25:
                    strN14 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(25);
                    break;
                case 26:
                    strN15 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(26);
                    break;
                case 27:
                    strN16 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(27);
                    break;
                case 28:
                    strN17 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(28);
                    break;
                case 29:
                    ic icVar15 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(29);
                    icVar5 = icVar15;
                    break;
                case TimeConstants.DAYSPERMONTH /* 30 */:
                    strN18 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(30);
                    break;
                case 31:
                    strN19 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(31);
                    break;
                case 32:
                    strN20 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(32);
                    break;
                case 33:
                    strN21 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(33);
                    break;
                case 34:
                    ic icVar16 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(34);
                    icVar6 = icVar16;
                    break;
                case 35:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
                case 36:
                    dL = com.google.android.gms.common.internal.safeparcel.a.l(parcel, iN);
                    hashSet.add(36);
                    break;
                case 37:
                    ic icVar17 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(37);
                    icVar7 = icVar17;
                    break;
                case 38:
                    dL2 = com.google.android.gms.common.internal.safeparcel.a.l(parcel, iN);
                    hashSet.add(38);
                    break;
                case 39:
                    strN22 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(39);
                    break;
                case 40:
                    ic icVar18 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(40);
                    icVar8 = icVar18;
                    break;
                case 41:
                    arrayListC5 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, ic.CREATOR);
                    hashSet.add(41);
                    break;
                case 42:
                    strN23 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(42);
                    break;
                case 43:
                    strN24 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(43);
                    break;
                case 44:
                    strN25 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(44);
                    break;
                case 45:
                    strN26 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(45);
                    break;
                case 46:
                    ic icVar19 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(46);
                    icVar9 = icVar19;
                    break;
                case 47:
                    strN27 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(47);
                    break;
                case BannerAd.POSITION_TOP /* 48 */:
                    strN28 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(48);
                    break;
                case 49:
                    strN29 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(49);
                    break;
                case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                    ic icVar20 = (ic) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ic.CREATOR);
                    hashSet.add(50);
                    icVar10 = icVar20;
                    break;
                case 51:
                    strN30 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(51);
                    break;
                case PVRTexture.PVRTextureHeader.SIZE /* 52 */:
                    strN31 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(52);
                    break;
                case 53:
                    strN32 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(53);
                    break;
                case 54:
                    strN33 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(54);
                    break;
                case 55:
                    strN34 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(55);
                    break;
                case 56:
                    strN35 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    hashSet.add(56);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ic(hashSet, iG, icVar, arrayListA, icVar2, strN, strN2, strN3, arrayListC, iG2, arrayListC2, icVar3, arrayListC3, strN4, strN5, icVar4, strN6, strN7, strN8, arrayListC4, strN9, strN10, strN11, strN12, strN13, strN14, strN15, strN16, strN17, icVar5, strN18, strN19, strN20, strN21, icVar6, dL, icVar7, dL2, strN22, icVar8, arrayListC5, strN23, strN24, strN25, strN26, icVar9, strN27, strN28, strN29, icVar10, strN30, strN31, strN32, strN33, strN34, strN35);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bO, reason: merged with bridge method [inline-methods] */
    public ic[] newArray(int i) {
        return new ic[i];
    }
}
