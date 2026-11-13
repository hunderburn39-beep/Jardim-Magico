package com.fyber.ads.interstitials.mediation;

import android.app.Activity;
import android.content.Context;
import com.fyber.Fyber;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.ads.interstitials.InterstitialAdCloseReason;
import com.fyber.exceptions.a;
import com.fyber.mediation.MediationAdapter;
import com.fyber.utils.c;
import java.util.Map;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public abstract class InterstitialMediationAdapter<V extends MediationAdapter> {
    protected static final String CREATIVE_TYPE_KEY = "creative_type";
    public static final String ERROR_NO_PLACEMENT_ID = "no_placement_id";
    protected static final String TPN_PLACEMENT_ID_KEY = "tpn_placement_id";
    protected V adapter;
    protected c<InterstitialAd, a> asyncCallable;
    protected com.fyber.ads.interstitials.b.a internalInterstitialAd;

    protected abstract void checkForAds(Context context);

    protected abstract void show(Activity activity);

    public InterstitialMediationAdapter(V v) {
        this.adapter = v;
    }

    protected void setAdAvailable() {
        if (this.asyncCallable != null && this.internalInterstitialAd != null) {
            this.asyncCallable.a(this.internalInterstitialAd.i());
        }
        this.asyncCallable = null;
    }

    protected void setAdNotAvailable() {
        if (this.asyncCallable != null) {
            this.asyncCallable.a((InterstitialAd) null);
        }
        this.asyncCallable = null;
        this.internalInterstitialAd = null;
    }

    protected void setAdError(String str) {
        setAdError(str, str);
    }

    protected void setAdError(String str, String str2) {
        if (this.asyncCallable != null) {
            this.asyncCallable.a(new a(str, str2));
        }
        this.asyncCallable = null;
        this.internalInterstitialAd = null;
    }

    public final Future<InterstitialAd> a(Context context, com.fyber.ads.interstitials.b.a aVar) {
        this.internalInterstitialAd = aVar;
        this.asyncCallable = new c<>();
        Future<InterstitialAd> futureA = Fyber.getConfigs().a(this.asyncCallable);
        checkForAds(context);
        return futureA;
    }

    public final void a(Activity activity) {
        show(activity);
    }

    protected void interstitialShown() {
        if (this.internalInterstitialAd != null) {
            this.internalInterstitialAd.g();
        }
    }

    protected void interstitialClicked() {
        if (this.internalInterstitialAd != null) {
            this.internalInterstitialAd.h();
        }
    }

    protected void interstitialClosed() {
        interstitialClosed(null);
    }

    protected void interstitialClosed(String str) {
        interstitialClosed(str, null);
    }

    protected void interstitialClosed(String str, InterstitialAdCloseReason interstitialAdCloseReason) {
        if (this.internalInterstitialAd != null) {
            this.internalInterstitialAd.a(str, interstitialAdCloseReason);
            this.internalInterstitialAd = null;
        }
    }

    protected void interstitialError(String str) {
        interstitialError(str, null);
    }

    protected void interstitialError(String str, String str2) {
        if (this.internalInterstitialAd != null) {
            this.internalInterstitialAd.b(str, str2);
        }
        this.internalInterstitialAd = null;
    }

    protected Map<String, String> getContextData() {
        return this.internalInterstitialAd.c();
    }

    protected com.fyber.ads.interstitials.a.a getCreativeType() {
        String str = this.internalInterstitialAd != null ? this.internalInterstitialAd.c().get(CREATIVE_TYPE_KEY) : "";
        if (str == null) {
            return com.fyber.ads.interstitials.a.a.UNDEFINED;
        }
        switch (str) {
            case "video":
                return com.fyber.ads.interstitials.a.a.VIDEO;
            case "static":
                return com.fyber.ads.interstitials.a.a.STATIC;
            default:
                return com.fyber.ads.interstitials.a.a.UNDEFINED;
        }
    }
}
