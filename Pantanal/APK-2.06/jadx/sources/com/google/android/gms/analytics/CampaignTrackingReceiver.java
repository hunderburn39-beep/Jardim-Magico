package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public final class CampaignTrackingReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context ctx, Intent intent) {
        String stringExtra = intent.getStringExtra(TapjoyConstants.TJC_REFERRER);
        if (!"com.android.vending.INSTALL_REFERRER".equals(intent.getAction()) || stringExtra == null) {
            return;
        }
        Intent intent2 = new Intent(ctx, (Class<?>) CampaignTrackingService.class);
        intent2.putExtra(TapjoyConstants.TJC_REFERRER, stringExtra);
        ctx.startService(intent2);
    }
}
