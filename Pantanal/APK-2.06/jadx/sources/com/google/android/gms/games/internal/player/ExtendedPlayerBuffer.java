package com.google.android.gms.games.internal.player;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class ExtendedPlayerBuffer extends DataBuffer<ExtendedPlayer> {
    public ExtendedPlayerBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    /* JADX INFO: renamed from: bk, reason: merged with bridge method [inline-methods] */
    public ExtendedPlayer get(int i) {
        return new ExtendedPlayerRef(this.BB, i);
    }
}
