package com.adcolony.sdk;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class by implements Runnable {
    private Thread b;
    private ArrayList<bx> a = new ArrayList<>();
    private Object c = new Object();
    private Object d = new Object();
    private Boolean e = false;
    private boolean f = false;

    by() {
    }

    private static String d() {
        return "AdColonyPubServices";
    }

    public void a(bx bxVar) {
        synchronized (this.c) {
            this.a.add(bxVar);
        }
    }

    public void a() {
        if (!e()) {
            this.b = new Thread(this);
            this.b.start();
            this.f = true;
        }
    }

    private boolean e() {
        return this.b != null && this.b.isAlive() && this.f;
    }

    public void b() {
        bw.b(d(), "Signalling pump manager.", true);
        synchronized (this.d) {
            this.e = true;
            this.d.notifyAll();
        }
    }

    public void c() {
        Boolean bool = false;
        synchronized (this.d) {
            if (!this.e.booleanValue()) {
                bool = true;
            }
            this.e = false;
            if (bool.booleanValue()) {
                bw.b(d(), "Pump manager waiting", true);
                try {
                    this.d.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bw.b(d(), "Pump manager DONE waiting.", true);
            }
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x004c */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void run() {
        try {
            ArrayList arrayList = new ArrayList();
            while (true) {
                bw.b(d(), "Starting to process managers.", true);
                arrayList.clear();
                synchronized (this.c) {
                    arrayList.addAll(this.a);
                }
                for (bx bxVar : ck.a((Iterable) arrayList)) {
                    if (bxVar != null) {
                        bxVar.b();
                    }
                }
                bw.b(d(), "Done updating managers.", true);
                c();
            }
        } catch (Exception e) {
            bw.b(d(), "Unhandled exception caught in internal message pump");
            bv.aN().a(e, "Unhandled exception caught in internal message pump", true);
        }
    }
}
