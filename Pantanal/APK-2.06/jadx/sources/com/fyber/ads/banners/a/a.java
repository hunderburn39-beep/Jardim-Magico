package com.fyber.ads.banners.a;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: BannerClient.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static a a = new a();
    private AtomicReference<com.fyber.ads.a.b> b = new AtomicReference<>(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);

    public static boolean a(com.fyber.ads.a.b bVar) {
        a.b.getAndSet(bVar);
        return true;
    }

    public static com.fyber.ads.a.b a() {
        return a.b.get();
    }
}
