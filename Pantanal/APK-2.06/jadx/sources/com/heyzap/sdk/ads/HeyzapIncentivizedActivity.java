package com.heyzap.sdk.ads;

import android.os.Bundle;
import com.fyber.ads.videos.RewardedVideoActivity;
import com.heyzap.internal.Constants;

/* JADX INFO: loaded from: classes.dex */
public final class HeyzapIncentivizedActivity extends RewardedVideoActivity {
    private boolean shown = false;
    private boolean closed = false;

    @Override // com.fyber.ads.videos.RewardedVideoActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.closed) {
            IncentivizedAd.getStatusListener().onShow(Constants.DEFAULT_TAG);
            IncentivizedAd.getStatusListener().onAudioStarted();
            this.shown = true;
        }
    }

    @Override // com.fyber.ads.videos.RewardedVideoActivity, com.fyber.ads.videos.c
    public void didReceiveOffers(boolean areOffersAvailable) {
        this.shown = true;
        IncentivizedAd.getStatusListener().onShow(Constants.DEFAULT_TAG);
        IncentivizedAd.getStatusListener().onAudioStarted();
        super.didReceiveOffers(areOffersAvailable);
    }

    @Override // com.fyber.ads.videos.RewardedVideoActivity
    protected void setResultAndClose(String message) {
        if (this.shown) {
            IncentivizedAd.getStatusListener().onHide(Constants.DEFAULT_TAG);
            IncentivizedAd.getStatusListener().onAudioFinished();
        } else {
            IncentivizedAd.getStatusListener().onFailedToShow(Constants.DEFAULT_TAG);
        }
        this.closed = true;
        if (RewardedVideoActivity.REQUEST_STATUS_PARAMETER_FINISHED_VALUE.equals(message)) {
            IncentivizedAd.getOnIncentiveResultListener().onComplete(Constants.DEFAULT_TAG);
        } else if (RewardedVideoActivity.REQUEST_STATUS_PARAMETER_ABORTED_VALUE.equals(message)) {
            IncentivizedAd.getOnIncentiveResultListener().onIncomplete(Constants.DEFAULT_TAG);
        }
        super.setResultAndClose(message);
        IncentivizedAd.onDisplayCompleted();
    }
}
