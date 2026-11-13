package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class GameRequestSummaryBuffer extends DataBuffer<GameRequestSummary> {
    @Override // com.google.android.gms.common.data.DataBuffer
    /* JADX INFO: renamed from: br, reason: merged with bridge method [inline-methods] */
    public GameRequestSummary get(int i) {
        return new GameRequestSummaryRef(this.BB, i);
    }
}
