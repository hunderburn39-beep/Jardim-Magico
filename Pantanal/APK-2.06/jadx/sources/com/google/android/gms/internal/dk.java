package com.google.android.gms.internal;

import android.os.Bundle;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public class dk {
    private final String qL;
    private final Object li = new Object();
    private int qQ = 0;
    private long qR = -1;
    private long qS = -1;
    private int qT = 0;
    private int qU = -1;

    public dk(String str) {
        this.qL = str;
    }

    public void b(ah ahVar, long j) {
        synchronized (this.li) {
            if (this.qS == -1) {
                this.qS = j;
                this.qR = this.qS;
            } else {
                this.qR = j;
            }
            if (ahVar.extras == null || ahVar.extras.getInt("gw", 2) != 1) {
                this.qU++;
            }
        }
    }

    public void bk() {
        synchronized (this.li) {
            this.qT++;
        }
    }

    public void bl() {
        synchronized (this.li) {
            this.qQ++;
        }
    }

    public long bw() {
        return this.qS;
    }

    public Bundle q(String str) {
        Bundle bundle;
        synchronized (this.li) {
            bundle = new Bundle();
            bundle.putString(TapjoyConstants.TJC_SESSION_ID, this.qL);
            bundle.putLong("basets", this.qS);
            bundle.putLong("currts", this.qR);
            bundle.putString("seq_num", str);
            bundle.putInt("preqs", this.qU);
            bundle.putInt("pclick", this.qQ);
            bundle.putInt("pimp", this.qT);
        }
        return bundle;
    }
}
