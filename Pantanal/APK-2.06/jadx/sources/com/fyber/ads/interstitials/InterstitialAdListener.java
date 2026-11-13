package com.fyber.ads.interstitials;

/* JADX INFO: loaded from: classes.dex */
public interface InterstitialAdListener {
    void onAdClicked(InterstitialAd interstitialAd);

    void onAdClosed(InterstitialAd interstitialAd, InterstitialAdCloseReason interstitialAdCloseReason);

    void onAdError(InterstitialAd interstitialAd, String str);

    void onAdShown(InterstitialAd interstitialAd);
}
