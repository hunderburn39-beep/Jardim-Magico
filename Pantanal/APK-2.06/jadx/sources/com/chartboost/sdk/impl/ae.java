package com.chartboost.sdk.impl;

/* JADX INFO: loaded from: classes.dex */
public class ae<T> {
    public final T a;
    public final Exception b;

    public boolean a() {
        return this.b == null;
    }

    public static <T> ae<T> a(T t) {
        return new ae<>(t, null);
    }

    public static <T> ae<T> b() {
        return new ae<>(null, null);
    }

    public static <T> ae<T> a(Exception exc) {
        return new ae<>(null, exc);
    }

    private ae(T t, Exception exc) {
        this.a = t;
        this.b = exc;
    }
}
