package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
class g implements Runnable {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.a("Shutting down.");
            this.a.l.b();
            this.a.l = null;
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
