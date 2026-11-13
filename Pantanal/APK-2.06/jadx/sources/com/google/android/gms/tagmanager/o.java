package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.google.android.gms.internal.it;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
class o extends com.google.android.gms.common.api.a.AbstractC0032a<ContainerHolder> {
    private final Looper AS;
    private final String WJ;
    private long WO;
    private final TagManager WW;
    private final d WZ;
    private final gl Wv;
    private final cf Xa;
    private final int Xb;
    private f Xc;
    private volatile n Xd;
    private volatile boolean Xe;
    private com.google.android.gms.internal.c.j Xf;
    private String Xg;
    private e Xh;
    private a Xi;
    private final Context mContext;

    interface a {
        boolean b(Container container);
    }

    private class b implements bg<it.a> {
        private b() {
        }

        @Override // com.google.android.gms.tagmanager.bg
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void i(it.a aVar) {
            com.google.android.gms.internal.c.j jVar;
            if (aVar.aaZ != null) {
                jVar = aVar.aaZ;
            } else {
                com.google.android.gms.internal.c.f fVar = aVar.fK;
                jVar = new com.google.android.gms.internal.c.j();
                jVar.fK = fVar;
                jVar.fJ = null;
                jVar.fL = fVar.fg;
            }
            o.this.a(jVar, aVar.aaY, true);
        }

        @Override // com.google.android.gms.tagmanager.bg
        public void a(bg.a aVar) {
            if (o.this.Xe) {
                return;
            }
            o.this.t(0L);
        }

        @Override // com.google.android.gms.tagmanager.bg
        public void kl() {
        }
    }

    private class c implements bg<com.google.android.gms.internal.c.j> {
        private c() {
        }

        @Override // com.google.android.gms.tagmanager.bg
        public void a(bg.a aVar) {
            if (o.this.Xd != null) {
                o.this.b(o.this.Xd);
            } else {
                o.this.b(o.this.d(Status.By));
            }
            o.this.t(3600000L);
        }

        @Override // com.google.android.gms.tagmanager.bg
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void i(com.google.android.gms.internal.c.j jVar) {
            synchronized (o.this) {
                if (jVar.fK == null) {
                    if (o.this.Xf.fK == null) {
                        bh.w("Current resource is null; network resource is also null");
                        o.this.t(3600000L);
                        return;
                    }
                    jVar.fK = o.this.Xf.fK;
                }
                o.this.a(jVar, o.this.Wv.currentTimeMillis(), false);
                bh.y("setting refresh time to current time: " + o.this.WO);
                if (!o.this.kk()) {
                    o.this.a(jVar);
                }
            }
        }

        @Override // com.google.android.gms.tagmanager.bg
        public void kl() {
        }
    }

    private class d implements n.a {
        private d() {
        }

        @Override // com.google.android.gms.tagmanager.n.a
        public void br(String str) {
            o.this.br(str);
        }

        @Override // com.google.android.gms.tagmanager.n.a
        public String ke() {
            return o.this.ke();
        }

        @Override // com.google.android.gms.tagmanager.n.a
        public void kg() {
            if (o.this.Xa.cS()) {
                o.this.t(0L);
            }
        }
    }

    interface e extends Releasable {
        void a(bg<com.google.android.gms.internal.c.j> bgVar);

        void bu(String str);

        void d(long j, String str);
    }

    interface f extends Releasable {
        void a(bg<it.a> bgVar);

        void b(it.a aVar);

        cq.c ca(int i);

        void km();
    }

