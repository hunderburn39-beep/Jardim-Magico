package org.anddev.andengine.sensor.location;

import android.location.Location;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public interface ILocationListener {
    void onLocationChanged(Location location);

    void onLocationLost();

    void onLocationProviderDisabled();

    void onLocationProviderEnabled();

    void onLocationProviderStatusChanged(LocationProviderStatus locationProviderStatus, Bundle bundle);
}
