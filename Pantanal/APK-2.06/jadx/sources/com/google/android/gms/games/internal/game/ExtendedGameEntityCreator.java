package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ExtendedGameEntityCreator implements Parcelable.Creator<ExtendedGameEntity> {
    static void a(ExtendedGameEntity extendedGameEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, (Parcelable) extendedGameEntity.getGame(), i, false);
        b.c(parcel, 1000, extendedGameEntity.getVersionCode());
        b.c(parcel, 2, extendedGameEntity.gX());
        b.a(parcel, 3, extendedGameEntity.gY());
        b.c(parcel, 4, extendedGameEntity.gZ());
        b.a(parcel, 5, extendedGameEntity.ha());
        b.a(parcel, 6, extendedGameEntity.hb());
        b.a(parcel, 7, extendedGameEntity.hc(), false);
        b.a(parcel, 8, extendedGameEntity.hd());
        b.a(parcel, 9, extendedGameEntity.he(), false);
        b.b(parcel, 10, extendedGameEntity.gW(), false);
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aq */
    public ExtendedGameEntity createFromParcel(Parcel parcel) {
        int iO = a.o(parcel);
        int iG = 0;
        GameEntity gameEntity = null;
        int iG2 = 0;
        boolean zC = false;
        int iG3 = 0;
        long jI = 0;
        long jI2 = 0;
        String strN = null;
        long jI3 = 0;
        String strN2 = null;
        ArrayList arrayListC = null;
        while (parcel.dataPosition() < iO) {
            int iN = a.n(parcel);
            switch (a.R(iN)) {
                case 1:
                    gameEntity = (GameEntity) a.a(parcel, iN, GameEntity.CREATOR);
                    break;
                case 2:
                    iG2 = a.g(parcel, iN);
                    break;
                case 3:
                    zC = a.c(parcel, iN);
                    break;
                case 4:
                    iG3 = a.g(parcel, iN);
                    break;
                case 5:
                    jI = a.i(parcel, iN);
                    break;
                case 6:
                    jI2 = a.i(parcel, iN);
                    break;
                case 7:
                    strN = a.n(parcel, iN);
                    break;
                case 8:
                    jI3 = a.i(parcel, iN);
                    break;
                case 9:
                    strN2 = a.n(parcel, iN);
                    break;
                case 10:
                    arrayListC = a.c(parcel, iN, GameBadgeEntity.CREATOR);
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
        return new ExtendedGameEntity(iG, gameEntity, iG2, zC, iG3, jI, jI2, strN, jI3, strN2, arrayListC);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: be, reason: merged with bridge method [inline-methods] */
    public ExtendedGameEntity[] newArray(int i) {
        return new ExtendedGameEntity[i];
    }
}
