package com.google.android.gms.location;

import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.hc;

/* JADX INFO: loaded from: classes.dex */
public class ActivityRecognitionClient implements GooglePlayServicesClient {
    private final hc NO;

    public ActivityRecognitionClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectedListener, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
        this.NO = new hc(context, connectedListener, connectionFailedListener, "activity_recognition");
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void connect() {
        this.NO.connect();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void disconnect() {
        this.NO.disconnect();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a, com.google.android.gms.internal.fg.b
    public boolean isConnected() {
        return this.NO.isConnected();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public boolean isConnecting() {
        return this.NO.isConnecting();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
        return this.NO.isConnectionCallbacksRegistered(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        return this.NO.isConnectionFailedListenerRegistered(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.NO.registerConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.NO.registerConnectionFailedListener(listener);
    }

    public void removeActivityUpdates(PendingIntent callbackIntent) {
        this.NO.removeActivityUpdates(callbackIntent);
    }

    public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
        this.NO.requestActivityUpdates(detectionIntervalMillis, callbackIntent);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.NO.unregisterConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.NO.unregisterConnectionFailedListener(listener);
    }
}
