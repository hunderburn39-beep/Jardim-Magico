package com.adcolony.sdk;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesPushRegIdListenerService extends InstanceIDListenerService {
    /* JADX WARN: Multi-variable type inference failed */
    public void onTokenRefresh() {
        startService(new Intent((Context) this, (Class<?>) AdColonyPubServicesPushRegIdIntentService.class));
    }
}
