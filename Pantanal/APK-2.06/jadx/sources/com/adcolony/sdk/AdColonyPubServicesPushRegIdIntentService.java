package com.adcolony.sdk;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.iid.InstanceID;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesPushRegIdIntentService extends IntentService {
    private static final String a = "AdColonyPubServicesPushRegIdIntentService";

    public AdColonyPubServicesPushRegIdIntentService() {
        super(a);
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        try {
            String token = InstanceID.getInstance(this).getToken(bv.aN().an(), "GCM", (Bundle) null);
            bw.b(a, "GCM Registration Token: " + token, true);
            a(token);
            bv.aN().aE().a(token);
        } catch (Exception e) {
            bw.a(a, "Exception occurred when retrieving registration token->" + e + ". Registration to GCM incomplete.", e);
            bv.aN().aE().a(e);
        }
    }

    private void a(String str) {
        bv.aN().t(str);
    }
}
