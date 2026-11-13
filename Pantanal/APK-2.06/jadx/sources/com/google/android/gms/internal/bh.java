package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class bh {
    private final bq ky;
    private final Context mContext;
    private final cx mQ;
    private final bj mR;
    private bm mT;
    private final Object li = new Object();
    private boolean mS = false;

    public bh(Context context, cx cxVar, bq bqVar, bj bjVar) {
        this.mContext = context;
        this.mQ = cxVar;
        this.ky = bqVar;
        this.mR = bjVar;
    }

    public bn a(long j, long j2) {
        dw.v("Starting mediation.");
        for (bi biVar : this.mR.nc) {
            dw.x("Trying mediation network: " + biVar.mX);
            for (String str : biVar.mY) {
                synchronized (this.li) {
                    if (this.mS) {
                        return new bn(-1);
                    }
                    this.mT = new bm(this.mContext, str, this.ky, this.mR, biVar, this.mQ.pg, this.mQ.kN, this.mQ.kK);
                    final bn bnVarB = this.mT.b(j, j2);
                    if (bnVarB.nw == 0) {
                        dw.v("Adapter succeeded.");
                        return bnVarB;
                    }
                    if (bnVarB.ny != null) {
                        dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bh.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    bnVarB.ny.destroy();
                                } catch (RemoteException e) {
                                    dw.c("Could not destroy mediation adapter.", e);
                                }
                            }
                        });
                    }
                }
            }
        }
        return new bn(1);
    }

    public void cancel() {
        synchronized (this.li) {
            this.mS = true;
            if (this.mT != null) {
                this.mT.cancel();
            }
        }
    }
}
