package com.tapjoy.internal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
public final class de {
    private static final Logger a = Logger.getLogger(de.class.getName());
    private final Queue b = new LinkedList();
    private boolean c = false;

    static class a {
        final Runnable a;
        final Executor b;
    }

    public final void a() {
        synchronized (this.b) {
            if (!this.c) {
                this.c = true;
                while (!this.b.isEmpty()) {
                    a aVar = (a) this.b.poll();
                    try {
                        aVar.b.execute(aVar.a);
                    } catch (RuntimeException e) {
                        a.log(Level.SEVERE, "RuntimeException while executing runnable " + aVar.a + " with executor " + aVar.b, (Throwable) e);
                    }
                }
            }
        }
    }
}
