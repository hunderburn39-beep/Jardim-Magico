package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fo;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class ExtendedGameEntity extends GamesDowngradeableSafeParcel implements ExtendedGame {
    public static final ExtendedGameEntityCreator CREATOR = new ExtendedGameEntityCreatorCompat();
    private final long LA;
    private final String LB;
    private final ArrayList<GameBadgeEntity> LC;
    private final GameEntity Lt;
    private final int Lu;
    private final boolean Lv;
    private final int Lw;
    private final long Lx;
    private final long Ly;
    private final String Lz;
    private final int xH;

    static final class ExtendedGameEntityCreatorCompat extends ExtendedGameEntityCreator {
        ExtendedGameEntityCreatorCompat() {
        }

        @Override // com.google.android.gms.games.internal.game.ExtendedGameEntityCreator, android.os.Parcelable.Creator
        /* JADX INFO: renamed from: aq, reason: merged with bridge method [inline-methods] */
        public ExtendedGameEntity createFromParcel(Parcel parcel) {
            if (ExtendedGameEntity.c(ExtendedGameEntity.eJ()) || ExtendedGameEntity.al(ExtendedGameEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            GameEntity gameEntityCreateFromParcel = GameEntity.CREATOR.createFromParcel(parcel);
            int i = parcel.readInt();
            boolean z = parcel.readInt() == 1;
            int i2 = parcel.readInt();
            long j = parcel.readLong();
            long j2 = parcel.readLong();
            String string = parcel.readString();
            long j3 = parcel.readLong();
            String string2 = parcel.readString();
            int i3 = parcel.readInt();
            ArrayList arrayList = new ArrayList(i3);
            for (int i4 = 0; i4 < i3; i4++) {
                arrayList.add(GameBadgeEntity.CREATOR.createFromParcel(parcel));
            }
            return new ExtendedGameEntity(1, gameEntityCreateFromParcel, i, z, i2, j, j2, string, j3, string2, arrayList);
        }
    }

    ExtendedGameEntity(int versionCode, GameEntity game, int availability, boolean owned, int achievementUnlockedCount, long lastPlayedServerTimestamp, long priceMicros, String formattedPrice, long fullPriceMicros, String formattedFullPrice, ArrayList<GameBadgeEntity> badges) {
        this.xH = versionCode;
        this.Lt = game;
        this.Lu = availability;
        this.Lv = owned;
        this.Lw = achievementUnlockedCount;
        this.Lx = lastPlayedServerTimestamp;
        this.Ly = priceMicros;
        this.Lz = formattedPrice;
        this.LA = fullPriceMicros;
        this.LB = formattedFullPrice;
        this.LC = badges;
    }

    public ExtendedGameEntity(ExtendedGame extendedGame) {
        this.xH = 1;
        Game game = extendedGame.getGame();
        this.Lt = game == null ? null : new GameEntity(game);
        this.Lu = extendedGame.gX();
        this.Lv = extendedGame.gY();
        this.Lw = extendedGame.gZ();
        this.Lx = extendedGame.ha();
        this.Ly = extendedGame.hb();
        this.Lz = extendedGame.hc();
        this.LA = extendedGame.hd();
        this.LB = extendedGame.he();
        ArrayList<GameBadge> arrayListGW = extendedGame.gW();
        int size = arrayListGW.size();
        this.LC = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.LC.add((GameBadgeEntity) arrayListGW.get(i).freeze());
        }
    }

    static int a(ExtendedGame extendedGame) {
        return fo.hashCode(extendedGame.getGame(), Integer.valueOf(extendedGame.gX()), Boolean.valueOf(extendedGame.gY()), Integer.valueOf(extendedGame.gZ()), Long.valueOf(extendedGame.ha()), Long.valueOf(extendedGame.hb()), extendedGame.hc(), Long.valueOf(extendedGame.hd()), extendedGame.he());
    }

    static boolean a(ExtendedGame extendedGame, Object obj) {
        if (!(obj instanceof ExtendedGame)) {
            return false;
        }
        if (extendedGame == obj) {
            return true;
        }
        ExtendedGame extendedGame2 = (ExtendedGame) obj;
        return fo.equal(extendedGame2.getGame(), extendedGame.getGame()) && fo.equal(Integer.valueOf(extendedGame2.gX()), Integer.valueOf(extendedGame.gX())) && fo.equal(Boolean.valueOf(extendedGame2.gY()), Boolean.valueOf(extendedGame.gY())) && fo.equal(Integer.valueOf(extendedGame2.gZ()), Integer.valueOf(extendedGame.gZ())) && fo.equal(Long.valueOf(extendedGame2.ha()), Long.valueOf(extendedGame.ha())) && fo.equal(Long.valueOf(extendedGame2.hb()), Long.valueOf(extendedGame.hb())) && fo.equal(extendedGame2.hc(), extendedGame.hc()) && fo.equal(Long.valueOf(extendedGame2.hd()), Long.valueOf(extendedGame.hd())) && fo.equal(extendedGame2.he(), extendedGame.he());
    }

    static String b(ExtendedGame extendedGame) {
        return fo.e(extendedGame).a("Game", extendedGame.getGame()).a("Availability", Integer.valueOf(extendedGame.gX())).a("Owned", Boolean.valueOf(extendedGame.gY())).a("AchievementUnlockedCount", Integer.valueOf(extendedGame.gZ())).a("LastPlayedServerTimestamp", Long.valueOf(extendedGame.ha())).a("PriceMicros", Long.valueOf(extendedGame.hb())).a("FormattedPrice", extendedGame.hc()).a("FullPriceMicros", Long.valueOf(extendedGame.hd())).a("FormattedFullPrice", extendedGame.he()).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public ArrayList<GameBadge> gW() {
        return new ArrayList<>(this.LC);
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public int gX() {
        return this.Lu;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public boolean gY() {
        return this.Lv;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public int gZ() {
        return this.Lw;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public long ha() {
        return this.Lx;
    }

    public int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public long hb() {
        return this.Ly;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public String hc() {
        return this.Lz;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public long hd() {
        return this.LA;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public String he() {
        return this.LB;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    /* JADX INFO: renamed from: hf, reason: merged with bridge method [inline-methods] */
    public GameEntity getGame() {
        return this.Lt;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: hg, reason: merged with bridge method [inline-methods] */
    public ExtendedGame freeze() {
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
            ExtendedGameEntityCreator.a(this, dest, flags);
            return;
        }
        this.Lt.writeToParcel(dest, flags);
        dest.writeInt(this.Lu);
        dest.writeInt(this.Lv ? 1 : 0);
        dest.writeInt(this.Lw);
        dest.writeLong(this.Lx);
        dest.writeLong(this.Ly);
        dest.writeString(this.Lz);
        dest.writeLong(this.LA);
        dest.writeString(this.LB);
        int size = this.LC.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            this.LC.get(i).writeToParcel(dest, flags);
        }
    }
}
