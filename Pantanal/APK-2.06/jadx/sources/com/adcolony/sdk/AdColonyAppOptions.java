package com.adcolony.sdk;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import com.google.android.gms.cast.MediaStatus;
import com.tapjoy.TapjoyConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyAppOptions {
    public static final int LANDSCAPE = 1;
    public static final int PORTRAIT = 0;
    public static final int SENSOR = 2;
    String[] b;
    AdColonyUserMetadata e;
    String a = "";
    JSONArray c = s.b();
    JSONObject d = s.a();

    public AdColonyAppOptions() {
        setOriginStore("google");
        if (a.m != null && a.m.v != null) {
            a(a.m.v.a);
            a(a.m.v.b);
        }
    }

    public String getAppID() {
        return this.a;
    }

    public AdColonyAppOptions setAppVersion(@NonNull String app_version) {
        if (au.d(app_version)) {
            setOption(TapjoyConstants.TJC_APP_VERSION_NAME, app_version);
        }
        return this;
    }

    public String getAppVersion() {
        return s.b(this.d, TapjoyConstants.TJC_APP_VERSION_NAME);
    }

    public AdColonyAppOptions setUserID(@NonNull String user_id) {
        if (au.d(user_id)) {
            setOption("user_id", user_id);
        }
        return this;
    }

    public String getUserID() {
        return s.b(this.d, "user_id");
    }

    public AdColonyAppOptions setOption(@NonNull String key, boolean value) {
        if (au.d(key)) {
            s.a(this.d, key, value);
        }
        return this;
    }

    public Object getOption(@NonNull String key) {
        return s.a(this.d, key);
    }

    public AdColonyAppOptions setOption(@NonNull String key, double value) {
        if (au.d(key)) {
            s.a(this.d, key, value);
        }
        return this;
    }

    public AdColonyAppOptions setOption(@NonNull String key, @NonNull String value) {
        if (key != null && au.d(key) && au.d(value)) {
            s.a(this.d, key, value);
        }
        return this;
    }

    public AdColonyAppOptions setOriginStore(@NonNull String origin_store) {
        if (au.d(origin_store)) {
            setOption("origin_store", origin_store);
        }
        return this;
    }

    public String getOriginStore() {
        return s.b(this.d, "origin_store");
    }

    public AdColonyAppOptions setRequestedAdOrientation(@IntRange(from = 0, to = MediaStatus.COMMAND_SEEK) int orientation) {
        setOption("orientation", orientation);
        return this;
    }

    public int getRequestedAdOrientation() {
        return s.a(this.d, "orientation", -1);
    }

    public AdColonyAppOptions setUserMetadata(@NonNull AdColonyUserMetadata metadata) {
        this.e = metadata;
        s.a(this.d, "user_metadata", metadata.c);
        return this;
    }

    public AdColonyAppOptions setMultiWindowEnabled(boolean enabled) {
        s.a(this.d, "multi_window_enabled", enabled);
        return this;
    }

    public boolean getMultiWindowEnabled() {
        return s.d(this.d, "multi_window_enabled");
    }

    public AdColonyUserMetadata getUserMetadata() {
        return this.e;
    }

    void a(String str) {
        if (str != null) {
            this.a = str;
            s.a(this.d, TapjoyConstants.TJC_APP_ID, str);
        }
    }

    void a(String... strArr) {
        if (strArr != null) {
            this.b = strArr;
            this.c = s.b();
            for (String str : strArr) {
                s.a(this.c, str);
            }
        }
    }
}
