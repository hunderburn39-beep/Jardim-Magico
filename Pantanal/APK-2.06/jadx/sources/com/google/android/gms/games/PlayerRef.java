package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;

/* JADX INFO: loaded from: classes.dex */
public final class PlayerRef extends b implements Player {
    private final PlayerColumnNames Ii;

    private static final class PlayerColumnNames {
        public final String Ij;
        public final String Ik;
        public final String Il;
        public final String Im;
        public final String In;
        public final String Io;
        public final String Ip;
        public final String Iq;
        public final String Ir;

        public PlayerColumnNames(String prefix) {
            if (TextUtils.isEmpty(prefix)) {
                this.Ij = "external_player_id";
                this.Ik = "profile_name";
                this.Il = "profile_icon_image_uri";
                this.Im = "profile_icon_image_url";
                this.In = "profile_hi_res_image_uri";
                this.Io = "profile_hi_res_image_url";
                this.Ip = "last_updated";
                this.Iq = "is_in_circles";
                this.Ir = "played_with_timestamp";
                return;
            }
            this.Ij = prefix + "external_player_id";
            this.Ik = prefix + "profile_name";
            this.Il = prefix + "profile_icon_image_uri";
            this.Im = prefix + "profile_icon_image_url";
            this.In = prefix + "profile_hi_res_image_uri";
            this.Io = prefix + "profile_hi_res_image_url";
            this.Ip = prefix + "last_updated";
            this.Iq = prefix + "is_in_circles";
            this.Ir = prefix + "played_with_timestamp";
        }
    }

    public PlayerRef(DataHolder holder, int dataRow) {
        this(holder, dataRow, null);
    }

    public PlayerRef(DataHolder holder, int dataRow, String prefix) {
        super(holder, dataRow);
        this.Ii = new PlayerColumnNames(prefix);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.b
    public boolean equals(Object obj) {
        return PlayerEntity.a(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public Player freeze() {
        return new PlayerEntity(this);
    }

    @Override // com.google.android.gms.games.Player
    public String getDisplayName() {
        return getString(this.Ii.Ik);
    }

    @Override // com.google.android.gms.games.Player
    public void getDisplayName(CharArrayBuffer dataOut) {
        a(this.Ii.Ik, dataOut);
    }

    @Override // com.google.android.gms.games.Player
    public Uri getHiResImageUri() {
        return ah(this.Ii.In);
    }

    @Override // com.google.android.gms.games.Player
    public String getHiResImageUrl() {
        return getString(this.Ii.Io);
    }

    @Override // com.google.android.gms.games.Player
    public Uri getIconImageUri() {
        return ah(this.Ii.Il);
    }

    @Override // com.google.android.gms.games.Player
    public String getIconImageUrl() {
        return getString(this.Ii.Im);
    }

    @Override // com.google.android.gms.games.Player
    public long getLastPlayedWithTimestamp() {
        if (hasColumn(this.Ii.Ir)) {
            return getLong(this.Ii.Ir);
        }
        return -1L;
    }

    @Override // com.google.android.gms.games.Player
    public String getPlayerId() {
        return getString(this.Ii.Ij);
    }

    @Override // com.google.android.gms.games.Player
    public long getRetrievedTimestamp() {
        return getLong(this.Ii.Ip);
    }

    @Override // com.google.android.gms.games.Player
    public int gh() {
        return getInteger(this.Ii.Iq);
    }

    @Override // com.google.android.gms.games.Player
    public boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    @Override // com.google.android.gms.games.Player
    public boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    @Override // com.google.android.gms.common.data.b
    public int hashCode() {
        return PlayerEntity.a(this);
    }

    public String toString() {
        return PlayerEntity.b(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        ((PlayerEntity) freeze()).writeToParcel(dest, flags);
    }
}
