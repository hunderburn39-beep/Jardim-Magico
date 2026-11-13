package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.internal.OpenFileIntentSenderRequest;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public class OpenFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private String EB;
    private DriveId EC;
    private String[] EQ;

    public IntentSender build(GoogleApiClient apiClient) {
        fq.b(this.EQ, "setMimeType(String[]) must be called on this builder before calling build()");
        fq.a(apiClient.isConnected(), "Client must be connected");
        try {
            return ((n) apiClient.a(Drive.wx)).fE().a(new OpenFileIntentSenderRequest(this.EB, this.EQ, this.EC));
        } catch (RemoteException e) {
            throw new RuntimeException("Unable to connect Drive Play Service", e);
        }
    }

    public OpenFileActivityBuilder setActivityStartFolder(DriveId folder) {
        this.EC = (DriveId) fq.f(folder);
        return this;
    }

    public OpenFileActivityBuilder setActivityTitle(String title) {
        this.EB = (String) fq.f(title);
        return this;
    }

    public OpenFileActivityBuilder setMimeType(String[] mimeTypes) {
        fq.b(mimeTypes != null && mimeTypes.length > 0, "mimeTypes may not be null and must contain at least one value");
        this.EQ = mimeTypes;
        return this;
    }
}
