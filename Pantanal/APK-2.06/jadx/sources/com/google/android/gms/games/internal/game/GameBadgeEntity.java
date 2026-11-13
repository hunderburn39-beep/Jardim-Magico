package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fo;

/* JADX INFO: loaded from: classes.dex */
public final class GameBadgeEntity extends GamesDowngradeableSafeParcel implements GameBadge {
    public static final GameBadgeEntityCreator CREATOR = new GameBadgeEntityCreatorCompat();
    private String EB;
    private String HD;
    private Uri HF;
    private int LF;
    private final int xH;

    static final class GameBadgeEntityCreatorCompat extends GameBadgeEntityCreator {
        GameBadgeEntityCreatorCompat() {
        }

        @Override // com.google.android.gms.games.internal.game.GameBadgeEntityCreator, android.os.Parcelable.Creator
        /* JADX INFO: renamed from: ar */
        public GameBadgeEntity createFromParcel(Parcel parcel) {
            if (GameBadgeEntity.c(GameBadgeEntity.eJ()) || GameBadgeEntity.al(GameBadgeEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            return new GameBadgeEntity(1, i, string, string2, string3 == null ? null : Uri.parse(string3));
        }
    }

    GameBadgeEntity(int versionCode, int type, String title, String description, Uri iconImageUri) {
        this.xH = versionCode;
        this.LF = type;
        this.EB = title;
        this.HD = description;
        this.HF = iconImageUri;
    }

    public GameBadgeEntity(GameBadge gameBadge) {
        this.xH = 1;
        this.LF = gameBadge.getType();
        this.EB = gameBadge.getTitle();
        this.HD = gameBadge.getDescription();
        this.HF = gameBadge.getIconImageUri();
    }

    static int a(GameBadge gameBadge) {
        return fo.hashCode(Integer.valueOf(gameBadge.getType()), gameBadge.getTitle(), gameBadge.getDescription(), gameBadge.getIconImageUri());
    }

    static boolean a(GameBadge gameBadge, Object obj) {
        if (!(obj instanceof GameBadge)) {
            return false;
        }
        if (gameBadge == obj) {
            return true;
        }
        GameBadge gameBadge2 = (GameBadge) obj;
        return fo.equal(Integer.valueOf(gameBadge2.getType()), gameBadge.getTitle()) && fo.equal(gameBadge2.getDescription(), gameBadge.getIconImageUri());
    }

    static String b(GameBadge gameBadge) {
        return fo.e(gameBadge).a("Type", Integer.valueOf(gameBadge.getType())).a("Title", gameBadge.getTitle()).a("Description", gameBadge.getDescription()).a("IconImageUri", gameBadge.getIconImageUri()).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public String getDescription() {
        return this.HD;
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public Uri getIconImageUri() {
        return this.HF;
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public String getTitle() {
        return this.EB;
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public int getType() {
        return this.LF;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: hh, reason: merged with bridge method [inline-methods] */
    public GameBadge freeze() {
        return this;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (!eK()) {
            GameBadgeEntityCreator.a(this, dest, flags);
            return;
        }
        dest.writeInt(this.LF);
        dest.writeString(this.EB);
        dest.writeString(this.HD);
        dest.writeString(this.HF == null ? null : this.HF.toString());
    }
}
