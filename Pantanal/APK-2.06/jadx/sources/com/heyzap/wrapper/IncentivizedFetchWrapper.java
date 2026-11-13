package com.heyzap.wrapper;

import android.content.Intent;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.fyber.requesters.RewardedVideoRequester;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.SettableFuture;
import com.heyzap.internal.Logger;
import com.heyzap.internal.RetryManager;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.HeyzapIncentivizedActivity;

/* JADX INFO: loaded from: classes.dex */
public class IncentivizedFetchWrapper extends AbstractFetchWrapper<Intent> {
    int[] initialRetrySchedule = {20, 60, 600};
    int[] regularRetrySchedule = {6, 20, 60, 600};
    public int fetchCount = 0;

    @Override // com.heyzap.wrapper.AbstractFetchWrapper
    public SettableFuture<CachedAd<Intent>> fetch() {
        Logger.debug(getClass() + " fetching");
        SettableFuture<CachedAd<Intent>> returnFuture = SettableFuture.create();
        new RetryManager(new IncentivizedFetchRetryableTask(returnFuture), new RetryManager.CustomArraySchedule(this.fetchCount == 0 ? this.initialRetrySchedule : this.regularRetrySchedule), ExecutorPool.getInstance()).start();
        this.fetchCount++;
        return returnFuture;
    }

    private class IncentivizedFetchRetryableTask extends RetryManager.UIThreadRetryableTask {
        private final SettableFuture<CachedAd<Intent>> cachedAdSettableFuture;

        private IncentivizedFetchRetryableTask(SettableFuture<CachedAd<Intent>> cachedAdSettableFuture) {
            this.cachedAdSettableFuture = cachedAdSettableFuture;
        }

        @Override // com.heyzap.internal.RetryManager.RetryableTask
        public void onCancelled() {
            Logger.debug(getClass() + " onCancelled");
            this.cachedAdSettableFuture.set(null);
        }

        @Override // com.heyzap.internal.RetryManager.UIThreadRetryableTask
        public void runOnUiThread() {
            Logger.debug(getClass() + " executing rewarded request trial");
            RewardedVideoRequester.create(new RequestCallback() { // from class: com.heyzap.wrapper.IncentivizedFetchWrapper.IncentivizedFetchRetryableTask.1
                @Override // com.fyber.requesters.RequestCallback
                public void onAdAvailable(Intent intent) {
                    Logger.debug(getClass() + " onAdAvailable");
                    Intent betterInent = new Intent(HeyzapAds.applicationContext, (Class<?>) HeyzapIncentivizedActivity.class);
                    betterInent.putExtras(intent.getExtras());
                    IncentivizedFetchRetryableTask.this.cachedAdSettableFuture.set(new CachedAd(betterInent));
                }

                @Override // com.fyber.requesters.RequestCallback
                public void onAdNotAvailable(AdFormat adFormat) {
                    Logger.debug(getClass() + "onAdNotAvailable - " + String.valueOf(adFormat));
                    IncentivizedFetchRetryableTask.this.retry();
                }

                @Override // com.fyber.requesters.Callback
                public void onRequestError(RequestError requestError) {
                    Logger.debug(getClass() + "onRequestError - " + requestError.getDescription());
                    IncentivizedFetchRetryableTask.this.retry();
                }
            }).request(HeyzapAds.applicationContext);
        }
    }
}
