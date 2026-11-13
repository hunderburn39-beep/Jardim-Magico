package com.fyber.cache.internal;

import java.io.Serializable;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: VideoEntry.java */
/* JADX INFO: loaded from: classes.dex */
public final class h implements Serializable {
    private final int a;
    private final String b;

    public h(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public h(JSONObject jSONObject) throws JSONException {
        this.a = jSONObject.getInt("ad_id");
        this.b = jSONObject.getString("url");
    }

    public final int a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final int hashCode() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return obj == this || obj.hashCode() == hashCode();
    }

    public final String toString() {
        return String.format(Locale.ENGLISH, "{\"ad_id\":%d, \"url\":\"%s\"}", Integer.valueOf(this.a), this.b);
    }
}
