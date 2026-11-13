package com.heyzap.common.concurrency;

import com.heyzap.internal.Preconditions;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractFuture<V> implements ListenableFuture<V> {
    private final Sync<V> sync = new Sync<>();
    private final ExecutionList executionList = new ExecutionList();

    @Override // java.util.concurrent.Future
    public V get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return this.sync.get(unit.toNanos(timeout));
    }

    @Override // java.util.concurrent.Future
    public V get() throws ExecutionException, InterruptedException {
        return this.sync.get();
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.sync.isDone();
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.sync.isCancelled();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!this.sync.cancel()) {
            return false;
        }
        done();
        if (mayInterruptIfRunning) {
            interruptTask();
        }
        return true;
    }

    protected void interruptTask() {
    }

    @Override // com.heyzap.common.concurrency.ListenableFuture
    public void addListener(Runnable listener, Executor exec) {
        this.executionList.add(listener, exec);
    }

    public <O> SettableFuture<O> addHandler(final FutureHandler<V, O> handler, Executor exec) {
        final SettableFuture<O> settableFutureCreate = SettableFuture.create();
        addListener(new Runnable() { // from class: com.heyzap.common.concurrency.AbstractFuture.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                Object obj = null;
                Exception exception = null;
                try {
                    obj = AbstractFuture.this.get();
                } catch (InterruptedException e) {
                    exception = e;
                } catch (ExecutionException e2) {
                    exception = e2;
                }
                try {
                    settableFutureCreate.set(handler.handle(obj, exception));
                } catch (Exception e3) {
                    settableFutureCreate.setException(e3);
                }
            }
        }, exec);
        return settableFutureCreate;
    }

    protected boolean set(V value) {
        boolean result = this.sync.set(value);
        if (result) {
            done();
        }
        return result;
    }

    protected boolean setException(Throwable throwable) throws Throwable {
        boolean result = this.sync.setException((Throwable) Preconditions.checkNotNull(throwable));
        if (result) {
            done();
        }
        if (throwable instanceof Error) {
            throw ((Error) throwable);
        }
        return result;
    }

    @Deprecated
    protected final boolean cancel() {
        boolean result = this.sync.cancel();
        if (result) {
            done();
        }
        return result;
    }

    @Deprecated
    protected void done() {
        this.executionList.execute();
    }

    static final class Sync<V> extends AbstractQueuedSynchronizer {
        static final int CANCELLED = 4;
        static final int COMPLETED = 2;
        static final int COMPLETING = 1;
        static final int RUNNING = 0;
        private static final long serialVersionUID = 0;
        private Throwable exception;
        private V value;

        Sync() {
        }

        @Override // java.util.concurrent.locks.AbstractQueuedSynchronizer
        protected int tryAcquireShared(int ignored) {
            return isDone() ? 1 : -1;
        }

        @Override // java.util.concurrent.locks.AbstractQueuedSynchronizer
        protected boolean tryReleaseShared(int finalState) {
            setState(finalState);
            return true;
        }

        V get(long nanos) throws ExecutionException, CancellationException, InterruptedException, TimeoutException {
            if (!tryAcquireSharedNanos(-1, nanos)) {
                throw new TimeoutException("Timeout waiting for task.");
            }
            return getValue();
        }

        V get() throws ExecutionException, CancellationException, InterruptedException {
            acquireSharedInterruptibly(-1);
            return getValue();
        }

        private V getValue() throws ExecutionException, CancellationException {
            int state = getState();
            switch (state) {
                case 2:
                    if (this.exception != null) {
                        throw new ExecutionException(this.exception);
                    }
                    return this.value;
                case 3:
                default:
                    throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
                case 4:
                    throw new CancellationException("Task was cancelled.");
            }
        }

        boolean isDone() {
            return (getState() & 6) != 0;
        }

        boolean isCancelled() {
            return getState() == 4;
        }

        boolean set(V v) {
            return complete(v, null, 2);
        }

        boolean setException(Throwable t) {
            return complete(null, t, 2);
        }

        boolean cancel() {
            return complete(null, null, 4);
        }

        private boolean complete(V v, Throwable t, int finalState) {
            if (!compareAndSetState(0, 1)) {
                return false;
            }
            this.value = v;
            this.exception = t;
            releaseShared(finalState);
            return true;
        }
    }
}
