package com.moat.analytics.mobile.tjy;

import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
class at implements ThreadFactory {
    final /* synthetic */ as a;

    at(as asVar) {
        this.a = asVar;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "MoatStatus");
        thread.setDaemon(true);
        return thread;
    }
}
