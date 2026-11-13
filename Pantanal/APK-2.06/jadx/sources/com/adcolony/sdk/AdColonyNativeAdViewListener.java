package com.adcolony.sdk;

/* JADX INFO: loaded from: classes.dex */
public abstract class AdColonyNativeAdViewListener extends e {
    public abstract void onRequestFilled(AdColonyNativeAdView adColonyNativeAdView);

    public void onRequestNotFilled(AdColonyZone zone) {
    }

    public void onOpened(AdColonyNativeAdView ad) {
    }

    public void onClosed(AdColonyNativeAdView ad) {
    }

    public void onIAPEvent(AdColonyNativeAdView ad, String product_id, int engagement_type) {
    }

    public void onNativeVideoStarted(AdColonyNativeAdView ad) {
    }

    public void onNativeVideoFinished(AdColonyNativeAdView ad) {
    }

    public void onMuted(AdColonyNativeAdView ad) {
    }

    public void onUnmuted(AdColonyNativeAdView ad) {
    }
}
