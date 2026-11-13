package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.v;

/* JADX INFO: loaded from: classes.dex */
public final class MarkerOptions implements SafeParcelable {
    public static final MarkerOptionsCreator CREATOR = new MarkerOptionsCreator();
    private String EB;
    private boolean SO;
    private float SW;
    private float SX;
    private LatLng Sn;
    private String Tf;
    private BitmapDescriptor Tg;
    private boolean Th;
    private boolean Ti;
    private float Tj;
    private float Tk;
    private float Tl;
    private float mAlpha;
    private final int xH;

    public MarkerOptions() {
        this.SW = 0.5f;
        this.SX = 1.0f;
        this.SO = true;
        this.Ti = false;
        this.Tj = BitmapDescriptorFactory.HUE_RED;
        this.Tk = 0.5f;
        this.Tl = BitmapDescriptorFactory.HUE_RED;
        this.mAlpha = 1.0f;
        this.xH = 1;
    }

    MarkerOptions(int versionCode, LatLng position, String title, String snippet, IBinder wrappedIcon, float anchorU, float anchorV, boolean draggable, boolean visible, boolean flat, float rotation, float infoWindowAnchorU, float infoWindowAnchorV, float alpha) {
        this.SW = 0.5f;
        this.SX = 1.0f;
        this.SO = true;
        this.Ti = false;
        this.Tj = BitmapDescriptorFactory.HUE_RED;
        this.Tk = 0.5f;
        this.Tl = BitmapDescriptorFactory.HUE_RED;
        this.mAlpha = 1.0f;
        this.xH = versionCode;
        this.Sn = position;
        this.EB = title;
        this.Tf = snippet;
        this.Tg = wrappedIcon == null ? null : new BitmapDescriptor(com.google.android.gms.dynamic.d.a.K(wrappedIcon));
        this.SW = anchorU;
        this.SX = anchorV;
        this.Th = draggable;
        this.SO = visible;
        this.Ti = flat;
        this.Tj = rotation;
        this.Tk = infoWindowAnchorU;
        this.Tl = infoWindowAnchorV;
        this.mAlpha = alpha;
    }

    public MarkerOptions alpha(float alpha) {
        this.mAlpha = alpha;
        return this;
    }

    public MarkerOptions anchor(float u, float v) {
        this.SW = u;
        this.SX = v;
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MarkerOptions draggable(boolean draggable) {
        this.Th = draggable;
        return this;
    }

    public MarkerOptions flat(boolean flat) {
        this.Ti = flat;
        return this;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getAnchorU() {
        return this.SW;
    }

    public float getAnchorV() {
        return this.SX;
    }

    public BitmapDescriptor getIcon() {
        return this.Tg;
    }

    public float getInfoWindowAnchorU() {
        return this.Tk;
    }

    public float getInfoWindowAnchorV() {
        return this.Tl;
    }

    public LatLng getPosition() {
        return this.Sn;
    }

    public float getRotation() {
        return this.Tj;
    }

    public String getSnippet() {
        return this.Tf;
    }

    public String getTitle() {
        return this.EB;
    }

    int getVersionCode() {
        return this.xH;
    }

    IBinder iE() {
        if (this.Tg == null) {
            return null;
        }
        return this.Tg.id().asBinder();
    }

    public MarkerOptions icon(BitmapDescriptor icon) {
        this.Tg = icon;
        return this;
    }

    public MarkerOptions infoWindowAnchor(float u, float v) {
        this.Tk = u;
        this.Tl = v;
        return this;
    }

    public boolean isDraggable() {
        return this.Th;
    }

    public boolean isFlat() {
        return this.Ti;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public MarkerOptions position(LatLng position) {
        this.Sn = position;
        return this;
    }

    public MarkerOptions rotation(float rotation) {
        this.Tj = rotation;
        return this;
    }

    public MarkerOptions snippet(String snippet) {
        this.Tf = snippet;
        return this;
    }

    public MarkerOptions title(String title) {
        this.EB = title;
        return this;
    }

    public MarkerOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (v.iB()) {
            f.a(this, out, flags);
        } else {
            MarkerOptionsCreator.a(this, out, flags);
        }
    }
}
