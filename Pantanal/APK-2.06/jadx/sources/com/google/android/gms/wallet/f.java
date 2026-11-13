package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.identity.intents.model.UserAddress;

/* JADX INFO: loaded from: classes.dex */
public class f implements Parcelable.Creator<FullWallet> {
    static void a(FullWallet fullWallet, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, fullWallet.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, fullWallet.abh, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, fullWallet.abi, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) fullWallet.abj, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, fullWallet.abk, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, (Parcelable) fullWallet.abl, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, (Parcelable) fullWallet.abm, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, fullWallet.abn, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, (Parcelable) fullWallet.abo, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, (Parcelable) fullWallet.abp, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, (Parcelable[]) fullWallet.abq, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bb, reason: merged with bridge method [inline-methods] */
    public FullWallet createFromParcel(Parcel parcel) {
        InstrumentInfo[] instrumentInfoArr = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        String[] strArrZ = null;
        Address address = null;
        Address address2 = null;
        String strN = null;
        ProxyCard proxyCard = null;
        String strN2 = null;
        String strN3 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 4:
                    proxyCard = (ProxyCard) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ProxyCard.CREATOR);
                    break;
                case 5:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iN);
                    break;
                case 6:
                    address2 = (Address) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Address.CREATOR);
                    break;
                case 7:
                    address = (Address) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Address.CREATOR);
                    break;
                case 8:
                    strArrZ = com.google.android.gms.common.internal.safeparcel.a.z(parcel, iN);
                    break;
                case 9:
                    userAddress2 = (UserAddress) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, UserAddress.CREATOR);
                    break;
                case 10:
                    userAddress = (UserAddress) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, UserAddress.CREATOR);
                    break;
                case 11:
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
        return new FullWallet(iG, strN3, strN2, proxyCard, strN, address2, address, strArrZ, userAddress2, userAddress, instrumentInfoArr);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cn, reason: merged with bridge method [inline-methods] */
    public FullWallet[] newArray(int i) {
        return new FullWallet[i];
    }
}
