package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

/* JADX INFO: loaded from: classes.dex */
public final class InvitationBuffer extends d<Invitation> {
    public InvitationBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.data.d
    /* JADX INFO: renamed from: getEntry, reason: merged with bridge method [inline-methods] */
    public Invitation c(int rowIndex, int numChildren) {
        return new InvitationRef(this.BB, rowIndex, numChildren);
    }

    @Override // com.google.android.gms.common.data.d
    protected String getPrimaryDataMarkerColumn() {
        return "external_invitation_id";
    }
}
