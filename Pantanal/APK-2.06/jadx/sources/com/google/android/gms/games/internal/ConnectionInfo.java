package com.google.android.gms.games.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionInfo implements SafeParcelable {
    public static final ConnectionInfoCreator CREATOR = new ConnectionInfoCreator();
    private final String Is;
    private final int It;
    private final int xH;

    public ConnectionInfo(int versionCode, String clientAddress, int registrationLatency) {
        this.xH = versionCode;
        this.Is = clientAddress;
        this.It = registrationLatency;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public String gi() {
        return this.Is;
    }

    public int gj() {
        return this.It;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ConnectionInfoCreator.a(this, out, flags);
    }
}
