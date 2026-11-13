package com.tapjoy;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesUtil;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyGpsHelper {
    private Context a;
    private String b;
    private boolean c;
    private int d = 0;
    private int e = 0;
    private boolean f;
    private Boolean g;
    private Boolean h;

    public TapjoyGpsHelper(Context context) {
        this.a = context;
    }

    public void loadAdvertisingId() {
        TapjoyLog.i("TapjoyGpsHelper", "Looking for Google Play Services...");
        if (isGooglePlayServicesAvailable() && isGooglePlayManifestConfigured()) {
            TapjoyLog.i("TapjoyGpsHelper", "Packaged Google Play Services found, fetching advertisingID...");
            TapjoyLog.i("TapjoyGpsHelper", "Packaged Google Play Services version: " + this.e);
            TapjoyAdIdClient tapjoyAdIdClient = new TapjoyAdIdClient(this.a);
            this.f = tapjoyAdIdClient.setupAdIdInfo();
            try {
                this.d = this.a.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 0).versionCode;
                TapjoyLog.i("TapjoyGpsHelper", "Device's Google Play Services version: " + this.d);
            } catch (Exception e) {
                TapjoyLog.i("TapjoyGpsHelper", "Error getting device's Google Play Services version");
            }
            if (this.f) {
                this.c = tapjoyAdIdClient.isAdTrackingEnabled();
                this.b = tapjoyAdIdClient.getAdvertisingId();
                TapjoyLog.i("TapjoyGpsHelper", "Found advertising ID: " + this.b);
                TapjoyLog.i("TapjoyGpsHelper", "Is ad tracking enabled: " + Boolean.toString(this.c));
                return;
            }
            TapjoyLog.i("TapjoyGpsHelper", "Error getting advertisingID from Google Play Services");
            return;
        }
        TapjoyLog.i("TapjoyGpsHelper", "Google Play Services not found");
    }

    public void checkGooglePlayIntegration() {
        if (!isGooglePlayServicesAvailable()) {
            throw new TapjoyIntegrationException("Tapjoy SDK is disabled because Google Play Services was not found. For more information about including the Google Play services client library visit http://developer.android.com/google/play-services/setup.html or http://tech.tapjoy.com/product-overview/sdk-change-log/tapjoy-and-identifiers");
        }
        if (!isGooglePlayManifestConfigured()) {
            throw new TapjoyIntegrationException("Failed to load manifest.xml meta-data, 'com.google.android.gms.version' not found. For more information about including the Google Play services client library visit http://developer.android.com/google/play-services/setup.html or http://tech.tapjoy.com/product-overview/sdk-change-log/tapjoy-and-identifiers");
        }
    }

    public boolean isGooglePlayServicesAvailable() {
        if (this.g == null) {
            try {
                this.a.getClassLoader().loadClass("com.google.android.gms.ads.identifier.AdvertisingIdClient");
                this.g = true;
            } catch (Error e) {
                this.g = false;
            } catch (Exception e2) {
                this.g = false;
            }
        }
        return this.g.booleanValue();
    }

    public boolean isGooglePlayManifestConfigured() {
        if (this.h == null) {
            try {
                this.e = this.a.getPackageManager().getApplicationInfo(this.a.getPackageName(), 128).metaData.getInt("com.google.android.gms.version");
                this.h = true;
            } catch (Exception e) {
                this.h = false;
            }
        }
        return this.h.booleanValue();
    }

    public String getAdvertisingId() {
        return this.b;
    }

    public boolean isAdTrackingEnabled() {
        return this.c;
    }

    public boolean isAdIdAvailable() {
        return this.f;
    }

    public int getDeviceGooglePlayServicesVersion() {
        return this.d;
    }

    public int getPackagedGooglePlayServicesVersion() {
        return this.e;
    }
}
