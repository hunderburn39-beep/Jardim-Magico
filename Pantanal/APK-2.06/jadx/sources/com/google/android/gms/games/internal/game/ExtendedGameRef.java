package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ExtendedGameRef extends b implements ExtendedGame {
    private final GameRef LD;
    private final int LE;

    ExtendedGameRef(DataHolder holder, int dataRow, int numChildren) {
        super(holder, dataRow);
        this.LD = new GameRef(holder, dataRow);
        this.LE = numChildren;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.b
    public boolean equals(Object obj) {
        return ExtendedGameEntity.a(this, obj);
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public ArrayList<GameBadge> gW() {
        if (this.BB.getString("badge_title", this.BD, this.BB.G(this.BD)) == null) {
            return new ArrayList<>(0);
        }
        ArrayList<GameBadge> arrayList = new ArrayList<>(this.LE);
        for (int i = 0; i < this.LE; i++) {
            arrayList.add(new GameBadgeRef(this.BB, this.BD + i));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public int gX() {
        return getInteger("availability");
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public boolean gY() {
        return getBoolean("owned");
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public int gZ() {
        return getInteger("achievement_unlocked_count");
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public Game getGame() {
        return this.LD;
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public long ha() {
        return getLong("last_played_server_time");
    }

    @Override // com.google.android.gms.common.data.b
    public int hashCode() {
        return ExtendedGameEntity.a(this);
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public long hb() {
        return getLong("price_micros");
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public String hc() {
        return getString("formatted_price");
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public long hd() {
        return getLong("full_price_micros");
    }

    @Override // com.google.android.gms.games.internal.game.ExtendedGame
    public String he() {
        return getString("formatted_full_price");
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: hg, reason: merged with bridge method [inline-methods] */
    public ExtendedGame freeze() {
        return new ExtendedGameEntity(this);
    }

    public String toString() {
        return ExtendedGameEntity.b(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        ((ExtendedGameEntity) freeze()).writeToParcel(dest, flags);
    }
}
