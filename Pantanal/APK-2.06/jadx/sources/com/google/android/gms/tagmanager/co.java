package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
class co implements o.e {
    private final String WJ;
    private String Xg;
    private bg<com.google.android.gms.internal.c.j> Zf;
    private r Zg;
    private final ScheduledExecutorService Zi;
    private final a Zj;
    private ScheduledFuture<?> Zk;
    private boolean mClosed;
    private final Context mContext;

    interface a {
        cn a(r rVar);
    }

    interface b {
        ScheduledExecutorService la();
    }

    public co(Context context, String str, r rVar) {
        this(context, str, rVar, null, null);
    }

    co(Context context, String str, r rVar, b bVar, a aVar) {
        this.Zg = rVar;
        this.mContext = context;
        this.WJ = str;
        this.Zi = (bVar == null ? new b() { // from class: com.google.android.gms.tagmanager.co.1
            @Override // com.google.android.gms.tagmanager.co.b
            public ScheduledExecutorService la() {
                return Executors.newSingleThreadScheduledExecutor();
            }
        } : bVar).la();
        if (aVar == null) {
            this.Zj = new a() { // from class: com.google.android.gms.tagmanager.co.2
                @Override // com.google.android.gms.tagmanager.co.a
                public cn a(r rVar2) {
                    return new cn(co.this.mContext, co.this.WJ, rVar2);
                }
            };
        } else {
            this.Zj = aVar;
        }
    }

    private cn bK(String str) {
        cn cnVarA = this.Zj.a(this.Zg);
        cnVarA.a(this.Zf);
        cnVarA.bu(this.Xg);
        cnVarA.bJ(str);
        return cnVarA;
    }

    private synchronized void kZ() {
        if (this.mClosed) {
            throw new IllegalStateException("called method after closed");
        }
    }

    @Override // com.google.android.gms.tagmanager.o.e
    public synchronized void a(bg<com.google.android.gms.internal.c.j> bgVar) {
        kZ();
        this.Zf = bgVar;
    }

    @Override // com.google.android.gms.tagmanager.o.e
    public synchronized void bu(String str) {
        kZ();
        this.Xg = str;
    }

    @Override // com.google.android.gms.tagmanager.o.e
    public synchronized void d(long j, String str) {
        bh.y("loadAfterDelay: containerId=" + this.WJ + " delay=" + j);
        kZ();
        if (this.Zf == null) {
            throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
        }
        if (this.Zk != null) {
            this.Zk.cancel(false);
        }
        this.Zk = this.Zi.schedule(bK(str), j, TimeUnit.MILLISECONDS);
    }

    @Override // com.google.android.gms.common.api.Releasable
    public synchronized void release() {
        kZ();
        if (this.Zk != null) {
            this.Zk.cancel(false);
        }
        this.Zi.shutdown();
        this.mClosed = true;
    }
}
