package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.v;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class PolylineOptions implements SafeParcelable {
    public static final PolylineOptionsCreator CREATOR = new PolylineOptionsCreator();
    private int Av;
    private float SN;
    private boolean SO;
    private float SS;
    private final List<LatLng> Tn;
    private boolean Tp;
    private final int xH;

    public PolylineOptions() {
        this.SS = 10.0f;
        this.Av = -16777216;
        this.SN = BitmapDescriptorFactory.HUE_RED;
        this.SO = true;
        this.Tp = false;
        this.xH = 1;
        this.Tn = new ArrayList();
    }

    PolylineOptions(int versionCode, List points, float width, int color, float zIndex, boolean visible, boolean geodesic) {
        this.SS = 10.0f;
        this.Av = -16777216;
        this.SN = BitmapDescriptorFactory.HUE_RED;
        this.SO = true;
        this.Tp = false;
        this.xH = versionCode;
        this.Tn = points;
        this.SS = width;
        this.Av = color;
        this.SN = zIndex;
        this.SO = visible;
        this.Tp = geodesic;
    }

    public PolylineOptions add(LatLng point) {
        this.Tn.add(point);
        return this;
    }

    public PolylineOptions add(LatLng... points) {
        this.Tn.addAll(Arrays.asList(points));
        return this;
    }

    public PolylineOptions addAll(Iterable<LatLng> points) {
        Iterator<LatLng> it = points.iterator();
        while (it.hasNext()) {
            this.Tn.add(it.next());
        }
        return this;
    }

    public PolylineOptions color(int color) {
        this.Av = color;
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PolylineOptions geodesic(boolean geodesic) {
        this.Tp = geodesic;
        return this;
    }

    public int getColor() {
        return this.Av;
    }

    public List<LatLng> getPoints() {
        return this.Tn;
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

    public boolean isGeodesic() {
        return this.Tp;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public PolylineOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    public PolylineOptions width(float width) {
        this.SS = width;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (v.iB()) {
            h.a(this, out, flags);
        } else {
            PolylineOptionsCreator.a(this, out, flags);
        }
    }

    public PolylineOptions zIndex(float zIndex) {
        this.SN = zIndex;
        return this;
    }
}
