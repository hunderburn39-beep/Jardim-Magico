package com.google.android.gms.games.internal.player;

import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Player;

/* JADX INFO: loaded from: classes.dex */
public interface ExtendedPlayer extends Freezable<ExtendedPlayer> {
    Player getPlayer();

    String hu();

    long hv();

    Uri hw();
}
