package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class r extends af {
    private static final Object sF = new Object();
    private static r sR;
    private Context mContext;
    private Handler mHandler;
    private d sG;
    private volatile f sH;
    private boolean sK;
    private String sL;
    private q sP;
    private int sI = 1800;
    private boolean sJ = true;
    private boolean sM = true;
    private boolean sN = true;
    private e sO = new e() { // from class: com.google.android.gms.analytics.r.1
        @Override // com.google.android.gms.analytics.e
        public void r(boolean z) {
            r.this.a(z, r.this.sM);
        }
    };
    private boolean sQ = false;

    private r() {
    }

    public static r ci() {
        if (sR == null) {
            sR = new r();
        }
        return sR;
    }

    private void cj() {
        this.sP = new q(this);
        this.sP.o(this.mContext);
    }

    private void ck() {
        this.mHandler = new Handler(this.mContext.getMainLooper(), new Handler.Callback() { // from class: com.google.android.gms.analytics.r.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message msg) {
                if (1 == msg.what && r.sF.equals(msg.obj)) {
                    u.cy().t(true);
                    r.this.dispatchLocalHits();
                    u.cy().t(false);
                    if (r.this.sI > 0 && !r.this.sQ) {
                        r.this.mHandler.sendMessageDelayed(r.this.mHandler.obtainMessage(1, r.sF), r.this.sI * 1000);
                    }
                }
                return true;
            }
        });
        if (this.sI > 0) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), this.sI * 1000);
        }
    }

    synchronized void a(Context context, f fVar) {
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
            if (this.sH == null) {
                this.sH = fVar;
                if (this.sJ) {
                    dispatchLocalHits();
                    this.sJ = false;
                }
                if (this.sK) {
                    bY();
                    this.sK = false;
                }
            }
        }
    }

    synchronized void a(boolean z, boolean z2) {
        if (this.sQ != z || this.sM != z2) {
            if ((z || !z2) && this.sI > 0) {
                this.mHandler.removeMessages(1, sF);
            }
            if (!z && z2 && this.sI > 0) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), this.sI * 1000);
            }
            aa.y("PowerSaveMode " + ((z || !z2) ? "initiated." : "terminated."));
            this.sQ = z;
            this.sM = z2;
        }
    }

    void bY() {
        if (this.sH == null) {
            aa.y("setForceLocalDispatch() queued. It will be called once initialization is complete.");
            this.sK = true;
        } else {
            u.cy().a(u.a.SET_FORCE_LOCAL_DISPATCH);
            this.sH.bY();
        }
    }

    synchronized d cl() {
        if (this.sG == null) {
            if (this.mContext == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.sG = new ac(this.sO, this.mContext);
            if (this.sL != null) {
                this.sG.bX().F(this.sL);
                this.sL = null;
            }
        }
        if (this.mHandler == null) {
            ck();
        }
        if (this.sP == null && this.sN) {
            cj();
        }
        return this.sG;
    }

    @Override // com.google.android.gms.analytics.af
    synchronized void cm() {
        if (!this.sQ && this.sM && this.sI > 0) {
            this.mHandler.removeMessages(1, sF);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, sF));
        }
    }

    @Override // com.google.android.gms.analytics.af
    synchronized void dispatchLocalHits() {
        if (this.sH == null) {
            aa.y("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.sJ = true;
        } else {
            u.cy().a(u.a.DISPATCH);
            this.sH.bW();
        }
    }

    @Override // com.google.android.gms.analytics.af
    synchronized void s(boolean z) {
        a(this.sQ, z);
    }

    @Override // com.google.android.gms.analytics.af
    synchronized void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        if (this.mHandler == null) {
            aa.y("Dispatch period set with null handler. Dispatch will run once initialization is complete.");
            this.sI = dispatchPeriodInSeconds;
        } else {
            u.cy().a(u.a.SET_DISPATCH_PERIOD);
            if (!this.sQ && this.sM && this.sI > 0) {
                this.mHandler.removeMessages(1, sF);
            }
            this.sI = dispatchPeriodInSeconds;
            if (dispatchPeriodInSeconds > 0 && !this.sQ && this.sM) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), dispatchPeriodInSeconds * 1000);
            }
        }
    }
}
