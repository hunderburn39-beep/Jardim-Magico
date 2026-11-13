package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

/* JADX INFO: loaded from: classes.dex */
public final class GameRequestBuffer extends d<GameRequest> {
    public GameRequestBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.data.d
    /* JADX INFO: renamed from: getEntry, reason: merged with bridge method [inline-methods] */
    public GameRequest c(int rowIndex, int numChildren) {
        return new GameRequestRef(this.BB, rowIndex, numChildren);
    }

    @Override // com.google.android.gms.common.data.d
    protected String getPrimaryDataMarkerColumn() {
        return "external_request_id";
    }
}
