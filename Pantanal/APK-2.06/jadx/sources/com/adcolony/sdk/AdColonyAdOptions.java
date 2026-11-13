package com.adcolony.sdk;

import android.support.annotation.NonNull;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyAdOptions {
    boolean a;
    boolean b;
    AdColonyUserMetadata c;
    JSONObject d = s.a();

    public AdColonyAdOptions enableConfirmationDialog(boolean confirmation_enabled) {
        this.a = confirmation_enabled;
        s.a(this.d, "confirmation_enabled", true);
        return this;
    }

    public AdColonyAdOptions enableResultsDialog(boolean results_enabled) {
        this.b = results_enabled;
        s.a(this.d, "results_enabled", true);
        return this;
    }

    public AdColonyAdOptions setOption(@NonNull String key, boolean value) {
        if (au.d(key)) {
            s.a(this.d, key, value);
        }
        return this;
    }

    public Object getOption(@NonNull String key) {
        return s.a(this.d, key);
    }

    public AdColonyAdOptions setOption(@NonNull String key, double value) {
        if (au.d(key)) {
            s.a(this.d, key, value);
        }
        return this;
    }

    public AdColonyAdOptions setOption(@NonNull String key, @NonNull String value) {
        if (key != null && au.d(key) && au.d(value)) {
            s.a(this.d, key, value);
        }
        return this;
    }

    public AdColonyAdOptions setUserMetadata(@NonNull AdColonyUserMetadata metadata) {
        this.c = metadata;
        s.a(this.d, "user_metadata", metadata.c);
        return this;
    }

    public AdColonyUserMetadata getUserMetadata() {
        return this.c;
    }
}
