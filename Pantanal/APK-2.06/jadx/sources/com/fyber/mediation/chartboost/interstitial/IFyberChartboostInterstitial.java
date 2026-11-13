package com.fyber.mediation.chartboost.interstitial;

/* JADX INFO: loaded from: classes.dex */
public interface IFyberChartboostInterstitial {
    void fyberInterstitialClicked();

    void fyberInterstitialClosed();

    void fyberInterstitialError(String str);

    void fyberInterstitialShown();

    void fyberSetAdAvailable();

    void fyberSetAdError(String str);

    void fyberSetAdUnavailable();
}
