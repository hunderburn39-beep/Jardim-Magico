package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.v;

/* JADX INFO: loaded from: classes.dex */
public final class GroundOverlayOptions implements SafeParcelable {
    public static final GroundOverlayOptionsCreator CREATOR = new GroundOverlayOptionsCreator();
    public static final float NO_DIMENSION = -1.0f;
    private float SG;
    private float SN;
    private boolean SO;
    private BitmapDescriptor SQ;
    private LatLng SR;
    private float SS;
    private float ST;
    private LatLngBounds SU;
    private float SV;
    private float SW;
    private float SX;
    private final int xH;

    public GroundOverlayOptions() {
        this.SO = true;
        this.SV = BitmapDescriptorFactory.HUE_RED;
        this.SW = 0.5f;
        this.SX = 0.5f;
        this.xH = 1;
    }

    GroundOverlayOptions(int versionCode, IBinder wrappedImage, LatLng location, float width, float height, LatLngBounds bounds, float bearing, float zIndex, boolean visible, float transparency, float anchorU, float anchorV) {
        this.SO = true;
        this.SV = BitmapDescriptorFactory.HUE_RED;
        this.SW = 0.5f;
        this.SX = 0.5f;
        this.xH = versionCode;
        this.SQ = new BitmapDescriptor(com.google.android.gms.dynamic.d.a.K(wrappedImage));
        this.SR = location;
        this.SS = width;
        this.ST = height;
        this.SU = bounds;
        this.SG = bearing;
        this.SN = zIndex;
        this.SO = visible;
        this.SV = transparency;
        this.SW = anchorU;
        this.SX = anchorV;
    }

    private GroundOverlayOptions a(LatLng latLng, float f, float f2) {
        this.SR = latLng;
        this.SS = f;
        this.ST = f2;
        return this;
    }

    public GroundOverlayOptions anchor(float u, float v) {
        this.SW = u;
        this.SX = v;
        return this;
    }

    public GroundOverlayOptions bearing(float bearing) {
        this.SG = ((bearing % 360.0f) + 360.0f) % 360.0f;
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float getAnchorU() {
        return this.SW;
    }

    public float getAnchorV() {
        return this.SX;
    }

    public float getBearing() {
        return this.SG;
    }

    public LatLngBounds getBounds() {
        return this.SU;
    }

    public float getHeight() {
        return this.ST;
    }

    public BitmapDescriptor getImage() {
        return this.SQ;
    }

    public LatLng getLocation() {
        return this.SR;
    }

    public float getTransparency() {
        return this.SV;
    }

    int getVersionCode() {
        return this.xH;
    }

    public float getWidth() {
        return this.SS;
    }

    public float getZIndex() {
        return this.SN;
    }

    IBinder iD() {
        return this.SQ.id().asBinder();
    }

    public GroundOverlayOptions image(BitmapDescriptor image) {
        this.SQ = image;
        return this;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public GroundOverlayOptions position(LatLng location, float width) {
        fq.a(this.SU == null, "Position has already been set using positionFromBounds");
        fq.b(location != null, "Location must be specified");
        fq.b(width >= BitmapDescriptorFactory.HUE_RED, "Width must be non-negative");
        return a(location, width, -1.0f);
    }

    public GroundOverlayOptions position(LatLng location, float width, float height) {
        fq.a(this.SU == null, "Position has already been set using positionFromBounds");
        fq.b(location != null, "Location must be specified");
        fq.b(width >= BitmapDescriptorFactory.HUE_RED, "Width must be non-negative");
        fq.b(height >= BitmapDescriptorFactory.HUE_RED, "Height must be non-negative");
        return a(location, width, height);
    }

    public GroundOverlayOptions positionFromBounds(LatLngBounds bounds) {
        fq.a(this.SR == null, "Position has already been set using position: " + this.SR);
        this.SU = bounds;
        return this;
    }

    public GroundOverlayOptions transparency(float transparency) {
        fq.b(transparency >= BitmapDescriptorFactory.HUE_RED && transparency <= 1.0f, "Transparency must be in the range [0..1]");
        this.SV = transparency;
        return this;
    }

    public GroundOverlayOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (v.iB()) {
            c.a(this, out, flags);
        } else {
            GroundOverlayOptionsCreator.a(this, out, flags);
        }
    }

    public GroundOverlayOptions zIndex(float zIndex) {
        this.SN = zIndex;
        return this;
    }
}
