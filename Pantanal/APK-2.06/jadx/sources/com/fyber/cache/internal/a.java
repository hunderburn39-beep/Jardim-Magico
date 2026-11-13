package com.fyber.cache.internal;

import java.io.Serializable;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: compiled from: CacheConfiguration.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements Serializable {
    public static final a a = new a("", -1);
    private String b;
    private Integer c;
    private d[] d = new d[EnumC0023a.values().length];

    /* JADX INFO: renamed from: com.fyber.cache.internal.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: CacheConfiguration.java */
    public enum EnumC0023a {
        WIFI,
        CELLULAR
    }

    public a(String str, Integer num) {
        this.b = str;
        if (num == null) {
            num = Integer.valueOf(TimeConstants.SECONDSPERHOUR);
        } else if (num.intValue() < 300) {
            num = 300;
        }
        this.c = num;
    }

    public final String a() {
        return this.b;
    }

    public final int b() {
        return this.c.intValue();
    }

    public final d a(EnumC0023a enumC0023a) {
        return this.d[enumC0023a.ordinal()];
    }

    public final void a(EnumC0023a enumC0023a, d dVar) {
        this.d[enumC0023a.ordinal()] = dVar;
    }

    public final int c() {
        return Math.max(this.d[0].a(), this.d[1].a());
    }
}
