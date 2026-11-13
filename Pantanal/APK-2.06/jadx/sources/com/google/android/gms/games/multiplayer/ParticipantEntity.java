package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantEntity extends GamesDowngradeableSafeParcel implements Participant {
    public static final Parcelable.Creator<ParticipantEntity> CREATOR = new ParticipantEntityCreatorCompat();
    private final String HA;
    private final Uri HF;
    private final Uri HG;
    private final String HQ;
    private final String HR;
    private final String Is;
    private final String Jg;
    private final PlayerEntity LH;
    private final int MB;
    private final boolean MC;
    private final int MD;
    private final ParticipantResult ME;
    private final int xH;

    static final class ParticipantEntityCreatorCompat extends ParticipantEntityCreator {
        ParticipantEntityCreatorCompat() {
        }

        @Override // com.google.android.gms.games.multiplayer.ParticipantEntityCreator, android.os.Parcelable.Creator
        /* JADX INFO: renamed from: av, reason: merged with bridge method [inline-methods] */
        public ParticipantEntity createFromParcel(Parcel parcel) {
            if (ParticipantEntity.c(ParticipantEntity.eJ()) || ParticipantEntity.al(ParticipantEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            Uri uri = string3 == null ? null : Uri.parse(string3);
            String string4 = parcel.readString();
            return new ParticipantEntity(3, string, string2, uri, string4 == null ? null : Uri.parse(string4), parcel.readInt(), parcel.readString(), parcel.readInt() > 0, parcel.readInt() > 0 ? PlayerEntity.CREATOR.createFromParcel(parcel) : null, 7, null, null, null);
        }
    }

    ParticipantEntity(int versionCode, String participantId, String displayName, Uri iconImageUri, Uri hiResImageUri, int status, String clientAddress, boolean connectedToRoom, PlayerEntity player, int capabilities, ParticipantResult result, String iconImageUrl, String hiResImageUrl) {
        this.xH = versionCode;
        this.Jg = participantId;
        this.HA = displayName;
        this.HF = iconImageUri;
        this.HG = hiResImageUri;
        this.MB = status;
        this.Is = clientAddress;
        this.MC = connectedToRoom;
        this.LH = player;
        this.MD = capabilities;
        this.ME = result;
        this.HQ = iconImageUrl;
        this.HR = hiResImageUrl;
    }

    public ParticipantEntity(Participant participant) {
        this.xH = 3;
        this.Jg = participant.getParticipantId();
        this.HA = participant.getDisplayName();
        this.HF = participant.getIconImageUri();
        this.HG = participant.getHiResImageUri();
        this.MB = participant.getStatus();
        this.Is = participant.gi();
        this.MC = participant.isConnectedToRoom();
        Player player = participant.getPlayer();
        this.LH = player == null ? null : new PlayerEntity(player);
        this.MD = participant.getCapabilities();
        this.ME = participant.getResult();
        this.HQ = participant.getIconImageUrl();
        this.HR = participant.getHiResImageUrl();
    }

    static int a(Participant participant) {
        return fo.hashCode(participant.getPlayer(), Integer.valueOf(participant.getStatus()), participant.gi(), Boolean.valueOf(participant.isConnectedToRoom()), participant.getDisplayName(), participant.getIconImageUri(), participant.getHiResImageUri(), Integer.valueOf(participant.getCapabilities()), participant.getResult(), participant.getParticipantId());
    }

    static boolean a(Participant participant, Object obj) {
        if (!(obj instanceof Participant)) {
            return false;
        }
        if (participant == obj) {
            return true;
        }
        Participant participant2 = (Participant) obj;
        return fo.equal(participant2.getPlayer(), participant.getPlayer()) && fo.equal(Integer.valueOf(participant2.getStatus()), Integer.valueOf(participant.getStatus())) && fo.equal(participant2.gi(), participant.gi()) && fo.equal(Boolean.valueOf(participant2.isConnectedToRoom()), Boolean.valueOf(participant.isConnectedToRoom())) && fo.equal(participant2.getDisplayName(), participant.getDisplayName()) && fo.equal(participant2.getIconImageUri(), participant.getIconImageUri()) && fo.equal(participant2.getHiResImageUri(), participant.getHiResImageUri()) && fo.equal(Integer.valueOf(participant2.getCapabilities()), Integer.valueOf(participant.getCapabilities())) && fo.equal(participant2.getResult(), participant.getResult()) && fo.equal(participant2.getParticipantId(), participant.getParticipantId());
    }

    static String b(Participant participant) {
        return fo.e(participant).a("ParticipantId", participant.getParticipantId()).a("Player", participant.getPlayer()).a("Status", Integer.valueOf(participant.getStatus())).a("ClientAddress", participant.gi()).a("ConnectedToRoom", Boolean.valueOf(participant.isConnectedToRoom())).a("DisplayName", participant.getDisplayName()).a("IconImage", participant.getIconImageUri()).a("IconImageUrl", participant.getIconImageUrl()).a("HiResImage", participant.getHiResImageUri()).a("HiResImageUrl", participant.getHiResImageUrl()).a("Capabilities", Integer.valueOf(participant.getCapabilities())).a("Result", participant.getResult()).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public Participant freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public int getCapabilities() {
        return this.MD;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public String getDisplayName() {
        return this.LH == null ? this.HA : this.LH.getDisplayName();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public void getDisplayName(CharArrayBuffer dataOut) {
        if (this.LH == null) {
            gm.b(this.HA, dataOut);
        } else {
            this.LH.getDisplayName(dataOut);
        }
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public Uri getHiResImageUri() {
        return this.LH == null ? this.HG : this.LH.getHiResImageUri();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public String getHiResImageUrl() {
        return this.LH == null ? this.HR : this.LH.getHiResImageUrl();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public Uri getIconImageUri() {
        return this.LH == null ? this.HF : this.LH.getIconImageUri();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public String getIconImageUrl() {
        return this.LH == null ? this.HQ : this.LH.getIconImageUrl();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public String getParticipantId() {
        return this.Jg;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public Player getPlayer() {
        return this.LH;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public ParticipantResult getResult() {
        return this.ME;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public int getStatus() {
        return this.MB;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public String gi() {
        return this.Is;
    }

    public int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public boolean isConnectedToRoom() {
        return this.MC;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return b(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (!eK()) {
            ParticipantEntityCreator.a(this, dest, flags);
            return;
        }
        dest.writeString(this.Jg);
        dest.writeString(this.HA);
        dest.writeString(this.HF == null ? null : this.HF.toString());
        dest.writeString(this.HG != null ? this.HG.toString() : null);
        dest.writeInt(this.MB);
        dest.writeString(this.Is);
        dest.writeInt(this.MC ? 1 : 0);
        dest.writeInt(this.LH != null ? 1 : 0);
        if (this.LH != null) {
            this.LH.writeToParcel(dest, flags);
        }
    }
}
