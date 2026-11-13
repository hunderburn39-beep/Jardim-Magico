package com.google.android.gms.games.internal.player;

import android.net.Uri;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.internal.fo;

/* JADX INFO: loaded from: classes.dex */
public final class ExtendedPlayerEntity implements ExtendedPlayer {
    private final PlayerEntity LH;
    private final String LI;
    private final long LJ;
    private final Uri LK;

    public ExtendedPlayerEntity(ExtendedPlayer extendedPlayer) {
        Player player = extendedPlayer.getPlayer();
        this.LH = player == null ? null : new PlayerEntity(player);
        this.LI = extendedPlayer.hu();
        this.LJ = extendedPlayer.hv();
        this.LK = extendedPlayer.hw();
    }

    static int a(ExtendedPlayer extendedPlayer) {
        return fo.hashCode(extendedPlayer.getPlayer(), extendedPlayer.hu(), Long.valueOf(extendedPlayer.hv()), extendedPlayer.hw());
    }

    static boolean a(ExtendedPlayer extendedPlayer, Object obj) {
        if (!(obj instanceof ExtendedPlayer)) {
            return false;
        }
        if (extendedPlayer == obj) {
            return true;
        }
        ExtendedPlayer extendedPlayer2 = (ExtendedPlayer) obj;
        return fo.equal(extendedPlayer2.getPlayer(), extendedPlayer.getPlayer()) && fo.equal(extendedPlayer2.hu(), extendedPlayer.hu()) && fo.equal(Long.valueOf(extendedPlayer2.hv()), Long.valueOf(extendedPlayer.hv())) && fo.equal(extendedPlayer2.hw(), extendedPlayer.hw());
    }

    static String b(ExtendedPlayer extendedPlayer) {
        return fo.e(extendedPlayer).a("Player", extendedPlayer.getPlayer()).a("MostRecentGameId", extendedPlayer.hu()).a("MostRecentActivityTimestamp", Long.valueOf(extendedPlayer.hv())).a("MostRecentGameIconImageUri", extendedPlayer.hw()).toString();
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    public int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    public String hu() {
        return this.LI;
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    public long hv() {
        return this.LJ;
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    public Uri hw() {
        return this.LK;
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    /* JADX INFO: renamed from: hx, reason: merged with bridge method [inline-methods] */
    public PlayerEntity getPlayer() {
        return this.LH;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: hy, reason: merged with bridge method [inline-methods] */
    public ExtendedPlayer freeze() {
        return this;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return b(this);
    }
}
