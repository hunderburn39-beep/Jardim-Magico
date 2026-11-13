package org.anddev.andengine.entity.particle.initializer;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class GravityInitializer extends AccelerationInitializer {
    public GravityInitializer() {
        super(BitmapDescriptorFactory.HUE_RED, 9.80665f);
    }
}
