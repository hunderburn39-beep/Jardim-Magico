package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class b implements Parcelable.Creator<WalletFragmentOptions> {
    static void a(WalletFragmentOptions walletFragmentOptions, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, walletFragmentOptions.xH);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, walletFragmentOptions.getEnvironment());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, walletFragmentOptions.getTheme());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) walletFragmentOptions.getFragmentStyle(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 5, walletFragmentOptions.getMode());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bo, reason: merged with bridge method [inline-methods] */
    public WalletFragmentOptions createFromParcel(Parcel parcel) {
        int iG = 1;
        int iG2 = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        WalletFragmentStyle walletFragmentStyle = null;
        int iG3 = 1;
        int iG4 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 3:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 4:
                    walletFragmentStyle = (WalletFragmentStyle) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, WalletFragmentStyle.CREATOR);
                    break;
                case 5:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new WalletFragmentOptions(iG4, iG3, iG2, walletFragmentStyle, iG);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: cB, reason: merged with bridge method [inline-methods] */
    public WalletFragmentOptions[] newArray(int i) {
        return new WalletFragmentOptions[i];
    }
}
