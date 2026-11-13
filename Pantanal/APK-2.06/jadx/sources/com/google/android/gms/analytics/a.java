package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class a implements m {
    private static Object sf = new Object();
    private static a sg;
    private Context mContext;
    private AdvertisingIdClient.Info sh;
    private long si;

    a(Context context) {
        this.mContext = context;
    }

    private AdvertisingIdClient.Info bQ() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
        } catch (GooglePlayServicesNotAvailableException e) {
            aa.z("GooglePlayServicesNotAvailableException getting Ad Id Info");
            return null;
        } catch (GooglePlayServicesRepairableException e2) {
            aa.z("GooglePlayServicesRepairableException getting Ad Id Info");
            return null;
        } catch (IOException e3) {
            aa.z("IOException getting Ad Id Info");
            return null;
        } catch (IllegalStateException e4) {
            aa.z("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Exception e5) {
            aa.z("Unknown exception. Could not get the ad Id.");
            return null;
        }
    }

    public static m m(Context context) {
        if (sg == null) {
            synchronized (sf) {
                if (sg == null) {
                    sg = new a(context);
                }
            }
        }
        return sg;
    }

    @Override // com.google.android.gms.analytics.m
    public String getValue(String field) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.si > 1000) {
            this.sh = bQ();
            this.si = jCurrentTimeMillis;
        }
        if (this.sh != null) {
            if ("&adid".equals(field)) {
                return this.sh.getId();
            }
            if ("&ate".equals(field)) {
                return this.sh.isLimitAdTrackingEnabled() ? "0" : "1";
            }
        }
        return null;
    }
}
