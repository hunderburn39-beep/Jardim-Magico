package com.adcolony.sdk;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class cf {
    int d;
    long e;
    long f;
    long g;
    long h;
    int i;
    long j;
    long k;
    int a = 0;
    int b = 1;
    int c = 2;
    Handler l = new Handler(Looper.getMainLooper());
    Runnable m = new Runnable() { // from class: com.adcolony.sdk.cf.1
        @Override // java.lang.Runnable
        public void run() {
            bw.b(cf.this.i(), "timerRoutine started", true);
            if (bv.aN().x()) {
                bv.aN().J();
                if (bv.aN().t() == AdColonyPubServices.ServiceAvailability.SERVICE_INVISIBLE) {
                    bv.aN().f("InvisibleReinitAttempt");
                }
            } else if (bv.aN().I()) {
                bv.aN().f("InternetAvailableFromTimer");
            }
            cf.this.l.postDelayed(cf.this.m, bv.aN().aw().o());
        }
    };

    cf() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String i() {
        return "AdColonyPubServices";
    }

    void a() {
        this.e = 0L;
        this.f = 0L;
        this.g = 0L;
        this.h = 0L;
        this.i = 0;
        this.j = 0L;
        this.d = this.a;
        this.l.removeCallbacks(this.m);
    }

    void b() {
        this.l.post(new Runnable() { // from class: com.adcolony.sdk.cf.2
            @Override // java.lang.Runnable
            public void run() {
                if (cf.this.d != cf.this.b) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    cf.this.a();
                    cf.this.j = jCurrentTimeMillis;
                    cf.this.d = cf.this.b;
                    cf.this.a(bv.aN().aw().o());
                }
            }
        });
    }

    void c() {
        if (this.d == this.b) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            bw.b(i(), "pause session time:" + jCurrentTimeMillis, true);
            this.d = this.c;
            this.g = jCurrentTimeMillis;
            this.i++;
            this.e = jCurrentTimeMillis - this.j;
            this.j = jCurrentTimeMillis;
            this.l.removeCallbacks(this.m);
            bv.aN().c();
        }
    }

    void a(int i) {
        this.f |= (long) bn.a(i);
    }

    long d() {
        if (this.d == this.a) {
            return 0L;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        bw.b(i(), "stopSession time: " + jCurrentTimeMillis, true);
        if (this.g > 0) {
            this.e += this.g - this.j;
        } else {
            this.e += jCurrentTimeMillis - this.j;
        }
        long jN = bv.aN().aw().n();
        HashMap map = new HashMap();
        map.put("paused_time_ms", Long.valueOf(this.h));
        map.put("paused_count", Integer.valueOf(this.i));
        map.put("grace_period_ms", Long.valueOf(jN));
        map.put("active_time_ms", Long.valueOf(this.e));
        map.put("error_bit_code", Long.valueOf(this.f));
        map.put("reset_session_timer_ms", Integer.valueOf(bv.aN().aw().o()));
        bv.aN().aA().a("stop_session", map);
        long j = this.e;
        a();
        return j;
    }

    void e() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - this.g;
        long jN = bv.aN().aw().n();
        boolean z = this.g > 0 && j > jN;
        if (!z && this.d == this.b) {
            bw.b(i(), "resumeSession returned early", true);
            return;
        }
        if (jCurrentTimeMillis < this.j) {
            bw.b(i(), "timeMs: " + jCurrentTimeMillis + "\n_sessionResumedTime: " + this.j, true);
            this.d = this.a;
        }
        long jO = bv.aN().aw().o();
        if (this.d == this.a) {
            bw.b(i(), "ResumeSession: no paused session - creating a new one.", true);
            b();
            return;
        }
        if (z) {
            bw.b(i(), "ResumeSession: resume grace period expired " + j + " > " + jN, true);
            this.i--;
            d();
            b();
            bv.aN().u();
            bv.aN().f("GracePeriodExpired");
            return;
        }
        this.g = 0L;
        this.h += j;
        this.d = this.b;
        if (jO - j < 0) {
        }
        bw.b(i(), "ResumeSession: normal resume after " + j + " ms", true);
    }

    void f() {
        a(bv.aN().aw().o());
    }

    void a(long j) {
        long jO = bv.aN().aw().o();
        this.l.removeCallbacks(this.m);
        this.l.postDelayed(this.m, j);
        bw.b(i(), "Session timer starting in " + j + " repeats every " + jO, true);
    }

    long g() {
        return this.e;
    }

    void b(long j) {
        this.k = j;
    }

    long h() {
        return this.k;
    }
}
