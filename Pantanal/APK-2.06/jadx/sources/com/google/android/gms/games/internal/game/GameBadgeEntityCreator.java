package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

/* JADX INFO: loaded from: classes.dex */
public class GameBadgeEntityCreator implements Parcelable.Creator<GameBadgeEntity> {
    static void a(GameBadgeEntity gameBadgeEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.c(parcel, 1, gameBadgeEntity.getType());
        b.c(parcel, 1000, gameBadgeEntity.getVersionCode());
        b.a(parcel, 2, gameBadgeEntity.getTitle(), false);
        b.a(parcel, 3, gameBadgeEntity.getDescription(), false);
        b.a(parcel, 4, (Parcelable) gameBadgeEntity.getIconImageUri(), i, false);
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ar, reason: merged with bridge method [inline-methods] */
    public GameBadgeEntity createFromParcel(Parcel parcel) {
        int iG = 0;
        Uri uri = null;
        int iO = a.o(parcel);
        String strN = null;
        String strN2 = null;
        int iG2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = a.n(parcel);
            switch (a.R(iN)) {
                case 1:
                    iG = a.g(parcel, iN);
                    break;
                case 2:
                    strN2 = a.n(parcel, iN);
                    break;
                case 3:
                    strN = a.n(parcel, iN);
                    break;
                case 4:
                    uri = (Uri) a.a(parcel, iN, Uri.CREATOR);
                    break;
                case 1000:
                    iG2 = a.g(parcel, iN);
                    break;
                default:
                    a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new GameBadgeEntity(iG2, iG, strN2, strN, uri);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: bg, reason: merged with bridge method [inline-methods] */
    public GameBadgeEntity[] newArray(int i) {
        return new GameBadgeEntity[i];
    }
}
