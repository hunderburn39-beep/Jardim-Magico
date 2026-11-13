package com.tapjoy.internal;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public abstract class dd implements dg {
    private final a b;
    private final a c;
    private final ReentrantLock a = new ReentrantLock();
    private dg.a d = dg.a.NEW;
    private boolean e = false;

    protected abstract void a();

    protected abstract void b();

    public dd() {
        byte b = 0;
        this.b = new a(this, b);
        this.c = new a(this, b);
    }

    @Override // com.tapjoy.internal.dg
    public final df e() {
        this.a.lock();
        try {
            if (this.d == dg.a.NEW) {
                this.d = dg.a.STARTING;
                a();
            }
        } catch (Throwable th) {
            a(th);
        } finally {
            this.a.unlock();
        }
        return this.b;
    }

    private df g() {
        this.a.lock();
        try {
            if (this.d == dg.a.NEW) {
                this.d = dg.a.TERMINATED;
                this.b.a(dg.a.TERMINATED);
                this.c.a(dg.a.TERMINATED);
            } else if (this.d == dg.a.STARTING) {
                this.e = true;
                this.b.a(dg.a.STOPPING);
            } else if (this.d == dg.a.RUNNING) {
                this.d = dg.a.STOPPING;
                b();
            }
        } catch (Throwable th) {
            a(th);
        } finally {
            this.a.unlock();
        }
        return this.c;
    }

    protected final void c() {
        this.a.lock();
        try {
            if (this.d != dg.a.STARTING) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStarted() when the service is " + this.d);
                a(illegalStateException);
                throw illegalStateException;
            }
            this.d = dg.a.RUNNING;
            if (this.e) {
                g();
            } else {
                this.b.a(dg.a.RUNNING);
            }
            this.a.unlock();
        } catch (Throwable th) {
            this.a.unlock();
            throw th;
        }
    }

    protected final void d() {
        this.a.lock();
        try {
            if (this.d != dg.a.STOPPING && this.d != dg.a.RUNNING) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStopped() when the service is " + this.d);
                a(illegalStateException);
                throw illegalStateException;
            }
            this.d = dg.a.TERMINATED;
            this.c.a(dg.a.TERMINATED);
            this.a.unlock();
        } catch (Throwable th) {
            this.a.unlock();
            throw th;
        }
    }

    protected final void a(Throwable th) {
        cq.a(th);
        this.a.lock();
        try {
            if (this.d == dg.a.STARTING) {
                this.b.a(th);
                this.c.a((Throwable) new Exception("Service failed to start.", th));
            } else if (this.d == dg.a.STOPPING) {
                this.c.a(th);
            } else if (this.d == dg.a.RUNNING) {
                this.c.a((Throwable) new Exception("Service failed while running", th));
            } else if (this.d == dg.a.NEW || this.d == dg.a.TERMINATED) {
                throw new IllegalStateException("Failed while in state:" + this.d, th);
            }
            this.d = dg.a.FAILED;
            this.a.unlock();
        } catch (Throwable th2) {
            this.a.unlock();
            throw th2;
        }
    }

    @Override // com.tapjoy.internal.dg
    public final dg.a f() {
        dg.a aVar;
        this.a.lock();
        try {
            if (this.e && this.d == dg.a.STARTING) {
                aVar = dg.a.STOPPING;
            } else {
                aVar = this.d;
            }
            return aVar;
        } finally {
            this.a.unlock();
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " [" + f() + "]";
    }

    class a extends dc {
        private a() {
        }

        /* synthetic */ a(dd ddVar, byte b) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Override // com.tapjoy.internal.dc, java.util.concurrent.Future
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public dg.a get(long j, TimeUnit timeUnit) throws TimeoutException {
            try {
                return (dg.a) super.get(j, timeUnit);
            } catch (TimeoutException e) {
                throw new TimeoutException(dd.this.toString());
            }
        }
    }
}
