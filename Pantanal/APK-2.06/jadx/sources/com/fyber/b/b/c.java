package com.fyber.b.b;

import android.content.Context;
import android.content.Intent;
import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.ads.interstitials.InterstitialActivity;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.requesters.AdRequestCallback;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.Requester;
import java.util.List;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: InterstitialFetchOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class c extends com.fyber.b.b<com.fyber.ads.interstitials.b.a, InterstitialAd> {
    @Override // com.fyber.b.b
    protected final /* synthetic */ void a(Ad ad) {
        InterstitialAd interstitialAd = (InterstitialAd) ad;
        Context context = this.c.get();
        if (context != null && this.a != null) {
            if (this.a instanceof RequestCallback) {
                Intent intent = new Intent(context, (Class<?>) InterstitialActivity.class);
                intent.putExtra(Requester.EXTRA_AD_FORMAT, AdFormat.INTERSTITIAL);
                ((RequestCallback) this.a).onAdAvailable(intent);
                return;
            }
            ((AdRequestCallback) this.a).onAdAvailable(interstitialAd);
        }
    }

    public c(a aVar) {
        super(aVar);
    }

    @Override // com.fyber.b.b
    protected final Future<List<com.fyber.ads.interstitials.b.a>> a(String str) {
        return d.a(this.b, str);
    }

    @Override // com.fyber.b.b
    protected final Future<InterstitialAd> a(List<com.fyber.ads.interstitials.b.a> list) {
        return new com.fyber.b.b.a.C0019a(list).a().a(this.c);
    }

    @Override // com.fyber.b.b
    protected final int a() {
        return 10;
    }

    @Override // com.fyber.b.b
    protected final int b() {
        return 10;
    }

    @Override // com.fyber.b.b
    protected final void a(String str, com.fyber.ads.a.a aVar, String str2) {
        new b.a(aVar).a(str2).b(str).b();
    }

    @Override // com.fyber.b.b
    protected final void a(com.fyber.ads.a.b bVar) {
        com.fyber.ads.interstitials.b.b.a(bVar);
    }

    @Override // com.fyber.b.b
    protected final void c() {
        if (this.a instanceof RequestCallback) {
            ((RequestCallback) this.a).onAdNotAvailable(AdFormat.INTERSTITIAL);
        } else {
            ((AdRequestCallback) this.a).onAdNotAvailable(AdFormat.INTERSTITIAL);
        }
    }

    /* JADX INFO: compiled from: InterstitialFetchOperation.java */
    public static class a extends com.fyber.b.b.a<c, a> {
        @Override // com.fyber.b.b.a
        protected final /* bridge */ /* synthetic */ a a() {
            return this;
        }

        @Override // com.fyber.b.b.a
        protected final String b() {
            return "interstitial";
        }
    }
}
