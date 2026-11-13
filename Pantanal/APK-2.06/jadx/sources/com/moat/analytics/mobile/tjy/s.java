package com.moat.analytics.mobile.tjy;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
class s implements Runnable {
    final /* synthetic */ n a;

    s(n nVar) {
        this.a = nVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            new Handler(Looper.getMainLooper()).post(new t(this));
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
