package com.google.android.gms.internal;

import android.os.Handler;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class x {
    private final a kV;
    private final Runnable kW;
    private ah kX;
    private boolean kY;
    private boolean kZ;
    private long la;

    public static class a {
        private final Handler mHandler;

        public a(Handler handler) {
            this.mHandler = handler;
        }

        public boolean postDelayed(Runnable runnable, long timeFromNowInMillis) {
            return this.mHandler.postDelayed(runnable, timeFromNowInMillis);
        }

        public void removeCallbacks(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

    public x(v vVar) {
        this(vVar, new a(dv.rp));
    }

    x(final v vVar, a aVar) {
        this.kY = false;
        this.kZ = false;
        this.la = 0L;
        this.kV = aVar;
        this.kW = new Runnable() { // from class: com.google.android.gms.internal.x.1
            private final WeakReference<v> lb;

            {
                this.lb = new WeakReference<>(vVar);
            }

            @Override // java.lang.Runnable
            public void run() {
                x.this.kY = false;
                v vVar2 = this.lb.get();
                if (vVar2 != null) {
                    vVar2.b(x.this.kX);
                }
            }
        };
    }

    public void a(ah ahVar, long j) {
        if (this.kY) {
            dw.z("An ad refresh is already scheduled.");
            return;
        }
        this.kX = ahVar;
        this.kY = true;
        this.la = j;
        if (this.kZ) {
            return;
        }
        dw.x("Scheduling ad refresh " + j + " milliseconds from now.");
        this.kV.postDelayed(this.kW, j);
    }

    public void cancel() {
        this.kY = false;
        this.kV.removeCallbacks(this.kW);
    }

    public void d(ah ahVar) {
        a(ahVar, 60000L);
    }

    public void pause() {
        this.kZ = true;
        if (this.kY) {
            this.kV.removeCallbacks(this.kW);
        }
    }

    public void resume() {
        this.kZ = false;
        if (this.kY) {
            this.kY = false;
            a(this.kX, this.la);
        }
    }
}
