package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.TurnBasedMatchTurnStatus;
import com.google.android.gms.games.multiplayer.InvitationBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class LoadMatchesResponse {
    private final InvitationBuffer MV;
    private final TurnBasedMatchBuffer MW;
    private final TurnBasedMatchBuffer MX;
    private final TurnBasedMatchBuffer MY;

    public LoadMatchesResponse(Bundle matchData) {
        DataHolder dataHolderA = a(matchData, 0);
        if (dataHolderA != null) {
            this.MV = new InvitationBuffer(dataHolderA);
        } else {
            this.MV = null;
        }
        DataHolder dataHolderA2 = a(matchData, 1);
        if (dataHolderA2 != null) {
            this.MW = new TurnBasedMatchBuffer(dataHolderA2);
        } else {
            this.MW = null;
        }
        DataHolder dataHolderA3 = a(matchData, 2);
        if (dataHolderA3 != null) {
            this.MX = new TurnBasedMatchBuffer(dataHolderA3);
        } else {
            this.MX = null;
        }
        DataHolder dataHolderA4 = a(matchData, 3);
        if (dataHolderA4 != null) {
            this.MY = new TurnBasedMatchBuffer(dataHolderA4);
        } else {
            this.MY = null;
        }
    }

    private static DataHolder a(Bundle bundle, int i) {
        String strBd = TurnBasedMatchTurnStatus.bd(i);
        if (bundle.containsKey(strBd)) {
            return (DataHolder) bundle.getParcelable(strBd);
        }
        return null;
    }

    public void close() {
        if (this.MV != null) {
            this.MV.close();
        }
        if (this.MW != null) {
            this.MW.close();
        }
        if (this.MX != null) {
            this.MX.close();
        }
        if (this.MY != null) {
            this.MY.close();
        }
    }

    public TurnBasedMatchBuffer getCompletedMatches() {
        return this.MY;
    }

    public InvitationBuffer getInvitations() {
        return this.MV;
    }

    public TurnBasedMatchBuffer getMyTurnMatches() {
        return this.MW;
    }

    public TurnBasedMatchBuffer getTheirTurnMatches() {
        return this.MX;
    }

    public boolean hasData() {
        if (this.MV != null && this.MV.getCount() > 0) {
            return true;
        }
        if (this.MW != null && this.MW.getCount() > 0) {
            return true;
        }
        if (this.MX == null || this.MX.getCount() <= 0) {
            return this.MY != null && this.MY.getCount() > 0;
        }
        return true;
    }
}
