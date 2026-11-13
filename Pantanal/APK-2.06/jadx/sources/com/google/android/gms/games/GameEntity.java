package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;

/* JADX INFO: loaded from: classes.dex */
public final class GameEntity extends GamesDowngradeableSafeParcel implements Game {
    public static final Parcelable.Creator<GameEntity> CREATOR = new GameEntityCreatorCompat();
    private final String HA;
    private final String HB;
    private final String HC;
    private final String HD;
    private final String HE;
    private final Uri HF;
    private final Uri HG;
    private final Uri HH;
    private final boolean HI;
    private final boolean HJ;
    private final String HK;
    private final int HL;
    private final int HM;
    private final int HN;
    private final boolean HO;
    private final boolean HP;
    private final String HQ;
    private final String HR;
    private final String HS;
    private final boolean HT;
    private final boolean HU;
    private final int xH;
    private final String xI;

    static final class GameEntityCreatorCompat extends GameEntityCreator {
        GameEntityCreatorCompat() {
        }

        @Override // com.google.android.gms.games.GameEntityCreator, android.os.Parcelable.Creator
        /* JADX INFO: renamed from: an */
        public GameEntity createFromParcel(Parcel parcel) {
            if (GameEntity.c(GameEntity.eJ()) || GameEntity.al(GameEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            String string6 = parcel.readString();
            String string7 = parcel.readString();
            Uri uri = string7 == null ? null : Uri.parse(string7);
            String string8 = parcel.readString();
            Uri uri2 = string8 == null ? null : Uri.parse(string8);
            String string9 = parcel.readString();
            return new GameEntity(3, string, string2, string3, string4, string5, string6, uri, uri2, string9 == null ? null : Uri.parse(string9), parcel.readInt() > 0, parcel.readInt() > 0, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), false, false, null, null, null, false, false);
        }
    }

    GameEntity(int versionCode, String applicationId, String displayName, String primaryCategory, String secondaryCategory, String description, String developerName, Uri iconImageUri, Uri hiResImageUri, Uri featuredImageUri, boolean playEnabledGame, boolean instanceInstalled, String instancePackageName, int gameplayAclStatus, int achievementTotalCount, int leaderboardCount, boolean realTimeEnabled, boolean turnBasedEnabled, String iconImageUrl, String hiResImageUrl, String featuredImageUrl, boolean muted, boolean identitySharingConfirmed) {
        this.xH = versionCode;
        this.xI = applicationId;
        this.HA = displayName;
        this.HB = primaryCategory;
        this.HC = secondaryCategory;
        this.HD = description;
        this.HE = developerName;
        this.HF = iconImageUri;
        this.HQ = iconImageUrl;
        this.HG = hiResImageUri;
        this.HR = hiResImageUrl;
        this.HH = featuredImageUri;
        this.HS = featuredImageUrl;
        this.HI = playEnabledGame;
        this.HJ = instanceInstalled;
        this.HK = instancePackageName;
        this.HL = gameplayAclStatus;
        this.HM = achievementTotalCount;
        this.HN = leaderboardCount;
        this.HO = realTimeEnabled;
        this.HP = turnBasedEnabled;
        this.HT = muted;
        this.HU = identitySharingConfirmed;
    }

    public GameEntity(Game game) {
        this.xH = 3;
        this.xI = game.getApplicationId();
        this.HB = game.getPrimaryCategory();
        this.HC = game.getSecondaryCategory();
        this.HD = game.getDescription();
        this.HE = game.getDeveloperName();
        this.HA = game.getDisplayName();
        this.HF = game.getIconImageUri();
        this.HQ = game.getIconImageUrl();
        this.HG = game.getHiResImageUri();
        this.HR = game.getHiResImageUrl();
        this.HH = game.getFeaturedImageUri();
        this.HS = game.getFeaturedImageUrl();
        this.HI = game.gb();
        this.HJ = game.gd();
        this.HK = game.ge();
        this.HL = game.gf();
        this.HM = game.getAchievementTotalCount();
        this.HN = game.getLeaderboardCount();
        this.HO = game.isRealTimeMultiplayerEnabled();
        this.HP = game.isTurnBasedMultiplayerEnabled();
        this.HT = game.isMuted();
        this.HU = game.gc();
    }

