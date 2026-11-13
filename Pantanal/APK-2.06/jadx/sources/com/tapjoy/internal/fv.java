package com.tapjoy.internal;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class fv implements fj {
    private static final fv a = new fv() { // from class: com.tapjoy.internal.fv.1
        @Override // com.tapjoy.internal.fv, com.tapjoy.internal.fj
        public final void a(String str) {
        }

        @Override // com.tapjoy.internal.fv, com.tapjoy.internal.fj
        public final void b(String str) {
        }

        @Override // com.tapjoy.internal.fv, com.tapjoy.internal.fj
        public final void c(String str) {
        }

        @Override // com.tapjoy.internal.fv, com.tapjoy.internal.fj
        public final void d(String str) {
        }

        @Override // com.tapjoy.internal.fv, com.tapjoy.internal.fj
        public final void a(String str, fg fgVar) {
        }

        @Override // com.tapjoy.internal.fv, com.tapjoy.internal.fj
        public final void a(String str, String str2, fg fgVar) {
        }
    };
    private final fj b;
    private final be c;

    /* synthetic */ fv(byte b) {
        this();
    }

    public static fv a(fj fjVar) {
        if (!(fjVar instanceof fv)) {
            return fjVar != null ? new fv(fjVar) : a;
        }
        throw new IllegalArgumentException();
    }

    private fv() {
        this.b = null;
        this.c = null;
    }

    private fv(fj fjVar) {
        Handler handlerA;
        this.b = fjVar;
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            handlerA = null;
        } else {
            cq.a(looperMyLooper);
            handlerA = looperMyLooper == Looper.getMainLooper() ? x.a() : new Handler(looperMyLooper);
        }
        if (handlerA != null) {
            this.c = x.a(handlerA);
            new Object[1][0] = handlerA.getLooper();
        } else if (Thread.currentThread() == fm.b()) {
            this.c = fm.a;
        } else {
            this.c = x.a(x.a());
        }
    }

    @Override // com.tapjoy.internal.fj
    public void a(final String str) {
        this.c.a(new Runnable() { // from class: com.tapjoy.internal.fv.2
            @Override // java.lang.Runnable
            public final void run() {
                fv.this.b.a(str);
            }
        });
    }

    @Override // com.tapjoy.internal.fj
    public void b(final String str) {
        this.c.a(new Runnable() { // from class: com.tapjoy.internal.fv.3
            @Override // java.lang.Runnable
            public final void run() {
                fv.this.b.b(str);
            }
        });
    }

    @Override // com.tapjoy.internal.fj
    public void c(final String str) {
        this.c.a(new Runnable() { // from class: com.tapjoy.internal.fv.4
            @Override // java.lang.Runnable
            public final void run() {
                fv.this.b.c(str);
            }
        });
    }

    @Override // com.tapjoy.internal.fj
    public void d(final String str) {
        this.c.a(new Runnable() { // from class: com.tapjoy.internal.fv.5
            @Override // java.lang.Runnable
            public final void run() {
                fv.this.b.d(str);
            }
        });
    }

    @Override // com.tapjoy.internal.fj
    public void a(final String str, final fg fgVar) {
        this.c.a(new Runnable() { // from class: com.tapjoy.internal.fv.6
            @Override // java.lang.Runnable
            public final void run() {
                fv.this.b.a(str, fgVar);
            }
        });
    }

    @Override // com.tapjoy.internal.fj
    public void a(final String str, final String str2, final fg fgVar) {
        this.c.a(new Runnable() { // from class: com.tapjoy.internal.fv.7
            @Override // java.lang.Runnable
            public final void run() {
                fv.this.b.a(str, str2, fgVar);
            }
        });
    }
}
