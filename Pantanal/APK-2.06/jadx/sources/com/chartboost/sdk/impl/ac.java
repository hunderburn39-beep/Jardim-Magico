package com.chartboost.sdk.impl;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class ac<T> implements Comparable<ac<T>> {
    public final a b;
    public final String c;
    public final ab d;
    public final File f;
    public final AtomicInteger e = new AtomicInteger();
    public long g = 0;
    public int h = 0;

    public enum a {
        GET,
        POST
    }

    public enum b {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public abstract ae<T> a(ah ahVar);

    public abstract void a(T t);

    public ac(a aVar, String str, ab abVar, File file) {
        this.b = aVar;
        this.c = str;
        this.d = abVar;
        this.f = file;
    }

    public aq a() {
        return new aq(null, null, null);
    }

    public void a(ad adVar) {
        if (this.d != null) {
            this.d.a(adVar);
        }
    }

    public b b() {
        return b.NORMAL;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ac<T> acVar) {
        return -b().compareTo(acVar.b());
    }

    public boolean c() {
        return this.e.compareAndSet(0, -1);
    }
}