    static int a(Game game) {
        return fo.hashCode(game.getApplicationId(), game.getDisplayName(), game.getPrimaryCategory(), game.getSecondaryCategory(), game.getDescription(), game.getDeveloperName(), game.getIconImageUri(), game.getHiResImageUri(), game.getFeaturedImageUri(), Boolean.valueOf(game.gb()), Boolean.valueOf(game.gd()), game.ge(), Integer.valueOf(game.gf()), Integer.valueOf(game.getAchievementTotalCount()), Integer.valueOf(game.getLeaderboardCount()), Boolean.valueOf(game.isRealTimeMultiplayerEnabled()), Boolean.valueOf(game.isTurnBasedMultiplayerEnabled()), Boolean.valueOf(game.isMuted()), Boolean.valueOf(game.gc()));
    }

    static boolean a(Game game, Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        if (game == obj) {
            return true;
        }
        Game game2 = (Game) obj;
        if (fo.equal(game2.getApplicationId(), game.getApplicationId()) && fo.equal(game2.getDisplayName(), game.getDisplayName()) && fo.equal(game2.getPrimaryCategory(), game.getPrimaryCategory()) && fo.equal(game2.getSecondaryCategory(), game.getSecondaryCategory()) && fo.equal(game2.getDescription(), game.getDescription()) && fo.equal(game2.getDeveloperName(), game.getDeveloperName()) && fo.equal(game2.getIconImageUri(), game.getIconImageUri()) && fo.equal(game2.getHiResImageUri(), game.getHiResImageUri()) && fo.equal(game2.getFeaturedImageUri(), game.getFeaturedImageUri()) && fo.equal(Boolean.valueOf(game2.gb()), Boolean.valueOf(game.gb())) && fo.equal(Boolean.valueOf(game2.gd()), Boolean.valueOf(game.gd())) && fo.equal(game2.ge(), game.ge()) && fo.equal(Integer.valueOf(game2.gf()), Integer.valueOf(game.gf())) && fo.equal(Integer.valueOf(game2.getAchievementTotalCount()), Integer.valueOf(game.getAchievementTotalCount())) && fo.equal(Integer.valueOf(game2.getLeaderboardCount()), Integer.valueOf(game.getLeaderboardCount())) && fo.equal(Boolean.valueOf(game2.isRealTimeMultiplayerEnabled()), Boolean.valueOf(game.isRealTimeMultiplayerEnabled()))) {
            if (fo.equal(Boolean.valueOf(game2.isTurnBasedMultiplayerEnabled()), Boolean.valueOf(game.isTurnBasedMultiplayerEnabled() && fo.equal(Boolean.valueOf(game2.isMuted()), Boolean.valueOf(game.isMuted())) && fo.equal(Boolean.valueOf(game2.gc()), Boolean.valueOf(game.gc()))))) {
                return true;
            }
        }
        return false;
    }

