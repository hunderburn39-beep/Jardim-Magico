package com.fyber.ads.banners;

/* JADX INFO: loaded from: classes.dex */
public interface BannerAdListener {
    void onAdClicked(BannerAd bannerAd);

    void onAdError(BannerAd bannerAd, String str);

    void onAdLeftApplication(BannerAd bannerAd);

    void onAdLoaded(BannerAd bannerAd);
}
