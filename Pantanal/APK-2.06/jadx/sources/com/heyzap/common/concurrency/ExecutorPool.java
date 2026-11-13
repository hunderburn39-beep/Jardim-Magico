package com.heyzap.common.concurrency;

import com.heyzap.internal.Logger;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class ExecutorPool {
    private static volatile ExecutorPool ref;
    ScheduledThreadPoolExecutor pool = new WrappedScheduledThreadPoolExecutor(10);

    private ExecutorPool() {
        this.pool.setKeepAliveTime(10L, TimeUnit.SECONDS);
        this.pool.allowCoreThreadTimeOut(true);
        this.pool.setThreadFactory(new ExecutorThreadFactory());
    }

    public static synchronized ScheduledThreadPoolExecutor getInstance() {
        if (ref == null) {
            ref = new ExecutorPool();
        }
        return ref.pool;
    }

    public Object clone() {
        return null;
    }

    class ExecutorThreadFactory implements ThreadFactory {
        ExecutorThreadFactory() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable r) {
            return new Thread(r, "HeyzapThreadPool");
        }
    }

    private static class WrappedScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
        public WrappedScheduledThreadPoolExecutor(int corePoolSize) {
            super(corePoolSize);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
        public void execute(Runnable r) {
            try {
                super.execute(new WrappedRunnable(r, this));
            } catch (RejectedExecutionException e) {
                Logger.error("Runnable rejected because executor is shut down");
            }
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ScheduledExecutorService
        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            try {
                return super.schedule(command, delay, unit);
            } catch (RejectedExecutionException e) {
                Logger.error("Runnable rejected because executor is shut down");
                return null;
            }
        }
    }
}
