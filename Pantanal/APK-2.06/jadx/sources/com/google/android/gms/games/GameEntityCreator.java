package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public class GameEntityCreator implements Parcelable.Creator<GameEntity> {
    static void a(GameEntity gameEntity, Parcel parcel, int i) {
        int iP = b.p(parcel);
        b.a(parcel, 1, gameEntity.getApplicationId(), false);
        b.a(parcel, 2, gameEntity.getDisplayName(), false);
        b.a(parcel, 3, gameEntity.getPrimaryCategory(), false);
        b.a(parcel, 4, gameEntity.getSecondaryCategory(), false);
        b.a(parcel, 5, gameEntity.getDescription(), false);
        b.a(parcel, 6, gameEntity.getDeveloperName(), false);
        b.a(parcel, 7, (Parcelable) gameEntity.getIconImageUri(), i, false);
        b.a(parcel, 8, (Parcelable) gameEntity.getHiResImageUri(), i, false);
        b.a(parcel, 9, (Parcelable) gameEntity.getFeaturedImageUri(), i, false);
        b.a(parcel, 10, gameEntity.gb());
        b.a(parcel, 11, gameEntity.gd());
        b.a(parcel, 12, gameEntity.ge(), false);
        b.c(parcel, 13, gameEntity.gf());
        b.c(parcel, 14, gameEntity.getAchievementTotalCount());
        b.c(parcel, 15, gameEntity.getLeaderboardCount());
        b.a(parcel, 17, gameEntity.isTurnBasedMultiplayerEnabled());
        b.a(parcel, 16, gameEntity.isRealTimeMultiplayerEnabled());
        b.c(parcel, 1000, gameEntity.getVersionCode());
        b.a(parcel, 19, gameEntity.getHiResImageUrl(), false);
        b.a(parcel, 18, gameEntity.getIconImageUrl(), false);
        b.a(parcel, 21, gameEntity.isMuted());
        b.a(parcel, 20, gameEntity.getFeaturedImageUrl(), false);
        b.a(parcel, 22, gameEntity.gc());
        b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aS, reason: merged with bridge method [inline-methods] */
    public GameEntity[] newArray(int i) {
        return new GameEntity[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: an, reason: merged with bridge method [inline-methods] */
    public GameEntity createFromParcel(Parcel parcel) {
        int iO = a.o(parcel);
        int iG = 0;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        boolean zC = false;
        boolean zC2 = false;
        String strN7 = null;
        int iG2 = 0;
        int iG3 = 0;
        int iG4 = 0;
        boolean zC3 = false;
        boolean zC4 = false;
        String strN8 = null;
        String strN9 = null;
        String strN10 = null;
        boolean zC5 = false;
        boolean zC6 = false;
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
                    strN3 = a.n(parcel, iN);
                    break;
                case 4:
                    strN4 = a.n(parcel, iN);
                    break;
                case 5:
                    strN5 = a.n(parcel, iN);
                    break;
                case 6:
                    strN6 = a.n(parcel, iN);
                    break;
                case 7:
                    uri = (Uri) a.a(parcel, iN, Uri.CREATOR);
                    break;
                case 8:
                    uri2 = (Uri) a.a(parcel, iN, Uri.CREATOR);
                    break;
                case 9:
                    uri3 = (Uri) a.a(parcel, iN, Uri.CREATOR);
                    break;
                case 10:
                    zC = a.c(parcel, iN);
                    break;
                case 11:
                    zC2 = a.c(parcel, iN);
                    break;
                case 12:
                    strN7 = a.n(parcel, iN);
                    break;
                case 13:
                    iG2 = a.g(parcel, iN);
                    break;
                case 14:
                    iG3 = a.g(parcel, iN);
                    break;
                case 15:
                    iG4 = a.g(parcel, iN);
                    break;
                case 16:
                    zC3 = a.c(parcel, iN);
                    break;
                case 17:
                    zC4 = a.c(parcel, iN);
                    break;
                case 18:
                    strN8 = a.n(parcel, iN);
                    break;
                case 19:
                    strN9 = a.n(parcel, iN);
                    break;
                case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                    strN10 = a.n(parcel, iN);
                    break;
                case 21:
                    zC5 = a.c(parcel, iN);
                    break;
                case 22:
                    zC6 = a.c(parcel, iN);
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
        return new GameEntity(iG, strN, strN2, strN3, strN4, strN5, strN6, uri, uri2, uri3, zC, zC2, strN7, iG2, iG3, iG4, zC3, zC4, strN8, strN9, strN10, zC5, zC6);
    }
}
