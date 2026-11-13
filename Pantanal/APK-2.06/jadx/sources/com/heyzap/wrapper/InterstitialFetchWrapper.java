package com.heyzap.wrapper;

import android.content.Intent;
import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.requesters.AdRequestCallback;
import com.fyber.requesters.InterstitialRequester;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.SettableFuture;
import com.heyzap.internal.Logger;
import com.heyzap.internal.RetryManager;
import com.heyzap.sdk.ads.HeyzapAds;

/* JADX INFO: loaded from: classes.dex */
public class InterstitialFetchWrapper extends AbstractFetchWrapper<InterstitialAd> {
    int[] initialRetrySchedule = {20, 60, 600};
    int[] regularRetrySchedule = {6, 20, 60, 600};
    public int fetchCount = 0;

    @Override // com.heyzap.wrapper.AbstractFetchWrapper
    public SettableFuture<CachedAd<InterstitialAd>> fetch() {
        Logger.debug(getClass() + " fetching");
        SettableFuture<CachedAd<InterstitialAd>> returnFuture = SettableFuture.create();
        new RetryManager(new InterstitialFetchRetryableTask(returnFuture), new RetryManager.CustomArraySchedule(this.fetchCount == 0 ? this.initialRetrySchedule : this.regularRetrySchedule), ExecutorPool.getInstance()).start();
        this.fetchCount++;
        return returnFuture;
    }

    private class InterstitialFetchRetryableTask extends RetryManager.UIThreadRetryableTask {
        private final SettableFuture<CachedAd<InterstitialAd>> cachedAdSettableFuture;

        private InterstitialFetchRetryableTask(SettableFuture<CachedAd<InterstitialAd>> cachedAdSettableFuture) {
            this.cachedAdSettableFuture = cachedAdSettableFuture;
        }

        @Override // com.heyzap.internal.RetryManager.RetryableTask
        public void onCancelled() {
            Logger.debug(getClass() + " onCancelled");
            this.cachedAdSettableFuture.set(null);
        }

        @Override // com.heyzap.internal.RetryManager.UIThreadRetryableTask
        public void runOnUiThread() {
            Logger.debug(getClass() + " executing interstitial request trial");
            if (FetchLock.attemptLock(FetchLock.LockType.INTERSTITIAL)) {
                Logger.debug(getClass() + " banner not fetching, starting fetch");
                InterstitialRequester.create(InterstitialFetchWrapper.this.new FyberAdRequestCallback(this, this.cachedAdSettableFuture)).request(HeyzapAds.applicationContext);
            } else {
                Logger.debug(getClass() + " banner fetching, will try to fetch later");
                retry();
            }
        }
    }

    private class FyberAdRequestCallback implements AdRequestCallback, RequestCallback {
        private SettableFuture<CachedAd<InterstitialAd>> cachedAdSettableFuture;
        private InterstitialFetchRetryableTask interstitialFetchRetryableTask;

        public FyberAdRequestCallback(InterstitialFetchRetryableTask interstitialFetchRetryableTask, SettableFuture<CachedAd<InterstitialAd>> cachedAdSettableFuture) {
            this.interstitialFetchRetryableTask = interstitialFetchRetryableTask;
            this.cachedAdSettableFuture = cachedAdSettableFuture;
        }

        @Override // com.fyber.requesters.RequestCallback
        public void onAdAvailable(Intent intent) {
            FetchLock.attemptUnlock(FetchLock.LockType.INTERSTITIAL);
        }

        @Override // com.fyber.requesters.AdRequestCallback
        public void onAdAvailable(Ad intersitialAd) {
            Logger.debug(getClass() + " onAdAvailable");
            this.cachedAdSettableFuture.set(new CachedAd<>((InterstitialAd) intersitialAd));
            FetchLock.attemptUnlock(FetchLock.LockType.INTERSTITIAL);
        }

        @Override // com.fyber.requesters.AdRequestCallback
        public void onAdNotAvailable(AdFormat adFormat) {
            Logger.log(getClass() + "onAdNotAvailable - " + String.valueOf(adFormat));
            FetchLock.attemptUnlock(FetchLock.LockType.INTERSTITIAL);
            this.interstitialFetchRetryableTask.retry();
        }

        @Override // com.fyber.requesters.Callback
        public void onRequestError(RequestError requestError) {
            Logger.log(getClass() + "onRequestError - " + requestError.getDescription());
            FetchLock.attemptUnlock(FetchLock.LockType.INTERSTITIAL);
            this.interstitialFetchRetryableTask.retry();
        }
    }
}
