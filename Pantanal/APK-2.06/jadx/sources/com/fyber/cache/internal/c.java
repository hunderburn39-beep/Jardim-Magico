package com.fyber.cache.internal;

import android.text.TextUtils;
import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CacheEntry.java */
/* JADX INFO: loaded from: classes.dex */
public final class c implements Serializable {
    private final File a;
    private final String b;
    private int c;
    private Set<h> d;
    private long e;
    private int f;

    public c(File file, String str, int i) {
        this.d = new HashSet();
        this.f = 0;
        this.a = file;
        this.b = str;
        this.c = i;
        h();
    }

    public c(JSONObject jSONObject) throws JSONException {
        this.d = new HashSet();
        this.f = 0;
        this.a = new File(jSONObject.getString("local_file"));
        this.b = jSONObject.getString("remote_url");
        this.c = jSONObject.getInt("download_state");
        this.f = jSONObject.getInt("retries");
        this.e = jSONObject.getLong("created_at");
        this.d = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("video_entries");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            this.d.add(new h(jSONArray.getJSONObject(i)));
        }
    }

    public final File a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    public final void a(int i) {
        this.c = i;
        if (i == 1) {
            this.f++;
        }
    }

    public final int d() {
        return this.f;
    }

    public final void e() {
        this.f = 0;
    }

    public final Set<h> f() {
        return this.d;
    }

    public final boolean a(h hVar) {
        boolean zAdd = this.d.add(hVar);
        h();
        return zAdd;
    }

    private void h() {
        this.e = Calendar.getInstance().getTimeInMillis();
    }

    public final long g() {
        return this.e;
    }

    public final String toString() {
        return String.format(Locale.ENGLISH, "{\"local_file\":\"%s\", \"remote_url\":\"%s\",\"download_state\":%d,\"created_at\":%d,\"retries\":%d,\"video_entries\":[%s]}", this.a.getAbsolutePath(), this.b, Integer.valueOf(this.c), Long.valueOf(this.e), Integer.valueOf(this.f), TextUtils.join(",", this.d));
    }

    public final int hashCode() {
        return this.b.hashCode();
    }

    public final boolean equals(Object obj) {
        return obj == this || obj.hashCode() == hashCode();
    }
}
