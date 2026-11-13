package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.v;

/* JADX INFO: loaded from: classes.dex */
public final class CameraPosition implements SafeParcelable {
    public static final CameraPositionCreator CREATOR = new CameraPositionCreator();
    public final float bearing;
    public final LatLng target;
    public final float tilt;
    private final int xH;
    public final float zoom;

    public static final class Builder {
        private LatLng SD;
        private float SE;
        private float SF;
        private float SG;

        public Builder() {
        }

        public Builder(CameraPosition previous) {
            this.SD = previous.target;
            this.SE = previous.zoom;
            this.SF = previous.tilt;
            this.SG = previous.bearing;
        }

        public Builder bearing(float bearing) {
            this.SG = bearing;
            return this;
        }

        public CameraPosition build() {
            return new CameraPosition(this.SD, this.SE, this.SF, this.SG);
        }

        public Builder target(LatLng location) {
            this.SD = location;
            return this;
        }

        public Builder tilt(float tilt) {
            this.SF = tilt;
            return this;
        }

        public Builder zoom(float zoom) {
            this.SE = zoom;
            return this;
        }
    }

    CameraPosition(int versionCode, LatLng target, float zoom, float tilt, float bearing) {
        fq.b(target, "null camera target");
        fq.b(BitmapDescriptorFactory.HUE_RED <= tilt && tilt <= 90.0f, "Tilt needs to be between 0 and 90 inclusive");
        this.xH = versionCode;
        this.target = target;
        this.zoom = zoom;
        this.tilt = tilt + BitmapDescriptorFactory.HUE_RED;
        this.bearing = (((double) bearing) <= 0.0d ? (bearing % 360.0f) + 360.0f : bearing) % 360.0f;
    }

    public CameraPosition(LatLng target, float zoom, float tilt, float bearing) {
        this(1, target, zoom, tilt, bearing);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition camera) {
        return new Builder(camera);
    }

    public static CameraPosition createFromAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return null;
        }
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attrs, R.styleable.MapAttrs);
        LatLng latLng = new LatLng(typedArrayObtainAttributes.hasValue(2) ? typedArrayObtainAttributes.getFloat(2, BitmapDescriptorFactory.HUE_RED) : 0.0f, typedArrayObtainAttributes.hasValue(3) ? typedArrayObtainAttributes.getFloat(3, BitmapDescriptorFactory.HUE_RED) : 0.0f);
        Builder builder = builder();
        builder.target(latLng);
        if (typedArrayObtainAttributes.hasValue(5)) {
            builder.zoom(typedArrayObtainAttributes.getFloat(5, BitmapDescriptorFactory.HUE_RED));
        }
        if (typedArrayObtainAttributes.hasValue(1)) {
            builder.bearing(typedArrayObtainAttributes.getFloat(1, BitmapDescriptorFactory.HUE_RED));
        }
        if (typedArrayObtainAttributes.hasValue(4)) {
            builder.tilt(typedArrayObtainAttributes.getFloat(4, BitmapDescriptorFactory.HUE_RED));
        }
        return builder.build();
    }

    public static final CameraPosition fromLatLngZoom(LatLng target, float zoom) {
        return new CameraPosition(target, zoom, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CameraPosition)) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) o;
        return this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing);
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    public String toString() {
        return fo.e(this).a("target", this.target).a("zoom", Float.valueOf(this.zoom)).a("tilt", Float.valueOf(this.tilt)).a("bearing", Float.valueOf(this.bearing)).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (v.iB()) {
            a.a(this, out, flags);
        } else {
            CameraPositionCreator.a(this, out, flags);
        }
    }
}
