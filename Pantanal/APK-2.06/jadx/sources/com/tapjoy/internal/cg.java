package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class cg implements Runnable {
    private final ce a;
    private final cj b;

    protected cg(ce ceVar, cj cjVar) {
        this.a = ceVar;
        this.b = cjVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Object objF = this.a.f();
            if (this.b != null && !(this.b instanceof ck)) {
                this.b.a(this.a, objF);
            }
        } catch (Throwable th) {
            if (this.b != null && !(this.b instanceof ck)) {
                this.b.a(this.a);
            }
        }
    }
}
