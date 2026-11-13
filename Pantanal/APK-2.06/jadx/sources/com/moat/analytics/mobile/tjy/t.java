package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
class t implements Runnable {
    final /* synthetic */ s a;

    t(s sVar) {
        this.a = sVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.a.c();
        } catch (com.moat.analytics.mobile.tjy.base.exception.b e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
