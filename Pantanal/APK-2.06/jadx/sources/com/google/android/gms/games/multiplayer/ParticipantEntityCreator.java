package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.PlayerEntity;

/* JADX INFO: loaded from: classes.dex */
public class ParticipantEntityCreator implements Parcelable.Creator<ParticipantEntity> {
    static void a(ParticipantEntity participantEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, participantEntity.getParticipantId(), false);
        b.a(parcel, 2, participantEntity.getDisplayName(), false);
        b.a(parcel, 3, (Parcelable) participantEntity.getIconImageUri(), i, false);
        b.a(parcel, 4, (Parcelable) participantEntity.getHiResImageUri(), i, false);
        b.c(parcel, 5, participantEntity.getStatus());
        b.a(parcel, 6, participantEntity.gi(), false);
        b.a(parcel, 7, participantEntity.isConnectedToRoom());
        b.a(parcel, 8, (Parcelable) participantEntity.getPlayer(), i, false);
        b.c(parcel, 9, participantEntity.getCapabilities());
        b.a(parcel, 10, (Parcelable) participantEntity.getResult(), i, false);
        b.a(parcel, 11, participantEntity.getIconImageUrl(), false);
        b.a(parcel, 12, participantEntity.getHiResImageUrl(), false);
        b.c(parcel, 1000, participantEntity.getVersionCode());
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: av */
    public ParticipantEntity createFromParcel(Parcel parcel) {
        int iO = a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        Uri uri = null;
        Uri uri2 = null;
        int iG2 = 0;
        String strN3 = null;
        boolean zC = false;
        PlayerEntity playerEntity = null;
        int iG3 = 0;
        ParticipantResult participantResult = null;
        String strN4 = null;
        String strN5 = null;
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
                    iG2 = a.g(parcel, iN);
                    break;
                case 6:
                    strN3 = a.n(parcel, iN);
                    break;
                case 7:
                    zC = a.c(parcel, iN);
                    break;
                case 8:
                    playerEntity = (PlayerEntity) a.a(parcel, iN, PlayerEntity.CREATOR);
                    break;
                case 9:
                    iG3 = a.g(parcel, iN);
                    break;
                case 10:
                    participantResult = (ParticipantResult) a.a(parcel, iN, ParticipantResult.CREATOR);
                    break;
                case 11:
                    strN4 = a.n(parcel, iN);
                    break;
                case 12:
                    strN5 = a.n(parcel, iN);
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
        return new ParticipantEntity(iG, strN, strN2, uri, uri2, iG2, strN3, zC, playerEntity, iG3, participantResult, strN4, strN5);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bo, reason: merged with bridge method [inline-methods] */
    public ParticipantEntity[] newArray(int i) {
        return new ParticipantEntity[i];
    }
}
