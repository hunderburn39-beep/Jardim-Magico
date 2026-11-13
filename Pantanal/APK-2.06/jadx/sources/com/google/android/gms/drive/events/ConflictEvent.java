package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class ConflictEvent implements SafeParcelable, DriveEvent {
    public static final Parcelable.Creator<ConflictEvent> CREATOR = new b();
    final DriveId Ew;
    final int xH;

    ConflictEvent(int versionCode, DriveId driveId) {
        this.xH = versionCode;
        this.Ew = driveId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.drive.events.DriveEvent
    public DriveId getDriveId() {
        return this.Ew;
    }

    @Override // com.google.android.gms.drive.events.DriveEvent
    public int getType() {
        return 1;
    }

    public String toString() {
        return String.format("ConflictEvent [id=%s]", this.Ew);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        b.a(this, dest, flags);
    }
}
