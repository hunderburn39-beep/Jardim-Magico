package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class WebImage implements SafeParcelable {
    public static final Parcelable.Creator<WebImage> CREATOR = new b();
    private final Uri Cu;
    private final int kr;
    private final int ks;
    private final int xH;

    WebImage(int versionCode, Uri url, int width, int height) {
        this.xH = versionCode;
        this.Cu = url;
        this.kr = width;
        this.ks = height;
    }

    public WebImage(Uri url) throws IllegalArgumentException {
        this(url, 0, 0);
    }

    public WebImage(Uri url, int width, int height) throws IllegalArgumentException {
        this(1, url, width, height);
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject json) throws IllegalArgumentException {
        this(d(json), json.optInt("width", 0), json.optInt("height", 0));
    }

    private static Uri d(JSONObject jSONObject) {
        if (!jSONObject.has("url")) {
            return null;
        }
        try {
            return Uri.parse(jSONObject.getString("url"));
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject dB() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.Cu.toString());
            jSONObject.put("width", this.kr);
            jSONObject.put("height", this.ks);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) other;
        return fo.equal(this.Cu, webImage.Cu) && this.kr == webImage.kr && this.ks == webImage.ks;
    }

    public int getHeight() {
        return this.ks;
    }

    public Uri getUrl() {
        return this.Cu;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int getWidth() {
        return this.kr;
    }

    public int hashCode() {
        return fo.hashCode(this.Cu, Integer.valueOf(this.kr), Integer.valueOf(this.ks));
    }

    public String toString() {
        return String.format("Image %dx%d %s", Integer.valueOf(this.kr), Integer.valueOf(this.ks), this.Cu.toString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        b.a(this, out, flags);
    }
}
