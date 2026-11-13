package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TurnBasedMatchEntityCreator implements Parcelable.Creator<TurnBasedMatchEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(TurnBasedMatchEntity turnBasedMatchEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, (Parcelable) turnBasedMatchEntity.getGame(), i, false);
        b.a(parcel, 2, turnBasedMatchEntity.getMatchId(), false);
        b.a(parcel, 3, turnBasedMatchEntity.getCreatorId(), false);
        b.a(parcel, 4, turnBasedMatchEntity.getCreationTimestamp());
        b.a(parcel, 5, turnBasedMatchEntity.getLastUpdaterId(), false);
        b.a(parcel, 6, turnBasedMatchEntity.getLastUpdatedTimestamp());
        b.a(parcel, 7, turnBasedMatchEntity.getPendingParticipantId(), false);
        b.c(parcel, 8, turnBasedMatchEntity.getStatus());
        b.c(parcel, 10, turnBasedMatchEntity.getVariant());
        b.c(parcel, 11, turnBasedMatchEntity.getVersion());
        b.a(parcel, 12, turnBasedMatchEntity.getData(), false);
        b.b(parcel, 13, turnBasedMatchEntity.getParticipants(), false);
        b.a(parcel, 14, turnBasedMatchEntity.getRematchId(), false);
        b.a(parcel, 15, turnBasedMatchEntity.getPreviousMatchData(), false);
        b.a(parcel, 17, turnBasedMatchEntity.getAutoMatchCriteria(), false);
        b.c(parcel, 16, turnBasedMatchEntity.getMatchNumber());
        b.c(parcel, 1000, turnBasedMatchEntity.getVersionCode());
        b.a(parcel, 19, turnBasedMatchEntity.isLocallyModified());
        b.c(parcel, 18, turnBasedMatchEntity.getTurnStatus());
        b.a(parcel, 21, turnBasedMatchEntity.getDescriptionParticipantId(), false);
        b.a(parcel, 20, turnBasedMatchEntity.getDescription(), false);
        b.F(parcel, iP);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public TurnBasedMatchEntity createFromParcel(Parcel parcel) {
        int iO = a.o(parcel);
        int iG = 0;
        GameEntity gameEntity = null;
        String strN = null;
        String strN2 = null;
        long jI = 0;
        String strN3 = null;
        long jI2 = 0;
        String strN4 = null;
        int iG2 = 0;
        int iG3 = 0;
        int iG4 = 0;
        byte[] bArrQ = null;
        ArrayList arrayListC = null;
        String strN5 = null;
        byte[] bArrQ2 = null;
        int iG5 = 0;
        Bundle bundleP = null;
        int iG6 = 0;
        boolean zC = false;
        String strN6 = null;
        String strN7 = null;
        while (parcel.dataPosition() < iO) {
            int iN = a.n(parcel);
            switch (a.R(iN)) {
                case 1:
                    gameEntity = (GameEntity) a.a(parcel, iN, GameEntity.CREATOR);
                    break;
                case 2:
                    strN = a.n(parcel, iN);
                    break;
                case 3:
                    strN2 = a.n(parcel, iN);
                    break;
                case 4:
                    jI = a.i(parcel, iN);
                    break;
                case 5:
                    strN3 = a.n(parcel, iN);
                    break;
                case 6:
                    jI2 = a.i(parcel, iN);
                    break;
                case 7:
                    strN4 = a.n(parcel, iN);
                    break;
                case 8:
                    iG2 = a.g(parcel, iN);
                    break;
                case 10:
                    iG3 = a.g(parcel, iN);
                    break;
                case 11:
                    iG4 = a.g(parcel, iN);
                    break;
                case 12:
                    bArrQ = a.q(parcel, iN);
                    break;
                case 13:
                    arrayListC = a.c(parcel, iN, ParticipantEntity.CREATOR);
                    break;
                case 14:
                    strN5 = a.n(parcel, iN);
                    break;
                case 15:
                    bArrQ2 = a.q(parcel, iN);
                    break;
                case 16:
                    iG5 = a.g(parcel, iN);
                    break;
                case 17:
                    bundleP = a.p(parcel, iN);
                    break;
                case 18:
                    iG6 = a.g(parcel, iN);
                    break;
                case 19:
                    zC = a.c(parcel, iN);
                    break;
                case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                    strN6 = a.n(parcel, iN);
                    break;
                case 21:
                    strN7 = a.n(parcel, iN);
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
        return new TurnBasedMatchEntity(iG, gameEntity, strN, strN2, jI, strN3, jI2, strN4, iG2, iG3, iG4, bArrQ, arrayListC, strN5, bArrQ2, iG5, bundleP, iG6, zC, strN6, strN7);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public TurnBasedMatchEntity[] newArray(int size) {
        return new TurnBasedMatchEntity[size];
    }
}
