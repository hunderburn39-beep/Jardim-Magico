package com.google.android.gms.games.internal.notification;

import com.google.android.gms.common.data.DataBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class GameNotificationBuffer extends DataBuffer<GameNotification> {
    @Override // com.google.android.gms.common.data.DataBuffer
    /* JADX INFO: renamed from: bj, reason: merged with bridge method [inline-methods] */
    public GameNotification get(int i) {
        return new GameNotificationRef(this.BB, i);
    }
}
