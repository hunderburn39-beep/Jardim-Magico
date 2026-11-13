package com.google.android.gms.games.internal.request;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestEntity;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class GameRequestCluster implements SafeParcelable, GameRequest {
    public static final GameRequestClusterCreator CREATOR = new GameRequestClusterCreator();
    private final ArrayList<GameRequestEntity> LM;
    private final int xH;

    GameRequestCluster(int versionCode, ArrayList<GameRequestEntity> requestList) {
        this.xH = versionCode;
        this.LM = requestList;
        hn();
    }

    private void hn() {
        fb.x(!this.LM.isEmpty());
        GameRequestEntity gameRequestEntity = this.LM.get(0);
        int size = this.LM.size();
        for (int i = 1; i < size; i++) {
            GameRequestEntity gameRequestEntity2 = this.LM.get(i);
            fb.a(gameRequestEntity.getType() == gameRequestEntity2.getType(), "All the requests must be of the same type");
            fb.a(gameRequestEntity.getSender().equals(gameRequestEntity2.getSender()), "All the requests must be from the same sender");
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GameRequestCluster)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GameRequestCluster gameRequestCluster = (GameRequestCluster) obj;
        if (gameRequestCluster.LM.size() != this.LM.size()) {
            return false;
        }
        int size = this.LM.size();
        for (int i = 0; i < size; i++) {
            if (!this.LM.get(i).equals(gameRequestCluster.LM.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public GameRequest freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public long getCreationTimestamp() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public byte[] getData() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public long getExpirationTimestamp() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public Game getGame() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public int getRecipientStatus(String playerId) {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public String getRequestId() {
        return this.LM.get(0).getRequestId();
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public Player getSender() {
        return this.LM.get(0).getSender();
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public int getStatus() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public int getType() {
        return this.LM.get(0).getType();
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    /* JADX INFO: renamed from: hA, reason: merged with bridge method [inline-methods] */
    public ArrayList<Player> getRecipients() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    public int hashCode() {
        return fo.hashCode(this.LM.toArray());
    }

    public ArrayList<GameRequest> hz() {
        return new ArrayList<>(this.LM);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public boolean isConsumed(String playerId) {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        GameRequestClusterCreator.a(this, dest, flags);
    }
}
