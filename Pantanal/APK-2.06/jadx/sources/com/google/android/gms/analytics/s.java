package com.google.android.gms.analytics;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.internal.ef;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: classes.dex */
class s implements ag, com.google.android.gms.analytics.c.b, com.google.android.gms.analytics.c.InterfaceC0030c {
    private final Context mContext;
    private com.google.android.gms.analytics.d sG;
    private final f sH;
    private boolean sJ;
    private volatile long sT;
    private volatile a sU;
    private volatile com.google.android.gms.analytics.b sV;
    private com.google.android.gms.analytics.d sW;
    private final GoogleAnalytics sX;
    private final Queue<d> sY;
    private volatile int sZ;
    private volatile Timer ta;
    private volatile Timer tb;
    private volatile Timer tc;
    private boolean td;
    private boolean te;
    private boolean tf;
    private i tg;
    private long th;

    private enum a {
        CONNECTING,
        CONNECTED_SERVICE,
        CONNECTED_LOCAL,
        BLOCKED,
        PENDING_CONNECTION,
        PENDING_DISCONNECT,
        DISCONNECTED
    }

    private class b extends TimerTask {
        private b() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (s.this.sU != a.CONNECTED_SERVICE || !s.this.sY.isEmpty() || s.this.sT + s.this.th >= s.this.tg.currentTimeMillis()) {
                s.this.tc.schedule(s.this.new b(), s.this.th);
            } else {
                aa.y("Disconnecting due to inactivity");
                s.this.be();
            }
        }
    }

    private class c extends TimerTask {
        private c() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (s.this.sU == a.CONNECTING) {
                s.this.cs();
            }
        }
    }

    private static class d {
        private final Map<String, String> ts;
        private final long tt;
        private final String tu;
        private final List<ef> tv;

        public d(Map<String, String> map, long j, String str, List<ef> list) {
            this.ts = map;
            this.tt = j;
            this.tu = str;
            this.tv = list;
        }

        public Map<String, String> cv() {
            return this.ts;
        }

        public long cw() {
            return this.tt;
        }

        public List<ef> cx() {
            return this.tv;
        }

        public String getPath() {
            return this.tu;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("PATH: ");
            sb.append(this.tu);
            if (this.ts != null) {
                sb.append("  PARAMS: ");
                for (Map.Entry<String, String> entry : this.ts.entrySet()) {
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue());
                    sb.append(",  ");
                }
            }
            return sb.toString();
        }
    }

    private class e extends TimerTask {
        private e() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            s.this.ct();
        }
    }

    s(Context context, f fVar) {
        this(context, fVar, null, GoogleAnalytics.getInstance(context));
    }

    s(Context context, f fVar, com.google.android.gms.analytics.d dVar, GoogleAnalytics googleAnalytics) {
        this.sY = new ConcurrentLinkedQueue();
        this.th = 300000L;
        this.sW = dVar;
        this.mContext = context;
        this.sH = fVar;
        this.sX = googleAnalytics;
        this.tg = new i() { // from class: com.google.android.gms.analytics.s.1
            @Override // com.google.android.gms.analytics.i
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        };
        this.sZ = 0;
        this.sU = a.DISCONNECTED;
    }

    private Timer a(Timer timer) {
        if (timer == null) {
            return null;
        }
        timer.cancel();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void be() {
        if (this.sV != null && this.sU == a.CONNECTED_SERVICE) {
            this.sU = a.PENDING_DISCONNECT;
            this.sV.disconnect();
        }
    }

    private void co() {
        this.ta = a(this.ta);
        this.tb = a(this.tb);
        this.tc = a(this.tc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public synchronized void cq() {
        if (Thread.currentThread().equals(this.sH.getThread())) {
            if (this.td) {
                bR();
            }
            switch (this.sU) {
                case CONNECTED_LOCAL:
                    while (!this.sY.isEmpty()) {
                        d dVarPoll = this.sY.poll();
                        aa.y("Sending hit to store  " + dVarPoll);
                        this.sG.a(dVarPoll.cv(), dVarPoll.cw(), dVarPoll.getPath(), dVarPoll.cx());
                    }
                    if (this.sJ) {
                        cr();
                    }
                    break;
                case CONNECTED_SERVICE:
                    while (!this.sY.isEmpty()) {
                        d dVarPeek = this.sY.peek();
                        aa.y("Sending hit to service   " + dVarPeek);
                        if (this.sX.isDryRunEnabled()) {
                            aa.y("Dry run enabled. Hit not actually sent to service.");
                        } else {
                            this.sV.a(dVarPeek.cv(), dVarPeek.cw(), dVarPeek.getPath(), dVarPeek.cx());
                        }
                        this.sY.poll();
                    }
                    this.sT = this.tg.currentTimeMillis();
                    break;
                case DISCONNECTED:
                    aa.y("Need to reconnect");
                    if (!this.sY.isEmpty()) {
                        ct();
                    }
                    break;
            }
        } else {
            this.sH.bZ().add(new Runnable() { // from class: com.google.android.gms.analytics.s.2
                @Override // java.lang.Runnable
                public void run() {
                    s.this.cq();
                }
            });
        }
    }

    private void cr() {
        this.sG.bW();
        this.sJ = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void cs() {
        if (this.sU != a.CONNECTED_LOCAL) {
            co();
            aa.y("falling back to local store");
            if (this.sW != null) {
                this.sG = this.sW;
            } else {
                r rVarCi = r.ci();
                rVarCi.a(this.mContext, this.sH);
                this.sG = rVarCi.cl();
            }
            this.sU = a.CONNECTED_LOCAL;
            cq();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void ct() {
        if (this.tf || this.sV == null || this.sU == a.CONNECTED_LOCAL) {
            aa.z("client not initialized.");
            cs();
        } else {
            try {
                this.sZ++;
                a(this.tb);
                this.sU = a.CONNECTING;
                this.tb = new Timer("Failed Connect");
                this.tb.schedule(new c(), 3000L);
                aa.y("connecting to Analytics service");
                this.sV.connect();
            } catch (SecurityException e2) {
                aa.z("security exception on connectToService");
                cs();
            }
        }
    }

    private void cu() {
        this.ta = a(this.ta);
        this.ta = new Timer("Service Reconnect");
        this.ta.schedule(new e(), 5000L);
    }

    @Override // com.google.android.gms.analytics.c.InterfaceC0030c
    public synchronized void a(int i, Intent intent) {
        this.sU = a.PENDING_CONNECTION;
        if (this.sZ < 2) {
            aa.z("Service unavailable (code=" + i + "), will retry.");
            cu();
        } else {
            aa.z("Service unavailable (code=" + i + "), using local store.");
            cs();
        }
    }

    @Override // com.google.android.gms.analytics.ag
    public void b(Map<String, String> map, long j, String str, List<ef> list) {
        aa.y("putHit called");
        this.sY.add(new d(map, j, str, list));
        cq();
    }

    @Override // com.google.android.gms.analytics.ag
    public void bR() {
        aa.y("clearHits called");
        this.sY.clear();
        switch (this.sU) {
            case CONNECTED_LOCAL:
                this.sG.j(0L);
                this.td = false;
                break;
            case CONNECTED_SERVICE:
                this.sV.bR();
                this.td = false;
                break;
            default:
                this.td = true;
                break;
        }
    }

    @Override // com.google.android.gms.analytics.ag
    public void bW() {
        switch (this.sU) {
            case CONNECTED_LOCAL:
                cr();
                break;
            case CONNECTED_SERVICE:
                break;
            default:
                this.sJ = true;
                break;
        }
    }

    @Override // com.google.android.gms.analytics.ag
    public synchronized void bY() {
        if (!this.tf) {
            aa.y("setForceLocalDispatch called.");
            this.tf = true;
            switch (this.sU) {
                case CONNECTED_SERVICE:
                    be();
                    break;
                case CONNECTING:
                    this.te = true;
                    break;
            }
        }
    }

    @Override // com.google.android.gms.analytics.ag
    public void cp() {
        if (this.sV != null) {
            return;
        }
        this.sV = new com.google.android.gms.analytics.c(this.mContext, this, this);
        ct();
    }

    @Override // com.google.android.gms.analytics.c.b
    public synchronized void onConnected() {
        this.tb = a(this.tb);
        this.sZ = 0;
        aa.y("Connected to service");
        this.sU = a.CONNECTED_SERVICE;
        if (this.te) {
            be();
            this.te = false;
        } else {
            cq();
            this.tc = a(this.tc);
            this.tc = new Timer("disconnect check");
            this.tc.schedule(new b(), this.th);
        }
    }

    @Override // com.google.android.gms.analytics.c.b
    public synchronized void onDisconnected() {
        if (this.sU == a.PENDING_DISCONNECT) {
            aa.y("Disconnected from service");
            co();
            this.sU = a.DISCONNECTED;
        } else {
            aa.y("Unexpected disconnect.");
            this.sU = a.PENDING_CONNECTION;
            if (this.sZ < 2) {
                cu();
            } else {
                cs();
            }
        }
    }
}
