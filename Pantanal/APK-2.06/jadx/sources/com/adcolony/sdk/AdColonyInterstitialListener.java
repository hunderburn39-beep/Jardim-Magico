package com.adcolony.sdk;

/* JADX INFO: loaded from: classes.dex */
public abstract class AdColonyInterstitialListener {
    public abstract void onRequestFilled(AdColonyInterstitial adColonyInterstitial);

    public void onRequestNotFilled(AdColonyZone zone) {
    }

    public void onOpened(AdColonyInterstitial ad) {
    }

    public void onClosed(AdColonyInterstitial ad) {
    }

    public void onIAPEvent(AdColonyInterstitial ad, String product_id, int engagement_type) {
    }

    public void onExpiring(AdColonyInterstitial ad) {
    }

    void a(AdColonyInterstitial adColonyInterstitial) {
    }
}
