package com.fyber.utils;

import java.lang.Exception;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: AsyncCallable.java */
/* JADX INFO: loaded from: classes.dex */
public final class c<V, T extends Exception> implements Callable<V> {
    private T a;
    private CountDownLatch b = new CountDownLatch(1);
    private V c;

    public final void a(V v) {
        this.c = v;
        this.b.countDown();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: T extends java.lang.Exception */
    @Override // java.util.concurrent.Callable
    public final V call() throws Exception {
        this.b.await(60000L, TimeUnit.MILLISECONDS);
        if (this.a != null) {
            throw this.a;
        }
        return this.c;
    }

    public final void a(T t) {
        this.a = t;
        a((Object) null);
    }
}
