package com.heyzap.common.concurrency;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class HandlerExecutorService implements ExecutorService {
    private static ExecutorService mainThreadExecutor;
    private final Handler handler;

    public HandlerExecutorService(Handler handler) {
        this.handler = handler;
    }

    public static synchronized ExecutorService getMainThreadExecutor() {
        if (mainThreadExecutor == null) {
            mainThreadExecutor = new HandlerExecutorService(new Handler(Looper.getMainLooper()));
        }
        return mainThreadExecutor;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(final Callable<T> task) {
        final SettableFuture<T> future = SettableFuture.create();
        this.handler.post(new Runnable() { // from class: com.heyzap.common.concurrency.HandlerExecutorService.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    future.set(task.call());
                } catch (Exception e) {
                    future.setException(e);
                }
            }
        });
        return future;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Runnable task, final T result) {
        final SettableFuture<T> future = SettableFuture.create();
        final WrappedRunnable wrappedTask = new WrappedRunnable(task, this);
        this.handler.post(new Runnable() { // from class: com.heyzap.common.concurrency.HandlerExecutorService.2
            @Override // java.lang.Runnable
            public void run() {
                wrappedTask.run();
                future.set(result);
            }
        });
        return future;
    }

    @Override // java.util.concurrent.ExecutorService
    public Future<?> submit(Runnable task) {
        return submit(task, true);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable command) {
        submit(command);
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        return new ArrayList();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        throw new RuntimeException("not implemented");
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        throw new RuntimeException("not implemented");
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws ExecutionException, InterruptedException {
        throw new RuntimeException("not implemented");
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        throw new RuntimeException("not implemented");
    }
}
