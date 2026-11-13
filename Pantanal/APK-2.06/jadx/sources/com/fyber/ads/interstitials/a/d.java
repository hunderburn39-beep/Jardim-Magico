package com.fyber.ads.interstitials.a;

import com.fyber.cache.CacheManager;
import com.fyber.cache.internal.e;
import com.tapjoy.TJAdUnitConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: InterstitialVideoListener.java */
/* JADX INFO: loaded from: classes.dex */
public final class d implements com.fyber.c.d.b, com.fyber.c.d.d.InterfaceC0022d {
    private final com.fyber.ads.interstitials.b.a a;
    private int b;
    private boolean h;
    private Map<String, String> c = new HashMap(3);
    private AtomicInteger d = new AtomicInteger(0);
    private final byte e = 25;
    private final byte f = 50;
    private final byte g = 75;
    private boolean i = false;

    public d(com.fyber.ads.interstitials.b.a aVar, boolean z) {
        this.a = aVar;
        this.h = z;
    }

    @Override // com.fyber.c.d.b
    public final void a() {
        new com.fyber.b.b.b.a(com.fyber.ads.a.a.ValidationTimeout).a("video").a(this.a).b();
    }

    @Override // com.fyber.c.d.b
    public final void a(String str, String str2, String str3) {
        boolean zStartsWith = str2.startsWith("file");
        e eVarD = CacheManager.a().d();
        int iA = eVarD.a();
        this.c.put("is_cached", Boolean.toString(zStartsWith));
        this.c.put("cache_config_id", str3);
        this.c.put("downloaded_videos_count", Integer.toString(iA));
        eVarD.c();
    }

    @Override // com.fyber.c.d.b
    public final void a(int i) {
        this.b = i;
        new com.fyber.b.b.b.a(com.fyber.ads.a.a.Progress).a(TJAdUnitConstants.String.VIDEO_START).a(this.c).a(this.a).b();
    }

    @Override // com.fyber.c.d.b
    public final void b(int i) {
        int i2 = (int) ((i / this.b) * 100.0f);
        if (i2 >= 25 && this.d.compareAndSet(0, 25)) {
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.Progress).a("q25").a(this.c).a(this.a).b();
        }
        if (i2 >= 50 && this.d.compareAndSet(25, 50)) {
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.Progress).a("q50").a(this.c).a(this.a).b();
        }
        if (i2 >= 75 && this.d.compareAndSet(50, 75)) {
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.Progress).a("q75").a(this.c).a(this.a).b();
        }
    }

    @Override // com.fyber.c.d.b
    public final void b() {
        if (this.d.get() != 75) {
            b(this.b);
        }
        new com.fyber.b.b.b.a(com.fyber.ads.a.a.Progress).a("finish").a(this.c).a(this.a).b();
    }

    @Override // com.fyber.c.d.b
    public final void c() {
        this.i = true;
        new com.fyber.b.b.b.a(com.fyber.ads.a.a.Interaction).a("click_through").a(this.a).b();
        if (!this.h) {
            this.a.h();
        }
    }

    @Override // com.fyber.c.d.b
    public final void d() {
        if (this.h) {
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.Interaction).a("close_video").a(this.a).b();
        }
    }

    @Override // com.fyber.c.d.b
    public final void a(String str) {
        new com.fyber.b.b.b.a(com.fyber.ads.a.a.ValidationError).a(str).a(this.a).b();
    }

    @Override // com.fyber.c.d.d.InterfaceC0022d
    public final void a(int i, String str) {
        if (this.h) {
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.Progress).a("end_card").a(this.c).a(this.a).b();
        }
    }

    public final boolean e() {
        return this.i;
    }
}
