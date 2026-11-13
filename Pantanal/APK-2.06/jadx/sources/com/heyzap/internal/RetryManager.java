package com.heyzap.internal;

import com.heyzap.common.concurrency.HandlerExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class RetryManager {
    private final ScheduledExecutorService executorService;
    private Schedule retrySchedule;
    private ScheduledFuture scheduleFuture;
    private final Runnable task;
    private Object retryLock = new Object();
    private boolean cancelled = false;

    public interface Schedule {
        long getIntervalMillis();

        void incRetries();

        boolean isCanceled();
    }

    public RetryManager(Runnable task, Schedule exponentialSchedule, ScheduledExecutorService executorService) {
        if (task instanceof RetryableTask) {
            ((RetryableTask) task).setRetryManager(this);
        }
        this.task = task;
        this.executorService = executorService;
        this.retrySchedule = exponentialSchedule;
    }

    public void start() {
        if (this.retrySchedule.isCanceled()) {
            cancel();
        } else {
            this.executorService.schedule(this.task, this.retrySchedule.getIntervalMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public boolean isCanceled() {
        return this.cancelled;
    }

    public void retry() {
        retry(this.retrySchedule);
    }

    public void retry(Schedule schedule) {
        if (!this.cancelled) {
            synchronized (this.retryLock) {
                if (schedule.isCanceled()) {
                    cancel();
                } else {
                    schedule.incRetries();
                    this.scheduleFuture = this.executorService.schedule(this.task, schedule.getIntervalMillis(), TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    public void retry(int period, TimeUnit timeUnit) {
        if (!this.cancelled) {
            synchronized (this.retryLock) {
                this.scheduleFuture = this.executorService.schedule(this.task, period, timeUnit);
            }
        }
    }

    public void cancel() {
        this.cancelled = true;
        if (this.task instanceof RetryableTask) {
            ((RetryableTask) this.task).onCancelled();
        }
        ScheduledFuture localFuture = this.scheduleFuture;
        if (localFuture != null) {
            localFuture.cancel(false);
        }
    }

    public void updateSchedule(Schedule schedule) {
        synchronized (this.retryLock) {
            this.retrySchedule = schedule;
        }
    }

    public static class DelayedSchedule implements Schedule {
        public final Schedule delegate;
        public final long initialDelay;
        private AtomicInteger retryCount = new AtomicInteger(0);

        public DelayedSchedule(int initialDelay, TimeUnit timeUnit, Schedule delegate) {
            this.delegate = delegate;
            this.initialDelay = timeUnit.toMillis(initialDelay);
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public void incRetries() {
            this.retryCount.incrementAndGet();
            this.delegate.incRetries();
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public long getIntervalMillis() {
            return this.retryCount.get() > 0 ? this.delegate.getIntervalMillis() : this.initialDelay;
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public boolean isCanceled() {
            return false;
        }
    }

    public static class StaticSchedule implements Schedule {
        private long delay;
        private int times = 0;
        private int timesLimit;

        public StaticSchedule(int delay, TimeUnit timeUnit, int times) {
            this.delay = timeUnit.toMillis(delay);
            this.timesLimit = times;
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public void incRetries() {
            this.times++;
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public long getIntervalMillis() {
            if (this.times == 0) {
                return 0L;
            }
            return this.delay;
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public boolean isCanceled() {
            return this.timesLimit > 0 && this.times > this.timesLimit;
        }
    }

    public static class CustomArraySchedule implements Schedule {
        private AtomicInteger retryCount = new AtomicInteger(0);
        private final int[] seconds;

        public CustomArraySchedule(int... seconds) {
            this.seconds = seconds;
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public void incRetries() {
            this.retryCount.incrementAndGet();
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public long getIntervalMillis() {
            if (this.retryCount.get() == 0) {
                return 0L;
            }
            return this.seconds[Math.min(this.seconds.length, this.retryCount.get()) - 1] * 1000;
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public boolean isCanceled() {
            return this.retryCount.get() >= this.seconds.length;
        }
    }

    public static class ExponentialSchedule implements Schedule {
        private final double exponentBase;
        private final long firstInterval;
        private AtomicInteger retryCount = new AtomicInteger(0);

        public ExponentialSchedule(double exponentBase, long firstInterval, TimeUnit timeUnit) {
            this.firstInterval = timeUnit.toMillis(firstInterval);
            this.exponentBase = exponentBase;
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public void incRetries() {
            this.retryCount.incrementAndGet();
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public long getIntervalMillis() {
            if (this.retryCount.get() == 0) {
                return 0L;
            }
            return (long) (this.firstInterval * Math.pow(this.exponentBase, this.retryCount.get()));
        }

        @Override // com.heyzap.internal.RetryManager.Schedule
        public boolean isCanceled() {
            return false;
        }
    }

    public static abstract class RetryableTask implements Runnable {
        private RetryManager retryManager;

        public boolean retry() {
            return retry(this.retryManager.retrySchedule);
        }

        public boolean retry(Schedule schedule) {
            if (this.retryManager.isCanceled()) {
                return false;
            }
            this.retryManager.retry(schedule);
            return true;
        }

        public void setRetryManager(RetryManager retryManager) {
            this.retryManager = retryManager;
        }

        protected void resetSchedule(Schedule schedule) {
            this.retryManager.updateSchedule(schedule);
        }

        public void onCancelled() {
        }
    }

    public static abstract class UIThreadRetryableTask extends RetryableTask {
        public abstract void runOnUiThread();

        @Override // java.lang.Runnable
        public void run() {
            HandlerExecutorService.getMainThreadExecutor().execute(new Runnable() { // from class: com.heyzap.internal.RetryManager.UIThreadRetryableTask.1
                @Override // java.lang.Runnable
                public void run() {
                    UIThreadRetryableTask.this.runOnUiThread();
                }
            });
        }
    }
}
