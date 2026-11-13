package com.google.android.gms.analytics;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.TapjoyConstants;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class CampaignTrackingService extends IntentService {
    public CampaignTrackingService() {
        super("CampaignIntentService");
    }

    public CampaignTrackingService(String name) {
        super(name);
    }

    @Override // android.app.IntentService
    public void onHandleIntent(Intent intent) {
        processIntent(this, intent);
    }

    public void processIntent(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(TapjoyConstants.TJC_REFERRER);
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput("gaInstallData", 0);
            fileOutputStreamOpenFileOutput.write(stringExtra.getBytes());
            fileOutputStreamOpenFileOutput.close();
            aa.y("Stored campaign information.");
        } catch (IOException e) {
            aa.w("Error storing install campaign.");
        }
    }
}
