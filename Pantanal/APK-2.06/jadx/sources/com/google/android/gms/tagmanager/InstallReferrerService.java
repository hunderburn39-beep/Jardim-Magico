package com.google.android.gms.tagmanager;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public final class InstallReferrerService extends IntentService {
    CampaignTrackingService Yi;
    Context Yj;

    public InstallReferrerService() {
        super("InstallReferrerService");
    }

    public InstallReferrerService(String name) {
        super(name);
    }

    private void a(Context context, Intent intent) {
        if (this.Yi == null) {
            this.Yi = new CampaignTrackingService();
        }
        this.Yi.processIntent(context, intent);
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(TapjoyConstants.TJC_REFERRER);
        Context applicationContext = this.Yj != null ? this.Yj : getApplicationContext();
        ay.c(applicationContext, stringExtra);
        a(applicationContext, intent);
    }
}
