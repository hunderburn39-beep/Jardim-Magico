package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;

/* JADX INFO: loaded from: classes.dex */
public final class Status implements Result, SafeParcelable {
    public static final Status Bv = new Status(0);
    public static final Status Bw = new Status(14);
    public static final Status Bx = new Status(8);
    public static final Status By = new Status(15);
    public static final Status Bz = new Status(16);
    public static final StatusCreator CREATOR = new StatusCreator();
    private final int Ah;
    private final String BA;
    private final PendingIntent mPendingIntent;
    private final int xH;

    public Status(int statusCode) {
        this(1, statusCode, null, null);
    }

    Status(int versionCode, int statusCode, String statusMessage, PendingIntent pendingIntent) {
        this.xH = versionCode;
        this.Ah = statusCode;
        this.BA = statusMessage;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int statusCode, String statusMessage, PendingIntent pendingIntent) {
        this(1, statusCode, statusMessage, pendingIntent);
    }

    private String dW() {
        return this.BA != null ? this.BA : CommonStatusCodes.getStatusCodeString(this.Ah);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    PendingIntent eo() {
        return this.mPendingIntent;
    }

    String ep() {
        return this.BA;
    }

    @Deprecated
    public ConnectionResult eq() {
        return new ConnectionResult(this.Ah, this.mPendingIntent);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.xH == status.xH && this.Ah == status.Ah && fo.equal(this.BA, status.BA) && fo.equal(this.mPendingIntent, status.mPendingIntent);
    }

    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    @Override // com.google.android.gms.common.api.Result
    public Status getStatus() {
        return this;
    }

    public int getStatusCode() {
        return this.Ah;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasResolution() {
        return this.mPendingIntent != null;
    }

    public int hashCode() {
        return fo.hashCode(Integer.valueOf(this.xH), Integer.valueOf(this.Ah), this.BA, this.mPendingIntent);
    }

    public boolean isCanceled() {
        return this.Ah == 16;
    }

    public boolean isInterrupted() {
        return this.Ah == 14;
    }

    public boolean isSuccess() {
        return this.Ah <= 0;
    }

    public void startResolutionForResult(Activity activity, int requestCode) throws IntentSender.SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), requestCode, null, 0, 0, 0);
        }
    }

    public String toString() {
        return fo.e(this).a("statusCode", dW()).a("resolution", this.mPendingIntent).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        StatusCreator.a(this, out, flags);
    }
}
