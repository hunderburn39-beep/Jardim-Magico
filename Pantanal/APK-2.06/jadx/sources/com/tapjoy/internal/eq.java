package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class eq {
    public volatile fa.a a;
    public int b;
    public volatile fa.a c;
    public volatile fa.a d;

    public final void a() {
        a(16);
        fa.a aVar = this.c;
        if (aVar != null) {
            this.c = null;
            aVar.b().c();
        }
    }

    public final synchronized void a(int i) {
        fa.a aVar = this.a;
        if (aVar != null && this.b < i) {
            this.b |= i;
            aVar.a("state", Integer.valueOf(this.b)).b().c();
        }
    }
}
