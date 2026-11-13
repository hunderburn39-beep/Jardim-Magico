package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class InvitationEntityCreator implements Parcelable.Creator<InvitationEntity> {
    static void a(InvitationEntity invitationEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, (Parcelable) invitationEntity.getGame(), i, false);
        b.c(parcel, 1000, invitationEntity.getVersionCode());
        b.a(parcel, 2, invitationEntity.getInvitationId(), false);
        b.a(parcel, 3, invitationEntity.getCreationTimestamp());
        b.c(parcel, 4, invitationEntity.getInvitationType());
        b.a(parcel, 5, (Parcelable) invitationEntity.getInviter(), i, false);
        b.b(parcel, 6, invitationEntity.getParticipants(), false);
        b.c(parcel, 7, invitationEntity.getVariant());
        b.c(parcel, 8, invitationEntity.getAvailableAutoMatchSlots());
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: au */
    public InvitationEntity createFromParcel(Parcel parcel) {
        ArrayList arrayListC = null;
        int iG = 0;
        int iO = a.o(parcel);
        long jI = 0;
        int iG2 = 0;
        ParticipantEntity participantEntity = null;
        int iG3 = 0;
        String strN = null;
        GameEntity gameEntity = null;
        int iG4 = 0;
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
                    jI = a.i(parcel, iN);
                    break;
                case 4:
                    iG3 = a.g(parcel, iN);
                    break;
                case 5:
                    participantEntity = (ParticipantEntity) a.a(parcel, iN, ParticipantEntity.CREATOR);
                    break;
                case 6:
                    arrayListC = a.c(parcel, iN, ParticipantEntity.CREATOR);
                    break;
                case 7:
                    iG2 = a.g(parcel, iN);
                    break;
                case 8:
                    iG = a.g(parcel, iN);
                    break;
                case 1000:
                    iG4 = a.g(parcel, iN);
                    break;
                default:
                    a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new InvitationEntity(iG4, gameEntity, strN, jI, iG3, participantEntity, arrayListC, iG2, iG);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bn, reason: merged with bridge method [inline-methods] */
    public InvitationEntity[] newArray(int i) {
        return new InvitationEntity[i];
    }
}
