package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
class r implements Runnable {
    final /* synthetic */ q a;

    r(q qVar) {
        this.a = qVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.a.b();
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
