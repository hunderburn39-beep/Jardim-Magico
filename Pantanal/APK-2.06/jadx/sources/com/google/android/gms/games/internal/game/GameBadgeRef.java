package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;

/* JADX INFO: loaded from: classes.dex */
public final class GameBadgeRef extends b implements GameBadge {
    GameBadgeRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.b
    public boolean equals(Object obj) {
        return GameBadgeEntity.a(this, obj);
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public String getDescription() {
        return getString("badge_description");
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public Uri getIconImageUri() {
        return ah("badge_icon_image_uri");
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public String getTitle() {
        return getString("badge_title");
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public int getType() {
        return getInteger("badge_type");
    }

    @Override // com.google.android.gms.common.data.b
    public int hashCode() {
        return GameBadgeEntity.a(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: hh, reason: merged with bridge method [inline-methods] */
    public GameBadge freeze() {
        return new GameBadgeEntity(this);
    }

    public String toString() {
        return GameBadgeEntity.b(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        ((GameBadgeEntity) freeze()).writeToParcel(dest, flags);
    }
}
