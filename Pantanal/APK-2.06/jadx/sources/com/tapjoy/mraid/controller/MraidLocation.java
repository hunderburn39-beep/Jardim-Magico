package com.tapjoy.mraid.controller;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.listener.Loc;
import com.tapjoy.mraid.view.MraidView;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class MraidLocation extends Abstract {
    final int c;
    private LocationManager d;
    private boolean e;
    private Loc f;
    private Loc g;
    private int h;
    private boolean i;

    public MraidLocation(MraidView adView, Context context) {
        super(adView, context);
        this.e = false;
        this.c = 1000;
        this.i = false;
        try {
            this.d = (LocationManager) context.getSystemService("location");
            if (!TapjoyConnectCore.isUnitTestMode()) {
                if (this.d.getProvider("gps") != null) {
                    this.f = new Loc(context, 1000, this, "gps");
                }
                if (this.d.getProvider("network") != null) {
                    this.g = new Loc(context, 1000, this, "network");
                }
                this.e = true;
            }
        } catch (SecurityException e) {
        }
    }

    public void allowLocationServices(boolean flag) {
        this.i = flag;
    }

    public boolean allowLocationServices() {
        return this.i;
    }

    private static String a(Location location) {
        return "{ lat: " + location.getLatitude() + ", lon: " + location.getLongitude() + ", acc: " + location.getAccuracy() + "}";
    }

    public String getLocation() {
        TapjoyLog.d("MRAID Location", "getLocation: hasPermission: " + this.e);
        if (!this.e) {
            return null;
        }
        Iterator<String> it = this.d.getProviders(true).iterator();
        Location lastKnownLocation = null;
        while (it.hasNext() && (lastKnownLocation = this.d.getLastKnownLocation(it.next())) == null) {
        }
        TapjoyLog.d("MRAID Location", "getLocation: " + lastKnownLocation);
        if (lastKnownLocation != null) {
            return a(lastKnownLocation);
        }
        return null;
    }

    public void startLocationListener() {
        if (this.h == 0) {
            if (this.g != null) {
                this.g.start();
            }
            if (this.f != null) {
                this.f.start();
            }
        }
        this.h++;
    }

    public void stopLocationListener() {
        this.h--;
        if (this.h == 0) {
            if (this.g != null) {
                this.g.stop();
            }
            if (this.f != null) {
                this.f.stop();
            }
        }
    }

    public void success(Location loc) {
        String str = "window.mraidview.fireChangeEvent({ location: " + a(loc) + "})";
        TapjoyLog.d("MRAID Location", str);
        this.a.injectMraidJavaScript(str);
    }

    public void fail() {
        TapjoyLog.e("MRAID Location", "Location can't be determined");
        this.a.injectMraidJavaScript("window.mraidview.fireErrorEvent(\"Location cannot be identified\", \"OrmmaLocationController\")");
    }

    @Override // com.tapjoy.mraid.controller.Abstract
    public void stopAllListeners() {
        this.h = 0;
        try {
            this.f.stop();
        } catch (Exception e) {
        }
        try {
            this.g.stop();
        } catch (Exception e2) {
        }
    }
}
