package com.heyzap.internal;

import android.content.Context;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
class AdvertisingIdCallable implements Callable<Boolean> {
    private Context context;

    public AdvertisingIdCallable(Context context) {
        this.context = context;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    public Boolean call() throws Exception {
        String id;
        Boolean isLAT;
        try {
            Boolean.valueOf(false);
            try {
                Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
                AdvertisingIdClient.AdInfo gAdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.context);
                Logger.log("Using Google Play Services...");
                id = gAdInfo.getId();
                isLAT = Boolean.valueOf(gAdInfo.isLimitAdTrackingEnabled());
            } catch (Exception e) {
                Logger.log("Using Heyzap Google Play Services Client as fallback...");
                AdvertisingIdClient.AdInfo adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.context);
                id = adInfo.getId();
                isLAT = Boolean.valueOf(adInfo.isLimitAdTrackingEnabled());
            }
            Utils.setAdvertisingId(id);
            Utils.setLimitAdTracking(isLAT);
        } catch (Exception e2) {
            Logger.trace((Throwable) e2);
            Utils.setAdvertisingId(null);
            Utils.setLimitAdTracking(false);
        }
        return true;
    }
}
