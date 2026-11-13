package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class RoomEntityCreator implements Parcelable.Creator<RoomEntity> {
    static void a(RoomEntity roomEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, roomEntity.getRoomId(), false);
        b.c(parcel, 1000, roomEntity.getVersionCode());
        b.a(parcel, 2, roomEntity.getCreatorId(), false);
        b.a(parcel, 3, roomEntity.getCreationTimestamp());
        b.c(parcel, 4, roomEntity.getStatus());
        b.a(parcel, 5, roomEntity.getDescription(), false);
        b.c(parcel, 6, roomEntity.getVariant());
        b.a(parcel, 7, roomEntity.getAutoMatchCriteria(), false);
        b.b(parcel, 8, roomEntity.getParticipants(), false);
        b.c(parcel, 9, roomEntity.getAutoMatchWaitEstimateSeconds());
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ax */
    public RoomEntity createFromParcel(Parcel parcel) {
        int iG = 0;
        ArrayList arrayListC = null;
        int iO = a.o(parcel);
        long jI = 0;
        Bundle bundleP = null;
        int iG2 = 0;
        String strN = null;
        int iG3 = 0;
        String strN2 = null;
        String strN3 = null;
        int iG4 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = a.n(parcel);
            switch (a.R(iN)) {
                case 1:
                    strN3 = a.n(parcel, iN);
                    break;
                case 2:
                    strN2 = a.n(parcel, iN);
                    break;
                case 3:
                    jI = a.i(parcel, iN);
                    break;
                case 4:
                    iG3 = a.g(parcel, iN);
                    break;
                case 5:
                    strN = a.n(parcel, iN);
                    break;
                case 6:
                    iG2 = a.g(parcel, iN);
                    break;
                case 7:
                    bundleP = a.p(parcel, iN);
                    break;
                case 8:
                    arrayListC = a.c(parcel, iN, ParticipantEntity.CREATOR);
                    break;
                case 9:
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
        return new RoomEntity(iG4, strN3, strN2, jI, iG3, strN, iG2, bundleP, arrayListC, iG);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bq, reason: merged with bridge method [inline-methods] */
    public RoomEntity[] newArray(int i) {
        return new RoomEntity[i];
    }
}
