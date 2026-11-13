package com.moat.analytics.mobile.tjy;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
class q implements Runnable {
    final /* synthetic */ n a;

    q(n nVar) {
        this.a = nVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            new Handler(Looper.getMainLooper()).post(new r(this));
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
