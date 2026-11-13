package com.fyber.ads.interstitials;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.ads.interstitials.b.b;
import com.fyber.ads.interstitials.b.c;

/* JADX INFO: loaded from: classes.dex */
public class InterstitialAd extends Ad {
    private c a;

    public InterstitialAd(@NonNull c cVar) {
        this.a = cVar;
    }

    public InterstitialAd withListener(InterstitialAdListener interstitialAdListener) {
        this.a.b(interstitialAdListener);
        return this;
    }

    @Override // com.fyber.ads.Ad
    public AdFormat getAdFormat() {
        return AdFormat.INTERSTITIAL;
    }

    @Override // com.fyber.ads.Ad
    public boolean canStart() {
        return b.a(this);
    }

    @Override // com.fyber.ads.Ad
    public void start(Activity activity) {
        b.a(this, activity);
    }
}
