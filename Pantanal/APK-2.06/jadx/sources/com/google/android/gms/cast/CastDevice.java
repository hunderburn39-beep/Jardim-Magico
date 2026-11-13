package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.eo;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CastDevice implements SafeParcelable {
    public static final Parcelable.Creator<CastDevice> CREATOR = new b();
    private final int xH;
    private String ya;
    String yb;
    private Inet4Address yc;
    private String yd;
    private String ye;
    private String yf;
    private int yg;
    private List<WebImage> yh;

    private CastDevice() {
        this(1, null, null, null, null, null, -1, new ArrayList());
    }

    CastDevice(int versionCode, String deviceId, String hostAddress, String friendlyName, String modelName, String deviceVersion, int servicePort, List<WebImage> icons) {
        this.xH = versionCode;
        this.ya = deviceId;
        this.yb = hostAddress;
        if (this.yb != null) {
            try {
                InetAddress byName = InetAddress.getByName(this.yb);
                if (byName instanceof Inet4Address) {
                    this.yc = (Inet4Address) byName;
                }
            } catch (UnknownHostException e) {
                this.yc = null;
            }
        }
        this.yd = friendlyName;
        this.ye = modelName;
        this.yf = deviceVersion;
        this.yg = servicePort;
        this.yh = icons;
    }

    public static CastDevice getFromBundle(Bundle extras) {
        if (extras == null) {
            return null;
        }
        extras.setClassLoader(CastDevice.class.getClassLoader());
        return (CastDevice) extras.getParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CastDevice)) {
            return false;
        }
        CastDevice castDevice = (CastDevice) obj;
        if (getDeviceId() == null) {
            return castDevice.getDeviceId() == null;
        }
        return eo.a(this.ya, castDevice.ya) && eo.a(this.yc, castDevice.yc) && eo.a(this.ye, castDevice.ye) && eo.a(this.yd, castDevice.yd) && eo.a(this.yf, castDevice.yf) && this.yg == castDevice.yg && eo.a(this.yh, castDevice.yh);
    }

    public String getDeviceId() {
        return this.ya;
    }

    public String getDeviceVersion() {
        return this.yf;
    }

    public String getFriendlyName() {
        return this.yd;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0072  */
    public WebImage getIcon(int preferredWidth, int preferredHeight) {
        WebImage webImage;
        WebImage webImage2 = null;
        if (this.yh.isEmpty()) {
            return null;
        }
        if (preferredWidth <= 0 || preferredHeight <= 0) {
            return this.yh.get(0);
        }
        WebImage webImage3 = null;
        for (WebImage webImage4 : this.yh) {
            int width = webImage4.getWidth();
            int height = webImage4.getHeight();
            if (width < preferredWidth || height < preferredHeight) {
                if (width >= preferredWidth || height >= preferredHeight || (webImage2 != null && (webImage2.getWidth() >= width || webImage2.getHeight() >= height))) {
                    webImage4 = webImage2;
                    webImage = webImage3;
                } else {
                    webImage = webImage3;
                }
            } else if (webImage3 == null || (webImage3.getWidth() > width && webImage3.getHeight() > height)) {
                WebImage webImage5 = webImage2;
                webImage = webImage4;
                webImage4 = webImage5;
            } else {
                webImage4 = webImage2;
                webImage = webImage3;
            }
            webImage3 = webImage;
            webImage2 = webImage4;
        }
        if (webImage3 == null) {
            webImage3 = webImage2 != null ? webImage2 : this.yh.get(0);
        }
        return webImage3;
    }

    public List<WebImage> getIcons() {
        return Collections.unmodifiableList(this.yh);
    }

    public Inet4Address getIpAddress() {
        return this.yc;
    }

    public String getModelName() {
        return this.ye;
    }

    public int getServicePort() {
        return this.yg;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasIcons() {
        return !this.yh.isEmpty();
    }

    public int hashCode() {
        if (this.ya == null) {
            return 0;
        }
        return this.ya.hashCode();
    }

    public boolean isSameDevice(CastDevice castDevice) {
        if (castDevice == null) {
            return false;
        }
        if (getDeviceId() == null) {
            return castDevice.getDeviceId() == null;
        }
        return eo.a(getDeviceId(), castDevice.getDeviceId());
    }

    public void putInBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        bundle.putParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE", this);
    }

    public String toString() {
        return String.format("\"%s\" (%s)", this.yd, this.ya);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        b.a(this, out, flags);
    }
}
