package com.fyber.b.a;

import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.ads.banners.BannerAd;
import com.fyber.ads.banners.BannerSize;
import com.fyber.requesters.AdRequestCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: BannerFetchOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class c extends com.fyber.b.b<com.fyber.ads.banners.a.b, BannerAd> {
    private List<BannerSize> d;

    @Override // com.fyber.b.b
    protected final /* synthetic */ void a(Ad ad) {
        ((AdRequestCallback) this.a).onAdAvailable((BannerAd) ad);
    }

    public c(a aVar) {
        super(aVar);
        this.d = aVar.d;
    }

    @Override // com.fyber.b.b
    protected final Future<List<com.fyber.ads.banners.a.b>> a(String str) {
        return d.a(this.b, str);
    }

    @Override // com.fyber.b.b
    protected final Future<BannerAd> a(List<com.fyber.ads.banners.a.b> list) {
        return new com.fyber.b.a.a.C0018a(list, this.d).a().a(this.c);
    }

    @Override // com.fyber.b.b
    protected final int a() {
        return 15;
    }

    @Override // com.fyber.b.b
    protected final int b() {
        return 20;
    }

    @Override // com.fyber.b.b
    protected final void a(String str, com.fyber.ads.a.a aVar, String str2) {
        new b.a(aVar).a(str2).b(str).b();
    }

    @Override // com.fyber.b.b
    protected final void a(com.fyber.ads.a.b bVar) {
        com.fyber.ads.banners.a.a.a(bVar);
    }

    @Override // com.fyber.b.b
    protected final void c() {
        ((AdRequestCallback) this.a).onAdNotAvailable(AdFormat.BANNER);
    }

    /* JADX INFO: compiled from: BannerFetchOperation.java */
    public static class a extends com.fyber.b.b.a<c, a> {
        private List<BannerSize> d;

        @Override // com.fyber.b.b.a
        protected final /* bridge */ /* synthetic */ a a() {
            return this;
        }

        @Override // com.fyber.b.b.a
        protected final String b() {
            return "banner";
        }

        public final a a(ArrayList<BannerSize> arrayList) {
            this.d = arrayList;
            return this;
        }
    }
}
