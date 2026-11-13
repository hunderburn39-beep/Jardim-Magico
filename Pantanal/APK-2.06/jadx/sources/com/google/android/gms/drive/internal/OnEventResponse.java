package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ConflictEvent;

/* JADX INFO: loaded from: classes.dex */
public class OnEventResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnEventResponse> CREATOR = new ac();
    final int ES;
    final ChangeEvent FH;
    final ConflictEvent FI;
    final int xH;

    OnEventResponse(int versionCode, int eventType, ChangeEvent changeEvent, ConflictEvent conflictEvent) {
        this.xH = versionCode;
        this.ES = eventType;
        this.FH = changeEvent;
        this.FI = conflictEvent;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ChangeEvent fL() {
        return this.FH;
    }

    public ConflictEvent fM() {
        return this.FI;
    }

    public int getEventType() {
        return this.ES;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        ac.a(this, dest, flags);
    }
}
