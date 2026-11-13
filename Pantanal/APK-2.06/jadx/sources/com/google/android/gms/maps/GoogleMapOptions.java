package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.CameraPosition;

/* JADX INFO: loaded from: classes.dex */
public final class GoogleMapOptions implements SafeParcelable {
    public static final GoogleMapOptionsCreator CREATOR = new GoogleMapOptionsCreator();
    private Boolean RI;
    private Boolean RJ;
    private int RK;
    private CameraPosition RL;
    private Boolean RM;
    private Boolean RN;
    private Boolean RO;
    private Boolean RP;
    private Boolean RQ;
    private Boolean RR;
    private final int xH;

    public GoogleMapOptions() {
        this.RK = -1;
        this.xH = 1;
    }

    GoogleMapOptions(int versionCode, byte zOrderOnTop, byte useViewLifecycleInFragment, int mapType, CameraPosition camera, byte zoomControlsEnabled, byte compassEnabled, byte scrollGesturesEnabled, byte zoomGesturesEnabled, byte tiltGesturesEnabled, byte rotateGesturesEnabled) {
        this.RK = -1;
        this.xH = versionCode;
        this.RI = com.google.android.gms.maps.internal.a.a(zOrderOnTop);
        this.RJ = com.google.android.gms.maps.internal.a.a(useViewLifecycleInFragment);
        this.RK = mapType;
        this.RL = camera;
        this.RM = com.google.android.gms.maps.internal.a.a(zoomControlsEnabled);
        this.RN = com.google.android.gms.maps.internal.a.a(compassEnabled);
        this.RO = com.google.android.gms.maps.internal.a.a(scrollGesturesEnabled);
        this.RP = com.google.android.gms.maps.internal.a.a(zoomGesturesEnabled);
        this.RQ = com.google.android.gms.maps.internal.a.a(tiltGesturesEnabled);
        this.RR = com.google.android.gms.maps.internal.a.a(rotateGesturesEnabled);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return null;
        }
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attrs, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (typedArrayObtainAttributes.hasValue(0)) {
            googleMapOptions.mapType(typedArrayObtainAttributes.getInt(0, -1));
        }
        if (typedArrayObtainAttributes.hasValue(13)) {
            googleMapOptions.zOrderOnTop(typedArrayObtainAttributes.getBoolean(13, false));
        }
        if (typedArrayObtainAttributes.hasValue(12)) {
            googleMapOptions.useViewLifecycleInFragment(typedArrayObtainAttributes.getBoolean(12, false));
        }
        if (typedArrayObtainAttributes.hasValue(6)) {
            googleMapOptions.compassEnabled(typedArrayObtainAttributes.getBoolean(6, true));
        }
        if (typedArrayObtainAttributes.hasValue(7)) {
            googleMapOptions.rotateGesturesEnabled(typedArrayObtainAttributes.getBoolean(7, true));
        }
        if (typedArrayObtainAttributes.hasValue(8)) {
            googleMapOptions.scrollGesturesEnabled(typedArrayObtainAttributes.getBoolean(8, true));
        }
        if (typedArrayObtainAttributes.hasValue(9)) {
            googleMapOptions.tiltGesturesEnabled(typedArrayObtainAttributes.getBoolean(9, true));
        }
        if (typedArrayObtainAttributes.hasValue(11)) {
            googleMapOptions.zoomGesturesEnabled(typedArrayObtainAttributes.getBoolean(11, true));
        }
        if (typedArrayObtainAttributes.hasValue(10)) {
            googleMapOptions.zoomControlsEnabled(typedArrayObtainAttributes.getBoolean(10, true));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attrs));
        typedArrayObtainAttributes.recycle();
        return googleMapOptions;
    }

    public GoogleMapOptions camera(CameraPosition camera) {
        this.RL = camera;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean enabled) {
        this.RN = Boolean.valueOf(enabled);
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CameraPosition getCamera() {
        return this.RL;
    }

    public Boolean getCompassEnabled() {
        return this.RN;
    }

    public int getMapType() {
        return this.RK;
    }

    public Boolean getRotateGesturesEnabled() {
        return this.RR;
    }

    public Boolean getScrollGesturesEnabled() {
        return this.RO;
    }

    public Boolean getTiltGesturesEnabled() {
        return this.RQ;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.RJ;
    }

    int getVersionCode() {
        return this.xH;
    }

    public Boolean getZOrderOnTop() {
        return this.RI;
    }

    public Boolean getZoomControlsEnabled() {
        return this.RM;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.RP;
    }

    byte ig() {
        return com.google.android.gms.maps.internal.a.c(this.RI);
    }

    byte ih() {
        return com.google.android.gms.maps.internal.a.c(this.RJ);
    }

    byte ii() {
        return com.google.android.gms.maps.internal.a.c(this.RM);
    }

    byte ij() {
        return com.google.android.gms.maps.internal.a.c(this.RN);
    }

    byte ik() {
        return com.google.android.gms.maps.internal.a.c(this.RO);
    }

    byte il() {
        return com.google.android.gms.maps.internal.a.c(this.RP);
    }

    byte im() {
        return com.google.android.gms.maps.internal.a.c(this.RQ);
    }

    byte in() {
        return com.google.android.gms.maps.internal.a.c(this.RR);
    }

    public GoogleMapOptions mapType(int mapType) {
        this.RK = mapType;
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean enabled) {
        this.RR = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean enabled) {
        this.RO = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean enabled) {
        this.RQ = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.RJ = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (v.iB()) {
            a.a(this, out, flags);
        } else {
            GoogleMapOptionsCreator.a(this, out, flags);
        }
    }

    public GoogleMapOptions zOrderOnTop(boolean zOrderOnTop) {
        this.RI = Boolean.valueOf(zOrderOnTop);
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean enabled) {
        this.RM = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean enabled) {
        this.RP = Boolean.valueOf(enabled);
        return this;
    }
}
