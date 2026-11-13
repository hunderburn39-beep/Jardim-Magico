package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public final class InstallReferrerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(TapjoyConstants.TJC_REFERRER);
        if (!"com.android.vending.INSTALL_REFERRER".equals(intent.getAction()) || stringExtra == null) {
            return;
        }
        ay.bF(stringExtra);
        Intent intent2 = new Intent(context, (Class<?>) InstallReferrerService.class);
        intent2.putExtra(TapjoyConstants.TJC_REFERRER, stringExtra);
        context.startService(intent2);
    }
}
