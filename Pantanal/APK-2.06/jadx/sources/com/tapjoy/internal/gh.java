package com.tapjoy.internal;

import android.content.SharedPreferences;
import android.os.SystemClock;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public final class gh {
    final ft a;
    ScheduledFuture c;
    final AtomicBoolean b = new AtomicBoolean();
    private final Runnable d = new Runnable() { // from class: com.tapjoy.internal.gh.1
        @Override // java.lang.Runnable
        public final void run() {
            if (gh.this.b.compareAndSet(true, false)) {
                fr.a("The session ended");
                ft ftVar = gh.this.a;
                long jElapsedRealtime = SystemClock.elapsedRealtime() - ftVar.c;
                fx fxVar = ftVar.a;
                synchronized (fxVar) {
                    long jA = fxVar.c.i.a() + jElapsedRealtime;
                    fxVar.c.i.a(jA);
                    fxVar.b.b(jA);
                }
                gl.c.a aVarD = ftVar.a(gl.i.APP, "session").d(jElapsedRealtime);
                ftVar.a(aVarD);
                ftVar.c = 0L;
                fx fxVar2 = ftVar.a;
                long j = aVarD.e;
                synchronized (fxVar2) {
                    SharedPreferences.Editor editorA = fxVar2.c.a();
                    fxVar2.c.j.a(editorA, j);
                    fxVar2.c.k.a(editorA, jElapsedRealtime);
                    editorA.commit();
                    fxVar2.b.c(j);
                    fxVar2.b.d(jElapsedRealtime);
                }
                final fs fsVar = ftVar.b;
                if (fsVar.b != null) {
                    fsVar.a();
                    new hc() { // from class: com.tapjoy.internal.fs.1
                        AnonymousClass1() {
                        }

                        @Override // com.tapjoy.internal.hc
                        public final boolean a() {
                            return !fs.this.a.c();
                        }
                    }.run();
                }
                fsVar.a.flush();
                eo.d.notifyObservers();
            }
        }
    };
    private final Runnable e = new Runnable() { // from class: com.tapjoy.internal.gh.2
        @Override // java.lang.Runnable
        public final void run() {
        }
    };

    gh(ft ftVar) {
        this.a = ftVar;
    }

    public final void a() {
        if (this.b.get()) {
            if (!Boolean.FALSE.booleanValue()) {
                this.d.run();
            } else {
                if (this.c != null && !this.c.cancel(false)) {
                    return;
                }
                this.c = gi.a.schedule(this.d, 3000L, TimeUnit.MILLISECONDS);
            }
        }
    }
}
