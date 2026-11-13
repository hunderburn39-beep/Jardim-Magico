package com.heyzap.sdk.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class PackageAddedReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
            String packageName = intent.getDataString().replaceFirst(intent.getScheme() + ":", "");
            if (packageName == null) {
            }
        }
    }
}
