package com.fyber.ads.interstitials.b;

import android.app.Activity;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.ads.interstitials.InterstitialAdCloseReason;
import com.fyber.ads.interstitials.InterstitialAdListener;

/* JADX INFO: compiled from: InternalInterstitialAd.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends com.fyber.ads.a.c<a> implements c {
    private InterstitialAdListener a;
    private InterstitialAdListener b;
    private InterstitialAd c;
    private boolean d;
    private boolean e;

    public a(String str, String str2, String str3) {
        super(str, str2, str3);
        this.d = false;
        this.e = false;
    }

    public final void a(InterstitialAdListener interstitialAdListener) {
        this.a = interstitialAdListener;
    }

    @Override // com.fyber.ads.interstitials.b.c
    public final void b(InterstitialAdListener interstitialAdListener) {
        this.b = interstitialAdListener;
    }

    public final void g() {
        if (!this.e) {
            this.e = true;
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.ShowImpression).a(this).b();
            if (this.b != null) {
                this.b.onAdShown(this.c);
            }
        }
    }

    public final void a(String str, InterstitialAdCloseReason interstitialAdCloseReason) {
        b.a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
        if (interstitialAdCloseReason == null) {
            interstitialAdCloseReason = !this.e ? InterstitialAdCloseReason.ReasonUnknown : this.d ? InterstitialAdCloseReason.ReasonUserClickedOnAd : InterstitialAdCloseReason.ReasonUserClosedAd;
        }
        if (this.e && !this.d) {
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.ShowClose).a(str).a(this).b();
        }
        if (this.a != null) {
            this.a.onAdClosed(this.c, interstitialAdCloseReason);
        }
        if (this.b != null) {
            this.b.onAdClosed(this.c, interstitialAdCloseReason);
        }
    }

    public final void a(String str) {
        b(str, null);
    }

    public final void b(String str, String str2) {
        b.a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
        new com.fyber.b.b.b.a(com.fyber.ads.a.a.ShowError).a(str2).a(this).b();
        if (this.a != null) {
            this.a.onAdError(this.c, str);
        }
        if (this.b != null) {
            this.b.onAdError(this.c, str);
        }
    }

    public final void h() {
        if (!this.d) {
            this.d = true;
            new com.fyber.b.b.b.a(com.fyber.ads.a.a.ShowClick).a(this).b();
            if (this.b != null) {
                this.b.onAdClicked(this.c);
            }
        }
    }

    public final InterstitialAd i() {
        if (this.c == null) {
            this.c = new InterstitialAd(this);
        }
        return this.c;
    }

    public final void a(Activity activity) {
        b.a(com.fyber.ads.a.b.SHOWING_OFFERS);
        b.a((a) null);
        if (!this.e) {
            if (com.fyber.mediation.b.a.a(activity, this)) {
                return;
            }
            b("The current network is not available", null);
            return;
        }
        b("The Ad was already shown.", null);
    }
}
