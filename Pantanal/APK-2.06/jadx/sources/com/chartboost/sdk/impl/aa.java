package com.chartboost.sdk.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class aa {
    private static ExecutorService a = null;
    private static ThreadFactory b = null;

    public static ExecutorService a() {
        if (b == null) {
            b = new ThreadFactory() { // from class: com.chartboost.sdk.impl.aa.1
                private final AtomicInteger a = new AtomicInteger(1);

                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable r) {
                    return new Thread(r, "Chartboost Thread #" + this.a.getAndIncrement());
                }
            };
        }
        if (a == null) {
            a = Executors.newFixedThreadPool(2, b);
        }
        return a;
    }

    public static void b() {
        final Semaphore semaphore = new Semaphore(0);
        final Semaphore semaphore2 = new Semaphore(0);
        Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.impl.aa.2
            @Override // java.lang.Runnable
            public void run() {
                semaphore.release(1);
                semaphore2.acquireUninterruptibly();
            }
        };
        ExecutorService executorServiceA = a();
        for (int i = 0; i < 2; i++) {
            try {
                executorServiceA.execute(runnable);
            } catch (Throwable th) {
                semaphore2.release(2);
                throw th;
            }
        }
        semaphore.acquireUninterruptibly(2);
        semaphore2.release(2);
    }
}
