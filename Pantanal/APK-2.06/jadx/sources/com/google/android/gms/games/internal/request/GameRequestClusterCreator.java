package com.google.android.gms.games.internal.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.request.GameRequestEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class GameRequestClusterCreator implements Parcelable.Creator<GameRequestCluster> {
    static void a(GameRequestCluster gameRequestCluster, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.b(parcel, 1, gameRequestCluster.hz(), false);
        b.c(parcel, 1000, gameRequestCluster.getVersionCode());
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: at, reason: merged with bridge method [inline-methods] */
    public GameRequestCluster createFromParcel(Parcel parcel) {
        int iO = a.o(parcel);
        int iG = 0;
        ArrayList arrayListC = null;
        while (parcel.dataPosition() < iO) {
            int iN = a.n(parcel);
            switch (a.R(iN)) {
                case 1:
                    arrayListC = a.c(parcel, iN, GameRequestEntity.CREATOR);
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
        return new GameRequestCluster(iG, arrayListC);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bl, reason: merged with bridge method [inline-methods] */
    public GameRequestCluster[] newArray(int i) {
        return new GameRequestCluster[i];
    }
}
