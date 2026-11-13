package com.adcolony.sdk;

import java.util.Iterator;
import java.util.concurrent.Executors;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class aj implements Runnable {
    long c;
    long d;
    long e;
    long f;
    long g;
    long h;
    long i;
    boolean l;
    boolean m;
    boolean n;
    boolean o;
    boolean p;
    boolean q;
    boolean r;
    final long a = 30000;
    final long b = 5000;
    boolean j = true;
    boolean k = true;

    aj() {
    }

    public void a() {
        a.a("SessionInfo.stopped", new ac() { // from class: com.adcolony.sdk.aj.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                aj.this.q = true;
            }
        });
    }

    @Override // java.lang.Runnable
    public void run() {
        while (!this.o) {
            this.f = System.currentTimeMillis();
            a.a();
            if (this.d >= 30000) {
                u.d.a("Ending session due to excessive suspend time: ").b(this.d);
                break;
            }
            if (!this.j) {
                if (this.l && !this.k) {
                    this.l = false;
                    c();
                }
                if (!this.p && a.l != null && a.l.isFinishing()) {
                    this.p = true;
                    this.h = 0L;
                }
                if (this.p) {
                    this.h += this.e;
                    if (this.h > 5000) {
                        u.d.b("Ending session due to excessive time between an Activity finishing and an onResume() event.");
                        break;
                    }
                }
                this.d += this.e;
            } else {
                if (this.l && this.k) {
                    this.l = false;
                    this.p = false;
                    d();
                }
                this.d = 0L;
            }
            this.e = (this.j || this.d < 5000) ? 17L : 100L;
            a(this.e);
            this.g = System.currentTimeMillis() - this.f;
            if (this.g > 0 && this.g < 6000) {
                this.c += this.g;
            }
            if (a.l == null || System.currentTimeMillis() - this.i > 1000) {
            }
            this.i = System.currentTimeMillis();
            String strC = a.m.p.c();
            if (!strC.equals(a.m.B)) {
                a.m.B = strC;
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "network_type", a.m.B);
                new z("Network.on_status_change", 1, jSONObjectA).b();
            }
        }
        u.c.b("AdColony session ending, releasing Activity reference.");
        a.m.E = true;
        a.l = null;
        this.n = true;
        this.r = true;
        b();
        au.b bVar = new au.b(10.0d);
        while (!this.q && !bVar.b() && this.r) {
            a.a();
            a(100L);
        }
        u.d.b("SessionInfo.stopped message received, ending ADC.update_module() spam.");
    }

    void a(boolean z) {
        if (!this.m) {
            if (this.n) {
                a.m.E = false;
                this.n = false;
            }
            this.c = 0L;
            this.d = 0L;
            this.m = true;
            this.j = true;
            this.q = false;
            new Thread(this).start();
            if (z) {
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, au.b());
                new z("SessionInfo.on_start", 1, jSONObjectA).b();
            }
            if (AdColony.a.isShutdown()) {
                AdColony.a = Executors.newSingleThreadExecutor();
            }
        }
    }

    void b() {
        this.m = false;
        this.j = false;
        if (w.j != null) {
            w.j.a();
        }
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "session_length", this.c / 1000.0d);
        new z("SessionInfo.on_stop", 1, jSONObjectA).b();
        a.a();
        AdColony.a.shutdown();
        u.d.b("SESSION STOP");
    }

    void c() {
        if (a.m != null && a.m.d != null) {
            Iterator<ad> it = a.m.d.a.iterator();
            while (it.hasNext()) {
                new z("SessionInfo.on_pause", it.next().a()).b();
            }
        }
        this.k = true;
        a.a();
    }

    void d() {
        if (a.m != null && a.m.d != null) {
            Iterator<ad> it = a.m.d.a.iterator();
            while (it.hasNext()) {
                new z("SessionInfo.on_resume", it.next().a()).b();
            }
        }
        this.k = false;
    }

    void a(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
        }
    }
}
