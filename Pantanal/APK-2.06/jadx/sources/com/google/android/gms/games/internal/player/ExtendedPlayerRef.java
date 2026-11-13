package com.google.android.gms.games.internal.player;

import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;

/* JADX INFO: loaded from: classes.dex */
public final class ExtendedPlayerRef extends b implements ExtendedPlayer {
    private final PlayerRef LL;

    ExtendedPlayerRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
        this.LL = new PlayerRef(holder, dataRow);
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    public Player getPlayer() {
        return this.LL;
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    public String hu() {
        return getString("most_recent_external_game_id");
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    public long hv() {
        return getLong("most_recent_activity_timestamp");
    }

    @Override // com.google.android.gms.games.internal.player.ExtendedPlayer
    public Uri hw() {
        return ah("game_icon_image_uri");
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: hy, reason: merged with bridge method [inline-methods] */
    public ExtendedPlayer freeze() {
        return new ExtendedPlayerEntity(this);
    }
}
