package com.tapjoy.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/* JADX INFO: loaded from: classes.dex */
public abstract class dc implements df {
    private final a a = new a();
    private final de b = new de();

    @Override // java.util.concurrent.Future
    public Object get(long timeout, TimeUnit unit) throws TimeoutException {
        a aVar = this.a;
        if (!aVar.tryAcquireSharedNanos(-1, unit.toNanos(timeout))) {
            throw new TimeoutException("Timeout waiting for task.");
        }
        return aVar.a();
    }

    @Override // java.util.concurrent.Future
    public Object get() {
        a aVar = this.a;
        aVar.acquireSharedInterruptibly(-1);
        return aVar.a();
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.a.b();
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.a.c();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!this.a.a(null, null, 4)) {
            return false;
        }
        this.b.a();
        return true;
    }

    protected final boolean a(Object obj) {
        boolean zA = this.a.a(obj, null, 2);
        if (zA) {
            this.b.a();
        }
        return zA;
    }

    protected final boolean a(Throwable th) {
        boolean zA = this.a.a(null, (Throwable) cq.a(th), 2);
        if (zA) {
            this.b.a();
        }
        if (th instanceof Error) {
            throw ((Error) th);
        }
        return zA;
    }

    static final class a extends AbstractQueuedSynchronizer {
        private Object a;
        private Throwable b;

        a() {
        }

        @Override // java.util.concurrent.locks.AbstractQueuedSynchronizer
        protected final int tryAcquireShared(int ignored) {
            return b() ? 1 : -1;
        }

        @Override // java.util.concurrent.locks.AbstractQueuedSynchronizer
        protected final boolean tryReleaseShared(int finalState) {
            setState(finalState);
            return true;
        }

        final Object a() throws ExecutionException {
            int state = getState();
            switch (state) {
                case 2:
                    if (this.b != null) {
                        throw new ExecutionException(this.b);
                    }
                    return this.a;
                case 3:
                default:
                    throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
                case 4:
                    throw new CancellationException("Task was cancelled.");
            }
        }

        final boolean b() {
            return (getState() & 6) != 0;
        }

        final boolean c() {
            return getState() == 4;
        }

        final boolean a(Object obj, Throwable th, int i) {
            boolean zCompareAndSetState = compareAndSetState(0, 1);
            if (zCompareAndSetState) {
                this.a = obj;
                this.b = th;
                releaseShared(i);
            } else if (getState() == 1) {
                acquireShared(-1);
            }
            return zCompareAndSetState;
        }
    }
}
