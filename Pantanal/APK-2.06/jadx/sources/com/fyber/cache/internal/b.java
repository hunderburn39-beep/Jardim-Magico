package com.fyber.cache.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: CacheConfigurationBuilder.java */
/* JADX INFO: loaded from: classes.dex */
public final class b implements Serializable {
    private String a;
    private Integer b;
    private HashMap<a.EnumC0023a, d> c = new HashMap<>();

    public final b a(String str) {
        this.a = str;
        return this;
    }

    public final b a(Integer num) {
        this.b = num;
        return this;
    }

    public final b a(a.EnumC0023a enumC0023a, d dVar) {
        this.c.put(enumC0023a, dVar);
        return this;
    }

    public final a a() {
        a aVar = new a(this.a, this.b);
        for (Map.Entry<a.EnumC0023a, d> entry : this.c.entrySet()) {
            aVar.a(entry.getKey(), entry.getValue());
        }
        return aVar;
    }
}
