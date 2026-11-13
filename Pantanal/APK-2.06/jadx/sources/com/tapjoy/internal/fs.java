package com.tapjoy.internal;

import java.io.File;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class fs implements Runnable {
    final gg a;
    ch b;
    private final Object c;
    private final Thread d;
    private boolean e;

    public fs(File file) {
        this.a = new gg(file);
        this.c = this.a;
        new Object[1][0] = Integer.valueOf(this.a.b());
        this.d = new Thread(this, "5Rocks");
        this.d.start();
    }

    @Override // java.lang.Runnable
    public final void run() {
        Exception exc;
        int i = 1;
        while (true) {
            long jMin = 0;
            while (this.b != null && this.a.b() > 0 && jMin <= 0) {
                try {
                    try {
                        if (this.a.b() > 10000) {
                            this.a.a(this.a.b() - 10000);
                        }
                        gl.c cVarB = this.a.b(0);
                        if (cVarB == null) {
                            break;
                        }
                        if (cVarB.p() && !cVarB.j.u()) {
                            gi.c.await(3L, TimeUnit.SECONDS);
                        }
                        if (!y.c()) {
                            gi.b.await(3L, TimeUnit.SECONDS);
                        }
                        jMin = (this.e || cVarB.c == gl.i.APP || this.a.b() >= 100 || cVarB.d > System.currentTimeMillis()) ? 0L : Math.min(Math.max((cVarB.d + 60000) - System.currentTimeMillis(), 0L), 60000L);
                        if (jMin <= 0) {
                            ha haVar = new ha();
                            haVar.a(cVarB);
                            new Object[1][0] = cVarB;
                            for (int i2 = 1; i2 < 100 && i2 < this.a.b(); i2++) {
                                gl.c cVarB2 = this.a.b(i2);
                                if (cVarB2 == null || !haVar.a(cVarB2)) {
                                    break;
                                }
                                new Object[1][0] = cVarB2;
                            }
                            int i3 = i + 1;
                            try {
                                Object[] objArr = {Integer.valueOf(haVar.c.f()), Integer.valueOf(i3)};
                                this.b.a(haVar);
                                this.a.a(haVar.c.f());
                                try {
                                    new Object[1][0] = Integer.valueOf(haVar.c.f());
                                    i = 0;
                                } catch (Exception e) {
                                    exc = e;
                                    i = 0;
                                    Object[] objArr2 = {Integer.valueOf(haVar.c.f()), exc};
                                    jMin = 300000;
                                }
                            } catch (Exception e2) {
                                exc = e2;
                                i = i3;
                            }
                        }
                    } catch (InterruptedException e3) {
                        return;
                    }
                } catch (Exception e4) {
                    return;
                }
            }
            this.a.flush();
            if (jMin > 0) {
                synchronized (this.c) {
                    this.e = false;
                    new Object[1][0] = Long.valueOf(jMin);
                    this.c.wait(jMin);
                }
            } else {
                synchronized (this.c) {
                    this.e = false;
                    if (this.b == null || this.a.c()) {
                        this.c.wait();
                    }
                }
            }
        }
    }

    final void a(boolean z) {
        synchronized (this.c) {
            this.e = z;
            this.c.notify();
        }
    }

    public final void a() {
        if (this.b != null && !this.a.c()) {
            a(true);
        }
    }
}
