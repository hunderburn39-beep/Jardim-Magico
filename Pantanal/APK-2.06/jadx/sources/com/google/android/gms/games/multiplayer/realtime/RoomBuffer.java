package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

/* JADX INFO: loaded from: classes.dex */
public final class RoomBuffer extends d<Room> {
    public RoomBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.data.d
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public Room c(int i, int i2) {
        return new RoomRef(this.BB, i, i2);
    }

    @Override // com.google.android.gms.common.data.d
    protected String getPrimaryDataMarkerColumn() {
        return "external_match_id";
    }
}
