package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.internal.CreateFileIntentSenderRequest;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.internal.fq;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class CreateFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private Contents EA;
    private String EB;
    private DriveId EC;
    private MetadataChangeSet Ez;

    public IntentSender build(GoogleApiClient apiClient) {
        fq.b(this.Ez, "Must provide initial metadata to CreateFileActivityBuilder.");
        fq.b(this.EA, "Must provide initial contents to CreateFileActivityBuilder.");
        try {
            this.EA.getParcelFileDescriptor().close();
        } catch (IOException e) {
        }
        this.EA.close();
        fq.a(apiClient.isConnected(), "Client must be connected");
        try {
            return ((n) apiClient.a(Drive.wx)).fE().a(new CreateFileIntentSenderRequest(this.Ez.fD(), this.EA.fA(), this.EB, this.EC));
        } catch (RemoteException e2) {
            throw new RuntimeException("Unable to connect Drive Play Service", e2);
        }
    }

    public CreateFileActivityBuilder setActivityStartFolder(DriveId folder) {
        this.EC = (DriveId) fq.f(folder);
        return this;
    }

    public CreateFileActivityBuilder setActivityTitle(String title) {
        this.EB = (String) fq.f(title);
        return this;
    }

    public CreateFileActivityBuilder setInitialContents(Contents contents) {
        this.EA = (Contents) fq.f(contents);
        return this;
    }

    public CreateFileActivityBuilder setInitialMetadata(MetadataChangeSet metadataChangeSet) {
        this.Ez = (MetadataChangeSet) fq.f(metadataChangeSet);
        return this;
    }
}
