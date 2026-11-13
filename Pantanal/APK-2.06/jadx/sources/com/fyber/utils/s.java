package com.fyber.utils;

import android.net.Uri;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.anddev.andengine.level.util.constants.LevelConstants;

/* JADX INFO: compiled from: UrlBuilder.java */
/* JADX INFO: loaded from: classes.dex */
public final class s {
    private static SparseArray<o> a;
    private String b;
    private com.fyber.a.a c;
    private String d;
    private String e;
    private Map<String, String> f;
    private boolean g = false;
    private boolean h = false;
    private boolean i = true;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private boolean n = false;

    private s(String str, com.fyber.a.a aVar) {
        this.b = str;
        this.c = aVar;
    }

    public final s a(Map<String, String> map) {
        if (map != null) {
            h().putAll(map);
        }
        return this;
    }

    public final s a(String str, String str2) {
        if (StringUtils.notNullNorEmpty(str)) {
            h().put(str, str2);
        }
        return this;
    }

    public final s a() {
        this.g = true;
        return this;
    }

    public final s b() {
        this.h = true;
        return this;
    }

    public final s a(boolean z) {
        this.i = z;
        return this;
    }

    public final s c() {
        this.k = true;
        return this;
    }

    public final s d() {
        this.l = true;
        this.j = true;
        return this;
    }

    public final s e() {
        this.m = true;
        return this;
    }

    public final s a(String str) {
        this.d = str;
        return this;
    }

    public final s b(String str) {
        this.e = str;
        return this;
    }

    public final s f() {
        this.n = true;
        return this;
    }

    public final String g() {
        HashMap map = new HashMap();
        a(map, 6);
        if (m.b(this.f)) {
            map.putAll(this.f);
        }
        map.put("appid", this.c.a());
        if (this.i) {
            map.put(LevelConstants.TAG_LEVEL_ATTRIBUTE_UID, this.c.b());
        }
        a(map, 0);
        a(map, 2);
        a(map, 1);
        if (this.e != null) {
            map.put("placement_id", this.e);
        }
        if (this.g) {
            a(map, 3);
        }
        if (this.h) {
            a(map, 4);
        }
        if (this.j) {
            map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        }
        if (this.k) {
            a(map, 7);
        }
        a(map, 5);
        if (StringUtils.notNullNorEmpty(this.d)) {
            map.put("request_id", this.d);
        } else if (this.m) {
            map.put("request_id", UUID.randomUUID().toString());
        }
        Uri.Builder builderBuildUpon = Uri.parse(this.b).buildUpon();
        for (Map.Entry entry : map.entrySet()) {
            builderBuildUpon.appendQueryParameter((String) entry.getKey(), entry.getValue() != null ? (String) entry.getValue() : "");
        }
        if (this.l) {
            String strC = this.c.c();
            if (StringUtils.notNullNorEmpty(strC)) {
                builderBuildUpon.appendQueryParameter("signature", q.a(map, strC));
            } else {
                FyberLogger.d("UrlBuilder", "It was impossible to add the signature, the SecretKey has not been provided");
            }
        }
        if (this.n) {
            builderBuildUpon.scheme("http");
        }
        return builderBuildUpon.build().toString();
    }

    private static void a(Map<String, String> map, int i) {
        o oVar = a.get(i);
        if (oVar != null) {
            map.putAll(oVar.a());
        }
    }

    private Map<String, String> h() {
        if (this.f == null) {
            this.f = new HashMap();
        }
        return this.f;
    }

    public static s a(String str, com.fyber.a.a aVar) {
        return new s(str, aVar);
    }

    static {
        SparseArray<o> sparseArray = new SparseArray<>(8);
        sparseArray.put(6, new g());
        sparseArray.put(3, new h.e());
        sparseArray.put(4, new h.d());
        sparseArray.put(7, new h.f());
        sparseArray.put(5, new h.c());
        sparseArray.put(2, new h.b());
        sparseArray.put(1, new h.a());
        sparseArray.put(0, new p());
        a = sparseArray;
    }
}
