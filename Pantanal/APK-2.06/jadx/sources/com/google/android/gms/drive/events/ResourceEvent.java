package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public interface ResourceEvent extends DriveEvent {
    @Override // com.google.android.gms.drive.events.DriveEvent
    DriveId getDriveId();
}
