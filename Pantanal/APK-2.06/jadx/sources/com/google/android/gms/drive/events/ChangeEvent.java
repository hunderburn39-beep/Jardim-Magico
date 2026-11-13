package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class ChangeEvent implements SafeParcelable, ResourceEvent {
    public static final Parcelable.Creator<ChangeEvent> CREATOR = new a();
    final int ER;
    final DriveId Ew;
    final int xH;

    ChangeEvent(int versionCode, DriveId driveId, int changeFlags) {
        this.xH = versionCode;
        this.Ew = driveId;
        this.ER = changeFlags;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.drive.events.ResourceEvent, com.google.android.gms.drive.events.DriveEvent
    public DriveId getDriveId() {
        return this.Ew;
    }

    @Override // com.google.android.gms.drive.events.DriveEvent
    public int getType() {
        return 1;
    }

    public boolean hasContentChanged() {
        return (this.ER & 2) != 0;
    }

    public boolean hasMetadataChanged() {
        return (this.ER & 1) != 0;
    }

    public String toString() {
        return String.format("ChangeEvent [id=%s,changeFlags=%x]", this.Ew, Integer.valueOf(this.ER));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        a.a(this, dest, flags);
    }
}
