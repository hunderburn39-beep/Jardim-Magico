package com.google.android.gms.drive.events;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public abstract class DriveEventService extends IntentService {
    private final String mName;

    protected DriveEventService(String name) {
        super(name);
        this.mName = name;
    }

    public void a(ConflictEvent conflictEvent) {
        Log.w("DriveEventService", "Unhandled ConflictEvent: " + conflictEvent);
    }

    public void onChangeEvent(ChangeEvent event) {
        Log.w("DriveEventService", "Unhandled ChangeEvent: " + event);
    }

    @Override // android.app.IntentService
    protected final void onHandleIntent(Intent intent) {
        intent.setExtrasClassLoader(getClassLoader());
        DriveEvent driveEvent = (DriveEvent) intent.getParcelableExtra("event");
        try {
            switch (driveEvent.getType()) {
                case 1:
                    fq.a(driveEvent instanceof ChangeEvent, "Unexpected event type: " + driveEvent);
                    onChangeEvent((ChangeEvent) driveEvent);
                    break;
                case 2:
                    fq.a(driveEvent instanceof ConflictEvent, "Unexpected event type: " + driveEvent);
                    a((ConflictEvent) driveEvent);
                    break;
                default:
                    Log.w(this.mName, "Unrecognized event: " + intent);
                    break;
            }
        } catch (ClassCastException e) {
            Log.wtf(this.mName, "Service does not implement listener for type:" + driveEvent.getType(), e);
        } catch (Exception e2) {
            Log.w(this.mName, "Error handling event: " + intent, e2);
        }
    }
}
