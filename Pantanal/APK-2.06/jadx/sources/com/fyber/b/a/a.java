package com.fyber.b.a;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fyber.ads.Ad;
import com.fyber.ads.banners.BannerAd;
import com.fyber.ads.banners.BannerSize;
import com.fyber.ads.banners.mediation.BannerWrapper;
import com.fyber.utils.FyberLogger;
import java.util.List;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: BannerAdsProcessorOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends com.fyber.b.d<BannerWrapper, BannerAd, com.fyber.ads.banners.a.b> {
    private final List<BannerSize> c;

    @Override // com.fyber.b.d
    protected final /* synthetic */ Ad a(BannerWrapper bannerWrapper, com.fyber.ads.a.c cVar) {
        return new BannerAd.a((com.fyber.ads.banners.a.b) cVar, bannerWrapper).a();
    }

    @Override // com.fyber.b.d
    protected final /* synthetic */ Future<BannerWrapper> a(com.fyber.ads.a.c cVar) {
        com.fyber.ads.banners.a.b bVar = (com.fyber.ads.banners.a.b) cVar;
        Context context = this.b.get();
        if (context != null) {
            return com.fyber.mediation.b.a.a(context, bVar, this.c);
        }
        FyberLogger.d("BannerAdsProcessorOperation", "There was no context. Not proceeding with the request...");
        return null;
    }

    protected a(List<com.fyber.ads.banners.a.b> list, List<BannerSize> list2) {
        super(list);
        this.c = list2;
    }

    @Override // com.fyber.b.d
    protected final int a() {
        return 10;
    }

    @Override // com.fyber.b.d
    protected final String b() {
        return "BannerAdsProcessorOperation";
    }

    @Override // com.fyber.b.d
    protected final void a(com.fyber.ads.a.c cVar, com.fyber.ads.a.a aVar, String str) {
        new b.a(aVar).a(str).a(cVar).b();
    }

    @Override // com.fyber.b.d
    @NonNull
    protected final int c() {
        return com.fyber.mediation.a.c;
    }

    /* JADX INFO: renamed from: com.fyber.b.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: BannerAdsProcessorOperation.java */
    public static class C0018a {
        private List<com.fyber.ads.banners.a.b> a;
        private List<BannerSize> b;

        public C0018a(List<com.fyber.ads.banners.a.b> list, List<BannerSize> list2) {
            this.a = list;
            this.b = list2;
        }

        public final a a() {
            return new a(this.a, this.b);
        }
    }
}