    static String b(Game game) {
        return fo.e(game).a("ApplicationId", game.getApplicationId()).a("DisplayName", game.getDisplayName()).a("PrimaryCategory", game.getPrimaryCategory()).a("SecondaryCategory", game.getSecondaryCategory()).a("Description", game.getDescription()).a("DeveloperName", game.getDeveloperName()).a("IconImageUri", game.getIconImageUri()).a("IconImageUrl", game.getIconImageUrl()).a("HiResImageUri", game.getHiResImageUri()).a("HiResImageUrl", game.getHiResImageUrl()).a("FeaturedImageUri", game.getFeaturedImageUri()).a("FeaturedImageUrl", game.getFeaturedImageUrl()).a("PlayEnabledGame", Boolean.valueOf(game.gb())).a("InstanceInstalled", Boolean.valueOf(game.gd())).a("InstancePackageName", game.ge()).a("AchievementTotalCount", Integer.valueOf(game.getAchievementTotalCount())).a("LeaderboardCount", Integer.valueOf(game.getLeaderboardCount())).a("RealTimeMultiplayerEnabled", Boolean.valueOf(game.isRealTimeMultiplayerEnabled())).a("TurnBasedMultiplayerEnabled", Boolean.valueOf(game.isTurnBasedMultiplayerEnabled())).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public Game freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.Game
    public boolean gb() {
        return this.HI;
    }

    @Override // com.google.android.gms.games.Game
    public boolean gc() {
        return this.HU;
    }

    @Override // com.google.android.gms.games.Game
    public boolean gd() {
        return this.HJ;
    }

    @Override // com.google.android.gms.games.Game
    public String ge() {
        return this.HK;
    }

    @Override // com.google.android.gms.games.Game
    public int getAchievementTotalCount() {
        return this.HM;
    }

    @Override // com.google.android.gms.games.Game
    public String getApplicationId() {
        return this.xI;
    }

    @Override // com.google.android.gms.games.Game
    public String getDescription() {
        return this.HD;
    }

    @Override // com.google.android.gms.games.Game
    public void getDescription(CharArrayBuffer dataOut) {
        gm.b(this.HD, dataOut);
    }

    @Override // com.google.android.gms.games.Game
    public String getDeveloperName() {
        return this.HE;
    }

    @Override // com.google.android.gms.games.Game
    public void getDeveloperName(CharArrayBuffer dataOut) {
        gm.b(this.HE, dataOut);
    }

    @Override // com.google.android.gms.games.Game
    public String getDisplayName() {
        return this.HA;
    }

    @Override // com.google.android.gms.games.Game
    public void getDisplayName(CharArrayBuffer dataOut) {
        gm.b(this.HA, dataOut);
    }

    @Override // com.google.android.gms.games.Game
    public Uri getFeaturedImageUri() {
        return this.HH;
    }

    @Override // com.google.android.gms.games.Game
    public String getFeaturedImageUrl() {
        return this.HS;
    }

    @Override // com.google.android.gms.games.Game
    public Uri getHiResImageUri() {
        return this.HG;
    }

    @Override // com.google.android.gms.games.Game
    public String getHiResImageUrl() {
        return this.HR;
    }

    @Override // com.google.android.gms.games.Game
    public Uri getIconImageUri() {
        return this.HF;
    }

    @Override // com.google.android.gms.games.Game
    public String getIconImageUrl() {
        return this.HQ;
    }

    @Override // com.google.android.gms.games.Game
    public int getLeaderboardCount() {
        return this.HN;
    }

    @Override // com.google.android.gms.games.Game
    public String getPrimaryCategory() {
        return this.HB;
    }

    @Override // com.google.android.gms.games.Game
    public String getSecondaryCategory() {
        return this.HC;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.games.Game
    public int gf() {
        return this.HL;
    }

    public int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.games.Game
    public boolean isMuted() {
        return this.HT;
    }

    @Override // com.google.android.gms.games.Game
    public boolean isRealTimeMultiplayerEnabled() {
        return this.HO;
    }

    @Override // com.google.android.gms.games.Game
    public boolean isTurnBasedMultiplayerEnabled() {
        return this.HP;
    }

    public String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (!eK()) {
            GameEntityCreator.a(this, dest, flags);
            return;
        }
        dest.writeString(this.xI);
        dest.writeString(this.HA);
        dest.writeString(this.HB);
        dest.writeString(this.HC);
        dest.writeString(this.HD);
        dest.writeString(this.HE);
        dest.writeString(this.HF == null ? null : this.HF.toString());
        dest.writeString(this.HG == null ? null : this.HG.toString());
        dest.writeString(this.HH != null ? this.HH.toString() : null);
        dest.writeInt(this.HI ? 1 : 0);
        dest.writeInt(this.HJ ? 1 : 0);
        dest.writeString(this.HK);
        dest.writeInt(this.HL);
        dest.writeInt(this.HM);
        dest.writeInt(this.HN);
    }
}