    o(Context context, TagManager tagManager, Looper looper, String str, int i, f fVar, e eVar, gl glVar, cf cfVar) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.WW = tagManager;
        this.AS = looper == null ? Looper.getMainLooper() : looper;
        this.WJ = str;
        this.Xb = i;
        this.Xc = fVar;
        this.Xh = eVar;
        this.WZ = new d();
        this.Xf = new com.google.android.gms.internal.c.j();
        this.Wv = glVar;
        this.Xa = cfVar;
        if (kk()) {
            br(cd.kT().kV());
        }
    }

    public o(Context context, TagManager tagManager, Looper looper, String str, int i, r rVar) {
        this(context, tagManager, looper, str, i, new cp(context, str), new co(context, str, rVar), gn.ft(), new bf(30, TapjoyConstants.PAID_APP_TIME, 5000L, "refreshing", gn.ft()));
    }

    private void C(final boolean z) {
        this.Xc.a(new b());
        this.Xh.a(new c());
        cq.c cVarCa = this.Xc.ca(this.Xb);
        if (cVarCa != null) {
            this.Xd = new n(this.WW, this.AS, new Container(this.mContext, this.WW.getDataLayer(), this.WJ, 0L, cVarCa), this.WZ);
        }
        this.Xi = new a() { // from class: com.google.android.gms.tagmanager.o.2
            @Override // com.google.android.gms.tagmanager.o.a
            public boolean b(Container container) {
                if (z) {
                    return container.getLastRefreshTime() + 43200000 >= o.this.Wv.currentTimeMillis();
                }
                return !container.isDefault();
            }
        };
        if (kk()) {
            this.Xh.d(0L, "");
        } else {
            this.Xc.km();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(com.google.android.gms.internal.c.j jVar) {
        if (this.Xc != null) {
            it.a aVar = new it.a();
            aVar.aaY = this.WO;
            aVar.fK = new com.google.android.gms.internal.c.f();
            aVar.aaZ = jVar;
            this.Xc.b(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(com.google.android.gms.internal.c.j jVar, long j, boolean z) {
        if (z) {
            if (!this.Xe) {
            }
        }
        if (!isReady() || this.Xd == null) {
        }
        this.Xf = jVar;
        this.WO = j;
        t(Math.max(0L, Math.min(43200000L, (this.WO + 43200000) - this.Wv.currentTimeMillis())));
        Container container = new Container(this.mContext, this.WW.getDataLayer(), this.WJ, j, jVar);
        if (this.Xd == null) {
            this.Xd = new n(this.WW, this.AS, container, this.WZ);
        } else {
            this.Xd.a(container);
        }
        if (!isReady() && this.Xi.b(container)) {
            b(this.Xd);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean kk() {
        cd cdVarKT = cd.kT();
        return (cdVarKT.kU() == cd.a.CONTAINER || cdVarKT.kU() == cd.a.CONTAINER_DEBUG) && this.WJ.equals(cdVarKT.getContainerId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void t(long j) {
        if (this.Xh == null) {
            bh.z("Refresh requested, but no network load scheduler.");
        } else {
            this.Xh.d(j, this.Xf.fL);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.a.AbstractC0032a
    /* JADX INFO: renamed from: ac, reason: merged with bridge method [inline-methods] */
    public ContainerHolder d(Status status) {
        if (this.Xd != null) {
            return this.Xd;
        }
        if (status == Status.By) {
            bh.w("timer expired: setting result to failure");
        }
        return new n(status);
    }

    synchronized void br(String str) {
        this.Xg = str;
        if (this.Xh != null) {
            this.Xh.bu(str);
        }
    }

    synchronized String ke() {
        return this.Xg;
    }

    public void kh() {
        cq.c cVarCa = this.Xc.ca(this.Xb);
        if (cVarCa != null) {
            b(new n(this.WW, this.AS, new Container(this.mContext, this.WW.getDataLayer(), this.WJ, 0L, cVarCa), new n.a() { // from class: com.google.android.gms.tagmanager.o.1
                @Override // com.google.android.gms.tagmanager.n.a
                public void br(String str) {
                    o.this.br(str);
                }

                @Override // com.google.android.gms.tagmanager.n.a
                public String ke() {
                    return o.this.ke();
                }

                @Override // com.google.android.gms.tagmanager.n.a
                public void kg() {
                    bh.z("Refresh ignored: container loaded as default only.");
                }
            }));
        } else {
            bh.w("Default was requested, but no default container was found");
            b(d(new Status(10, "Default was requested, but no default container was found", null)));
        }
        this.Xh = null;
        this.Xc = null;
    }

    public void ki() {
        C(false);
    }

    public void kj() {
        C(true);
    }
}
