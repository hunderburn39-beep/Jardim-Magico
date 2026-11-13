package com.fyber.b;

import android.os.Process;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: ExecutorService.java */
/* JADX INFO: loaded from: classes.dex */
public final class e extends ThreadPoolExecutor {
    public e() {
        super(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new b());
    }

    /* JADX INFO: compiled from: ExecutorService.java */
    static class b implements ThreadFactory {
        b() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return new a(runnable);
        }
    }

    /* JADX INFO: compiled from: ExecutorService.java */
    private static class a extends Thread {
        public a(Runnable runnable) {
            super(runnable);
            setName("FyberExecutorThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }
}
