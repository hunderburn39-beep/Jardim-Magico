package com.moat.analytics.mobile.tjy;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
class as implements ap {
    private static final AtomicReference a = new AtomicReference();
    private static final Queue b = new ConcurrentLinkedQueue();
    private static volatile ar c = ar.OFF;
    private static volatile boolean d = false;
    private static volatile int e = 200;

    as(aa aaVar) {
        at atVar = null;
        if (a.get() == null) {
            if (a.compareAndSet(null, Executors.newSingleThreadExecutor(new at(this)))) {
                ((ExecutorService) a.get()).submit(new av("TJY", aaVar, new au(this), atVar));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        synchronized (b) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Iterator it = b.iterator();
            while (it.hasNext()) {
                aq aqVar = (aq) it.next();
                if (aqVar.c()) {
                    it.remove();
                } else if (jCurrentTimeMillis - aqVar.d() >= 300000) {
                    it.remove();
                }
            }
            if (b.size() >= 15) {
                for (int i = 0; i < 5; i++) {
                    b.remove();
                }
            }
        }
    }

    @Override // com.moat.analytics.mobile.tjy.ap
    public ar a() {
        return c;
    }

    @Override // com.moat.analytics.mobile.tjy.ap
    public void a(aq aqVar) {
        g();
        b.add(aqVar);
    }

    @Override // com.moat.analytics.mobile.tjy.ap
    public boolean b() {
        return d;
    }

    @Override // com.moat.analytics.mobile.tjy.ap
    public int c() {
        return e;
    }
}
