package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public final class bm implements bn.a {
    private final ah kX;
    private final bq ky;
    private final Context mContext;
    private final String nn;
    private final long no;
    private final bi np;
    private final ak nq;
    private final dx nr;
    private br ns;
    private final Object li = new Object();
    private int nt = -2;

    public bm(Context context, String str, bq bqVar, bj bjVar, bi biVar, ah ahVar, ak akVar, dx dxVar) {
        this.mContext = context;
        this.nn = str;
        this.ky = bqVar;
        this.no = bjVar.nd != -1 ? bjVar.nd : TapjoyConstants.TIMER_INCREMENT;
        this.np = biVar;
        this.kX = ahVar;
        this.nq = akVar;
        this.nr = dxVar;
    }

    private void a(long j, long j2, long j3, long j4) {
        while (this.nt == -2) {
            b(j, j2, j3, j4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(bl blVar) {
        try {
            if (this.nr.rs < 4100000) {
                if (this.nq.lT) {
                    this.ns.a(com.google.android.gms.dynamic.e.h(this.mContext), this.kX, this.np.nb, blVar);
                } else {
                    this.ns.a(com.google.android.gms.dynamic.e.h(this.mContext), this.nq, this.kX, this.np.nb, blVar);
                }
            } else if (this.nq.lT) {
                this.ns.a(com.google.android.gms.dynamic.e.h(this.mContext), this.kX, this.np.nb, this.np.mW, blVar);
            } else {
                this.ns.a(com.google.android.gms.dynamic.e.h(this.mContext), this.nq, this.kX, this.np.nb, this.np.mW, blVar);
            }
        } catch (RemoteException e) {
            dw.c("Could not request ad from mediation adapter.", e);
            f(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public br aJ() {
        dw.x("Instantiating mediation adapter: " + this.nn);
        try {
            return this.ky.m(this.nn);
        } catch (RemoteException e) {
            dw.a("Could not instantiate mediation adapter: " + this.nn, e);
            return null;
        }
    }

    private void b(long j, long j2, long j3, long j4) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j5 = j2 - (jElapsedRealtime - j);
        long j6 = j4 - (jElapsedRealtime - j3);
        if (j5 <= 0 || j6 <= 0) {
            dw.x("Timed out waiting for adapter.");
            this.nt = 3;
        } else {
            try {
                this.li.wait(Math.min(j5, j6));
            } catch (InterruptedException e) {
                this.nt = -1;
            }
        }
    }

    public bn b(long j, long j2) {
        bn bnVar;
        synchronized (this.li) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            final bl blVar = new bl();
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bm.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (bm.this.li) {
                        if (bm.this.nt != -2) {
                            return;
                        }
                        bm.this.ns = bm.this.aJ();
                        if (bm.this.ns == null) {
                            bm.this.f(4);
                        } else {
                            blVar.a(bm.this);
                            bm.this.a(blVar);
                        }
                    }
                }
            });
            a(jElapsedRealtime, this.no, j, j2);
            bnVar = new bn(this.np, this.ns, this.nn, blVar, this.nt);
        }
        return bnVar;
    }

    public void cancel() {
        synchronized (this.li) {
            try {
                if (this.ns != null) {
                    this.ns.destroy();
                }
            } catch (RemoteException e) {
                dw.c("Could not destroy mediation adapter.", e);
            }
            this.nt = -1;
            this.li.notify();
        }
    }

    @Override // com.google.android.gms.internal.bn.a
    public void f(int i) {
        synchronized (this.li) {
            this.nt = i;
            this.li.notify();
        }
    }
}
