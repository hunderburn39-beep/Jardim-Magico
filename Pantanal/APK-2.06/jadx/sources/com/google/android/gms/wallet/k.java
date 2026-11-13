package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.identity.intents.model.UserAddress;

/* JADX INFO: loaded from: classes.dex */
public class k implements Parcelable.Creator<MaskedWallet> {
    static void a(MaskedWallet maskedWallet, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, maskedWallet.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, maskedWallet.abh, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, maskedWallet.abi, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, maskedWallet.abn, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, maskedWallet.abk, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, (Parcelable) maskedWallet.abl, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, (Parcelable) maskedWallet.abm, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, (Parcelable[]) maskedWallet.abT, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, (Parcelable[]) maskedWallet.abU, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, (Parcelable) maskedWallet.abo, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, (Parcelable) maskedWallet.abp, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, (Parcelable[]) maskedWallet.abq, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bg, reason: merged with bridge method [inline-methods] */
    public MaskedWallet createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        String[] strArrZ = null;
        String strN3 = null;
        Address address = null;
        Address address2 = null;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        OfferWalletObject[] offerWalletObjectArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
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
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    strArrZ = com.google.android.gms.common.internal.safeparcel.a.z(parcel, iN);
                    break;
                case 5:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    address = (Address) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Address.CREATOR);
                    break;
                case 7:
                    address2 = (Address) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Address.CREATOR);
                    break;
                case 8:
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN, LoyaltyWalletObject.CREATOR);
                    break;
                case 9:
                    offerWalletObjectArr = (OfferWalletObject[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN, OfferWalletObject.CREATOR);
                    break;
                case 10:
                    userAddress = (UserAddress) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, UserAddress.CREATOR);
                    break;
                case 11:
                    userAddress2 = (UserAddress) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, UserAddress.CREATOR);
                    break;
                case 12:
                    instrumentInfoArr = (InstrumentInfo[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN, InstrumentInfo.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new MaskedWallet(iG, strN, strN2, strArrZ, strN3, address, address2, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress, userAddress2, instrumentInfoArr);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cs, reason: merged with bridge method [inline-methods] */
    public MaskedWallet[] newArray(int i) {
        return new MaskedWallet[i];
    }
}
