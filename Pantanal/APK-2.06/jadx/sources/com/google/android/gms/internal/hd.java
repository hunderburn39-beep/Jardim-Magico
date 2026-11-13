package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class hd implements SafeParcelable, Geofence {
    public static final he CREATOR = new he();
    private final String Jo;
    private final int NU;
    private final short NW;
    private final double NX;
    private final double NY;
    private final float NZ;
    private final int Oa;
    private final int Ob;
    private final long Oz;
    private final int xH;

    public hd(int i, String str, int i2, short s, double d, double d2, float f, long j, int i3, int i4) {
        aY(str);
        b(f);
        a(d, d2);
        int iBB = bB(i2);
        this.xH = i;
        this.NW = s;
        this.Jo = str;
        this.NX = d;
        this.NY = d2;
        this.NZ = f;
        this.Oz = j;
        this.NU = iBB;
        this.Oa = i3;
        this.Ob = i4;
    }

    public hd(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
        this(1, str, i, s, d, d2, f, j, i2, i3);
    }

    private static void a(double d, double d2) {
        if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException("invalid latitude: " + d);
        }
        if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException("invalid longitude: " + d2);
        }
    }

    private static void aY(String str) {
        if (str == null || str.length() > 100) {
            throw new IllegalArgumentException("requestId is null or too long: " + str);
        }
    }

    private static void b(float f) {
        if (f <= BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("invalid radius: " + f);
        }
    }

    private static int bB(int i) {
        int i2 = i & 7;
        if (i2 == 0) {
            throw new IllegalArgumentException("No supported transition specified: " + i);
        }
        return i2;
    }

    private static String bC(int i) {
        switch (i) {
            case 1:
                return "CIRCLE";
            default:
                return null;
        }
    }

    public static hd h(byte[] bArr) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        hd hdVarCreateFromParcel = CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return hdVarCreateFromParcel;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        he heVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof hd)) {
            hd hdVar = (hd) obj;
            return this.NZ == hdVar.NZ && this.NX == hdVar.NX && this.NY == hdVar.NY && this.NW == hdVar.NW;
        }
        return false;
    }

    public long getExpirationTime() {
        return this.Oz;
    }

    public double getLatitude() {
        return this.NX;
    }

    public double getLongitude() {
        return this.NY;
    }

    public int getNotificationResponsiveness() {
        return this.Oa;
    }

    @Override // com.google.android.gms.location.Geofence
    public String getRequestId() {
        return this.Jo;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public short hS() {
        return this.NW;
    }

    public float hT() {
        return this.NZ;
    }

    public int hU() {
        return this.NU;
    }

    public int hV() {
        return this.Ob;
    }

    public int hashCode() {
        long jDoubleToLongBits = Double.doubleToLongBits(this.NX);
        int i = ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32))) + 31;
        long jDoubleToLongBits2 = Double.doubleToLongBits(this.NY);
        return (((((((i * 31) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.NZ)) * 31) + this.NW) * 31) + this.NU;
    }

    public String toString() {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", bC(this.NW), this.Jo, Integer.valueOf(this.NU), Double.valueOf(this.NX), Double.valueOf(this.NY), Float.valueOf(this.NZ), Integer.valueOf(this.Oa / 1000), Integer.valueOf(this.Ob), Long.valueOf(this.Oz));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        he heVar = CREATOR;
        he.a(this, parcel, flags);
    }
}
