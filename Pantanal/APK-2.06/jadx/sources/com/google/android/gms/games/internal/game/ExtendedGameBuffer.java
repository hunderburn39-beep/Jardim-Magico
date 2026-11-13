package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

/* JADX INFO: loaded from: classes.dex */
public final class ExtendedGameBuffer extends d<ExtendedGame> {
    public ExtendedGameBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.data.d
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public ExtendedGame c(int i, int i2) {
        return new ExtendedGameRef(this.BB, i, i2);
    }

    @Override // com.google.android.gms.common.data.d
    protected String getPrimaryDataMarkerColumn() {
        return "external_game_id";
    }
}
