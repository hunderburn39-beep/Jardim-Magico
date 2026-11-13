package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
class j implements Runnable {
    final /* synthetic */ i a;

    j(i iVar) {
        this.a = iVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.a.f.get() == null || this.a.e() || !Boolean.valueOf(this.a.i()).booleanValue()) {
                this.a.c();
            } else {
                this.a.d.postDelayed(this, 200L);
            }
        } catch (Exception e) {
            this.a.c();
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
