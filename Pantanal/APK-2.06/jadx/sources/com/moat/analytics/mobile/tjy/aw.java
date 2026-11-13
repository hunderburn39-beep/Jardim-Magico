package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
class aw implements Runnable {
    final /* synthetic */ ar a;
    final /* synthetic */ av b;

    aw(av avVar, ar arVar) {
        this.b = avVar;
        this.a = arVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.b.d.a(this.a);
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
