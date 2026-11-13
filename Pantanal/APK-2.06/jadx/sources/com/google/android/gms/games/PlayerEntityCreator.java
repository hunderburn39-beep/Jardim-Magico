package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

/* JADX INFO: loaded from: classes.dex */
public class PlayerEntityCreator implements Parcelable.Creator<PlayerEntity> {
    static void a(PlayerEntity playerEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, playerEntity.getPlayerId(), false);
        b.c(parcel, 1000, playerEntity.getVersionCode());
        b.a(parcel, 2, playerEntity.getDisplayName(), false);
        b.a(parcel, 3, (Parcelable) playerEntity.getIconImageUri(), i, false);
        b.a(parcel, 4, (Parcelable) playerEntity.getHiResImageUri(), i, false);
        b.a(parcel, 5, playerEntity.getRetrievedTimestamp());
        b.c(parcel, 6, playerEntity.gh());
        b.a(parcel, 7, playerEntity.getLastPlayedWithTimestamp());
        b.a(parcel, 8, playerEntity.getIconImageUrl(), false);
        b.a(parcel, 9, playerEntity.getHiResImageUrl(), false);
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aT, reason: merged with bridge method [inline-methods] */
    public PlayerEntity[] newArray(int i) {
        return new PlayerEntity[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ao, reason: merged with bridge method [inline-methods] */
    public PlayerEntity createFromParcel(Parcel parcel) {
        int iO = a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        Uri uri = null;
        Uri uri2 = null;
        long jI = 0;
        int iG2 = 0;
        long jI2 = 0;
        String strN3 = null;
        String strN4 = null;
        while (parcel.dataPosition() < iO) {
            int iN = a.n(parcel);
            switch (a.R(iN)) {
                case 1:
                    strN = a.n(parcel, iN);
                    break;
                case 2:
                    strN2 = a.n(parcel, iN);
                    break;
                case 3:
                    uri = (Uri) a.a(parcel, iN, Uri.CREATOR);
                    break;
                case 4:
                    uri2 = (Uri) a.a(parcel, iN, Uri.CREATOR);
                    break;
                case 5:
                    jI = a.i(parcel, iN);
                    break;
                case 6:
                    iG2 = a.g(parcel, iN);
                    break;
                case 7:
                    jI2 = a.i(parcel, iN);
                    break;
                case 8:
                    strN3 = a.n(parcel, iN);
                    break;
                case 9:
                    strN4 = a.n(parcel, iN);
                    break;
                case 1000:
                    iG = a.g(parcel, iN);
                    break;
                default:
                    a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new PlayerEntity(iG, strN, strN2, uri, uri2, jI, iG2, jI2, strN3, strN4);
    }
}
