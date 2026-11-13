package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

/* JADX INFO: loaded from: classes.dex */
public final class StreetViewPanoramaOptions implements SafeParcelable {
    public static final StreetViewPanoramaOptionsCreator CREATOR = new StreetViewPanoramaOptionsCreator();
    private Boolean RJ;
    private Boolean RP;
    private StreetViewPanoramaCamera Sl;
    private String Sm;
    private LatLng Sn;
    private Integer So;
    private Boolean Sp;
    private Boolean Sq;
    private Boolean Sr;
    private final int xH;

    public StreetViewPanoramaOptions() {
        this.Sp = true;
        this.RP = true;
        this.Sq = true;
        this.Sr = true;
        this.xH = 1;
    }

    StreetViewPanoramaOptions(int versionCode, StreetViewPanoramaCamera camera, String panoId, LatLng position, Integer radius, byte userNavigationEnabled, byte zoomGesturesEnabled, byte panningGesturesEnabled, byte streetNamesEnabled, byte useViewLifecycleInFragment) {
        this.Sp = true;
        this.RP = true;
        this.Sq = true;
        this.Sr = true;
        this.xH = versionCode;
        this.Sl = camera;
        this.Sn = position;
        this.So = radius;
        this.Sm = panoId;
        this.Sp = com.google.android.gms.maps.internal.a.a(userNavigationEnabled);
        this.RP = com.google.android.gms.maps.internal.a.a(zoomGesturesEnabled);
        this.Sq = com.google.android.gms.maps.internal.a.a(panningGesturesEnabled);
        this.Sr = com.google.android.gms.maps.internal.a.a(streetNamesEnabled);
        this.RJ = com.google.android.gms.maps.internal.a.a(useViewLifecycleInFragment);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Boolean getPanningGesturesEnabled() {
        return this.Sq;
    }

    public String getPanoramaId() {
        return this.Sm;
    }

    public LatLng getPosition() {
        return this.Sn;
    }

    public Integer getRadius() {
        return this.So;
    }

    public Boolean getStreetNamesEnabled() {
        return this.Sr;
    }

    public StreetViewPanoramaCamera getStreetViewPanoramaCamera() {
        return this.Sl;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.RJ;
    }

    public Boolean getUserNavigationEnabled() {
        return this.Sp;
    }

    int getVersionCode() {
        return this.xH;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.RP;
    }

    byte ih() {
        return com.google.android.gms.maps.internal.a.c(this.RJ);
    }

    byte il() {
        return com.google.android.gms.maps.internal.a.c(this.RP);
    }

    byte it() {
        return com.google.android.gms.maps.internal.a.c(this.Sp);
    }

    byte iu() {
        return com.google.android.gms.maps.internal.a.c(this.Sq);
    }

    byte iv() {
        return com.google.android.gms.maps.internal.a.c(this.Sr);
    }

    public StreetViewPanoramaOptions panningGesturesEnabled(boolean enabled) {
        this.Sq = Boolean.valueOf(enabled);
        return this;
    }

    public StreetViewPanoramaOptions panoramaCamera(StreetViewPanoramaCamera camera) {
        this.Sl = camera;
        return this;
    }

    public StreetViewPanoramaOptions panoramaId(String panoId) {
        this.Sm = panoId;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng position) {
        this.Sn = position;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng position, Integer radius) {
        this.Sn = position;
        this.So = radius;
        return this;
    }

    public StreetViewPanoramaOptions streetNamesEnabled(boolean enabled) {
        this.Sr = Boolean.valueOf(enabled);
        return this;
    }

    public StreetViewPanoramaOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.RJ = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    public StreetViewPanoramaOptions userNavigationEnabled(boolean enabled) {
        this.Sp = Boolean.valueOf(enabled);
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        StreetViewPanoramaOptionsCreator.a(this, out, flags);
    }

    public StreetViewPanoramaOptions zoomGesturesEnabled(boolean enabled) {
        this.RP = Boolean.valueOf(enabled);
        return this;
    }
}
