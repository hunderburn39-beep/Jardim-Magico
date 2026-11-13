package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class l implements Parcelable.Creator<MaskedWalletRequest> {
    static void a(MaskedWalletRequest maskedWalletRequest, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, maskedWalletRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, maskedWalletRequest.abi, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, maskedWalletRequest.abV);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, maskedWalletRequest.abW);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, maskedWalletRequest.abX);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, maskedWalletRequest.abY, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, maskedWalletRequest.abd, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, maskedWalletRequest.abZ, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, (Parcelable) maskedWalletRequest.abr, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, maskedWalletRequest.aca);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, maskedWalletRequest.acb);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, (Parcelable[]) maskedWalletRequest.acc, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 13, maskedWalletRequest.acd);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, maskedWalletRequest.ace);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 15, maskedWalletRequest.acf, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bh, reason: merged with bridge method [inline-methods] */
    public MaskedWalletRequest createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        boolean zC = false;
        boolean zC2 = false;
        boolean zC3 = false;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        Cart cart = null;
        boolean zC4 = false;
        boolean zC5 = false;
        CountrySpecification[] countrySpecificationArr = null;
        boolean zC6 = true;
        boolean zC7 = true;
        ArrayList arrayListC = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 4:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 5:
                    zC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 6:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 7:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 8:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 9:
                    cart = (Cart) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Cart.CREATOR);
                    break;
                case 10:
                    zC4 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 11:
                    zC5 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 12:
                    countrySpecificationArr = (CountrySpecification[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN, CountrySpecification.CREATOR);
                    break;
                case 13:
                    zC6 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 14:
                    zC7 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    break;
                case 15:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN, com.google.android.gms.identity.intents.model.CountrySpecification.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new MaskedWalletRequest(iG, strN, zC, zC2, zC3, strN2, strN3, strN4, cart, zC4, zC5, countrySpecificationArr, zC6, zC7, arrayListC);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ct, reason: merged with bridge method [inline-methods] */
    public MaskedWalletRequest[] newArray(int i) {
        return new MaskedWalletRequest[i];
    }
}
