package com.google.android.gms.internal;

import android.os.Process;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class dp {
    private static final ThreadFactory ra = new ThreadFactory() { // from class: com.google.android.gms.internal.dp.2
        private final AtomicInteger rd = new AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AdWorker #" + this.rd.getAndIncrement());
        }
    };
    private static final ThreadPoolExecutor rb = new ThreadPoolExecutor(0, 10, 65, TimeUnit.SECONDS, new SynchronousQueue(true), ra);

    public static void execute(final Runnable task) {
        try {
            rb.execute(new Runnable() { // from class: com.google.android.gms.internal.dp.1
                @Override // java.lang.Runnable
                public void run() {
                    Process.setThreadPriority(10);
                    task.run();
                }
            });
        } catch (RejectedExecutionException e) {
            dw.c("Too many background threads already running. Aborting task.  Current pool size: " + getPoolSize(), e);
        }
    }

    public static int getPoolSize() {
        return rb.getPoolSize();
    }
}
