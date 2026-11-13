package com.tapjoy.internal;

import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
public abstract class db implements dg {
    private static final Logger a = Logger.getLogger(db.class.getName());
    private final dg b = new dd() { // from class: com.tapjoy.internal.db.1
        @Override // com.tapjoy.internal.dd
        protected final void a() {
            final db dbVar = db.this;
            new Executor() { // from class: com.tapjoy.internal.db.2
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable command) {
                    new Thread(command, db.this.getClass().getSimpleName()).start();
                }
            }.execute(new Runnable() { // from class: com.tapjoy.internal.db.1.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        db.this.a();
                        c();
                        if (f() == dg.a.RUNNING) {
                            try {
                                db.this.b();
                            } catch (Throwable th) {
                                try {
                                    db.this.c();
                                } catch (Exception e) {
                                    db.a.log(Level.WARNING, "Error while attempting to shut down the service after failure.", (Throwable) e);
                                }
                                throw th;
                            }
                        }
                        db.this.c();
                        d();
                    } catch (Throwable th2) {
                        a(th2);
                        throw cs.a(th2);
                    }
                }
            });
        }

        @Override // com.tapjoy.internal.dd
        protected final void b() {
            db.this.d();
        }
    };

    public abstract void b();

    public void a() {
    }

    public void c() {
    }

    public void d() {
    }

    public String toString() {
        return getClass().getSimpleName() + " [" + f() + "]";
    }

    @Override // com.tapjoy.internal.dg
    public final df e() {
        return this.b.e();
    }

    @Override // com.tapjoy.internal.dg
    public final dg.a f() {
        return this.b.f();
    }
}
