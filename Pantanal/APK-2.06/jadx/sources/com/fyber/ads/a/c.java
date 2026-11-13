package com.fyber.ads.a;

import com.fyber.ads.a.c;
import com.fyber.utils.m;
import com.fyber.utils.w;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: InternalAd.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class c<V extends c<V>> {
    private final String a;
    private final String b;
    private Map<String, String> c;
    private String d;
    private final String e;
    private Map<String, Object> f;
    private Map<String, String> g;

    protected c(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.e = str3;
    }

    public final V a(String str, Object obj) {
        if (this.f == null) {
            this.f = new HashMap();
            this.g = new w(this.f);
        }
        this.f.put(str, obj);
        return this;
    }

    public final V a(String str, String str2) {
        if (this.c == null) {
            this.c = new HashMap();
        }
        this.c.put(str, str2);
        return this;
    }

    public final String a() {
        return this.b;
    }

    public final String b() {
        return this.a;
    }

    public final Map<String, String> c() {
        return m.b(this.f) ? this.g : Collections.emptyMap();
    }

    public final <T> T a(String str, Class<T> cls) {
        Object obj;
        if (m.b(this.f) && (obj = this.f.get(str)) != null && cls.isAssignableFrom(obj.getClass())) {
            return cls.cast(obj);
        }
        return null;
    }

    public final <T> T a(String str, Class<T> cls, T t) {
        T t2 = (T) a(str, (Class) cls);
        return t2 != null ? t2 : t;
    }

    public final Map<String, String> d() {
        return this.c;
    }

    public final String e() {
        return this.d;
    }

    public final String f() {
        return this.e;
    }
}
