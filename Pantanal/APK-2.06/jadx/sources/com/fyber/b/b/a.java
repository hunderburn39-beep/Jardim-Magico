package com.fyber.b.b;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fyber.ads.Ad;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.utils.FyberLogger;
import java.util.List;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: InterstitialAdsProcessorOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends com.fyber.b.d<InterstitialAd, InterstitialAd, com.fyber.ads.interstitials.b.a> {
    @Override // com.fyber.b.d
    protected final /* bridge */ /* synthetic */ Ad a(InterstitialAd interstitialAd, com.fyber.ads.a.c cVar) {
        InterstitialAd interstitialAd2 = interstitialAd;
        com.fyber.ads.interstitials.b.b.a((com.fyber.ads.interstitials.b.a) cVar);
        return interstitialAd2;
    }

    @Override // com.fyber.b.d
    protected final /* synthetic */ Future<InterstitialAd> a(com.fyber.ads.a.c cVar) {
        com.fyber.ads.interstitials.b.a aVar = (com.fyber.ads.interstitials.b.a) cVar;
        Context context = this.b.get();
        if (context != null) {
            return com.fyber.mediation.b.a.a(context, aVar);
        }
        FyberLogger.d("InterstitialAdsProcessorOperation", "There was no context. Not proceeding with the request...");
        return null;
    }

    protected a(List<com.fyber.ads.interstitials.b.a> list) {
        super(list);
    }

    @Override // com.fyber.b.d
    protected final int a() {
        return 5;
    }

    @Override // com.fyber.b.d
    protected final String b() {
        return "InterstitialAdsProcessorOperation";
    }

    @Override // com.fyber.b.d
    protected final void a(com.fyber.ads.a.c cVar, com.fyber.ads.a.a aVar, String str) {
        new b.a(aVar).a(str).a(cVar).b();
    }

    @Override // com.fyber.b.d
    @NonNull
    protected final int c() {
        return com.fyber.mediation.a.b;
    }

    /* JADX INFO: renamed from: com.fyber.b.b.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: InterstitialAdsProcessorOperation.java */
    public static class C0019a {
        private List<com.fyber.ads.interstitials.b.a> a;

        public C0019a(List<com.fyber.ads.interstitials.b.a> list) {
            this.a = list;
        }

        public final a a() {
            return new a(this.a);
        }
    }
}
