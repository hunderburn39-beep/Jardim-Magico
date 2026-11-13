package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class GameRequestEntityCreator implements Parcelable.Creator<GameRequestEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(GameRequestEntity gameRequestEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, (Parcelable) gameRequestEntity.getGame(), i, false);
        b.c(parcel, 1000, gameRequestEntity.getVersionCode());
        b.a(parcel, 2, (Parcelable) gameRequestEntity.getSender(), i, false);
        b.a(parcel, 3, gameRequestEntity.getData(), false);
        b.a(parcel, 4, gameRequestEntity.getRequestId(), false);
        b.b(parcel, 5, gameRequestEntity.getRecipients(), false);
        b.c(parcel, 7, gameRequestEntity.getType());
        b.a(parcel, 9, gameRequestEntity.getCreationTimestamp());
        b.a(parcel, 10, gameRequestEntity.getExpirationTimestamp());
        b.a(parcel, 11, gameRequestEntity.hK(), false);
        b.c(parcel, 12, gameRequestEntity.getStatus());
        b.F(parcel, iP);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public GameRequestEntity createFromParcel(Parcel parcel) {
        int iO = a.o(parcel);
        int iG = 0;
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        byte[] bArrQ = null;
        String strN = null;
        ArrayList arrayListC = null;
        int iG2 = 0;
        long jI = 0;
        long jI2 = 0;
        Bundle bundleP = null;
        int iG3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = a.n(parcel);
            switch (a.R(iN)) {
                case 1:
                    gameEntity = (GameEntity) a.a(parcel, iN, GameEntity.CREATOR);
                    break;
                case 2:
                    playerEntity = (PlayerEntity) a.a(parcel, iN, PlayerEntity.CREATOR);
                    break;
                case 3:
                    bArrQ = a.q(parcel, iN);
                    break;
                case 4:
                    strN = a.n(parcel, iN);
                    break;
                case 5:
                    arrayListC = a.c(parcel, iN, PlayerEntity.CREATOR);
                    break;
                case 7:
                    iG2 = a.g(parcel, iN);
                    break;
                case 9:
                    jI = a.i(parcel, iN);
                    break;
                case 10:
                    jI2 = a.i(parcel, iN);
                    break;
                case 11:
                    bundleP = a.p(parcel, iN);
                    break;
                case 12:
                    iG3 = a.g(parcel, iN);
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
        return new GameRequestEntity(iG, gameEntity, playerEntity, bArrQ, strN, arrayListC, iG2, jI, jI2, bundleP, iG3);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public GameRequestEntity[] newArray(int size) {
        return new GameRequestEntity[size];
    }
}
