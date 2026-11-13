package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class LeaderboardScoreBuffer extends DataBuffer<LeaderboardScore> {
    private final LeaderboardScoreBufferHeader LT;

    public LeaderboardScoreBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.LT = new LeaderboardScoreBufferHeader(dataHolder.getMetadata());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.DataBuffer
    public LeaderboardScore get(int position) {
        return new LeaderboardScoreRef(this.BB, position);
    }

    public LeaderboardScoreBufferHeader hD() {
        return this.LT;
    }
}
