package com.adcolony.sdk;

/* JADX INFO: loaded from: classes.dex */
abstract class bp {
    private bp a;
    private boolean b;
    private boolean c;
    private boolean d;
    private bs e;

    abstract void a();

    bp(bq bqVar) {
        this(bqVar, null);
    }

    bp(bq bqVar, bp bpVar) {
        this(bqVar, bpVar, true);
    }

    bp(bq bqVar, bp bpVar, boolean z) {
        this.b = true;
        this.c = false;
        this.d = false;
        this.e = null;
        this.a = bpVar;
        if (z) {
            this.d = true;
            bqVar.a(this);
        }
    }

    synchronized void b() {
        a(true);
    }

    synchronized void a(boolean z) {
        a(z, bn.a.YVOLVER_ERROR_UNKNOWN);
    }

    synchronized void a(boolean z, bn.a aVar) {
        this.b = z;
        this.d = true;
        if (!z) {
            this.e = new bs(aVar, "Yvolver error when setSuccess");
        }
    }

    synchronized void a(bs bsVar) {
        this.b = false;
        this.e = bsVar;
    }

    synchronized bs c() {
        return this.e;
    }

    synchronized void d() {
        this.c = true;
        this.d = true;
    }

    synchronized boolean e() {
        return this.d;
    }

    synchronized boolean f() {
        return !this.b;
    }

    synchronized boolean g() {
        return this.b;
    }

    synchronized boolean h() {
        return this.c;
    }

    boolean i() {
        return this.a == null || this.a.g();
    }

    synchronized bp j() {
        return this.a;
    }
}
