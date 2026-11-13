package com.chartboost.sdk;

import com.chartboost.sdk.Model.CBError;

/* JADX INFO: loaded from: classes.dex */
public abstract class ChartboostDelegate implements a {
    @Override // com.chartboost.sdk.a
    public boolean shouldRequestInterstitial(String location) {
        return true;
    }

    @Override // com.chartboost.sdk.a
    public boolean shouldDisplayInterstitial(String location) {
        return true;
    }

    @Override // com.chartboost.sdk.a
    public void didCacheInterstitial(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error) {
    }

    @Override // com.chartboost.sdk.a
    public void didDismissInterstitial(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didCloseInterstitial(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didClickInterstitial(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didDisplayInterstitial(String location) {
    }

    @Override // com.chartboost.sdk.a
    public boolean shouldRequestMoreApps(String location) {
        return true;
    }

    @Override // com.chartboost.sdk.a
    public void didCacheMoreApps(String location) {
    }

    @Override // com.chartboost.sdk.a
    public boolean shouldDisplayMoreApps(String location) {
        return true;
    }

    @Override // com.chartboost.sdk.a
    public void didFailToLoadMoreApps(String location, CBError.CBImpressionError error) {
    }

    @Override // com.chartboost.sdk.a
    public void didDismissMoreApps(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didCloseMoreApps(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didClickMoreApps(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didDisplayMoreApps(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didFailToRecordClick(String uri, CBError.CBClickError error) {
    }

    @Override // com.chartboost.sdk.a
    public boolean shouldDisplayRewardedVideo(String location) {
        return true;
    }

    @Override // com.chartboost.sdk.a
    public void didCacheRewardedVideo(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didFailToLoadRewardedVideo(String location, CBError.CBImpressionError error) {
    }

    @Override // com.chartboost.sdk.a
    public void didDismissRewardedVideo(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didCloseRewardedVideo(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didClickRewardedVideo(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didCompleteRewardedVideo(String location, int reward) {
    }

    @Override // com.chartboost.sdk.a
    public void didDisplayRewardedVideo(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void willDisplayVideo(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didCacheInPlay(String location) {
    }

    @Override // com.chartboost.sdk.a
    public void didFailToLoadInPlay(String location, CBError.CBImpressionError error) {
    }

    @Override // com.chartboost.sdk.a
    public void didInitialize() {
    }
}
