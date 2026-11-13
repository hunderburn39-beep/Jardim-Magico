package com.tapjoy.mraid.listener;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tapjoy.mraid.controller.MraidLocation;

/* JADX INFO: loaded from: classes.dex */
public class Loc implements LocationListener {
    MraidLocation a;
    private LocationManager b;
    private String c;

    public Loc(Context c, int interval, MraidLocation ormmaLocationController, String provider) {
        this.a = ormmaLocationController;
        this.b = (LocationManager) c.getSystemService("location");
        this.c = provider;
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String provider) {
        this.a.fail();
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (status == 0) {
            this.a.fail();
        }
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        this.a.success(location);
    }

    public void stop() {
        this.b.removeUpdates(this);
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String provider) {
    }

    public void start() {
        this.b.requestLocationUpdates(this.c, 0L, BitmapDescriptorFactory.HUE_RED, this);
    }
}
