package com.fyber.mediation.chartboost.rv;

import android.app.Activity;
import android.content.Context;
import com.chartboost.sdk.Chartboost;
import com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter;
import com.fyber.ads.videos.mediation.TPNVideoValidationResult;
import com.fyber.mediation.chartboost.ChartboostMediationAdapter;

/* JADX INFO: loaded from: classes.dex */
public class ChartboostVideoMediationAdapter extends RewardedVideoMediationAdapter<ChartboostMediationAdapter> implements IFyberChartboostRV {
    private static final String TAG = ChartboostVideoMediationAdapter.class.getSimpleName();

    public ChartboostVideoMediationAdapter(ChartboostMediationAdapter adapter) {
        super(adapter);
        Chartboost.setShouldPrefetchVideoContent(true);
        checkForVideos();
    }

    public void checkForVideos() {
        Chartboost.cacheRewardedVideo(ChartboostMediationAdapter.LOCATION_REWARDED_VIDEO_DEFAULT);
    }

    @Override // com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter
    public void videosAvailable(Context context) {
        if (Chartboost.hasRewardedVideo(ChartboostMediationAdapter.LOCATION_REWARDED_VIDEO_DEFAULT)) {
            sendValidationEvent(TPNVideoValidationResult.Success);
        } else {
            sendValidationEvent(TPNVideoValidationResult.NoVideoAvailable);
            checkForVideos();
        }
    }

    @Override // com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter
    public void startVideo(Activity parentActivity) {
        if (Chartboost.hasRewardedVideo(ChartboostMediationAdapter.LOCATION_REWARDED_VIDEO_DEFAULT)) {
            Chartboost.showRewardedVideo(ChartboostMediationAdapter.LOCATION_REWARDED_VIDEO_DEFAULT);
        } else {
            notifyVideoError();
        }
    }

    @Override // com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter
    public void startPrecaching() {
        checkForVideos();
    }

    @Override // com.fyber.mediation.chartboost.rv.IFyberChartboostRV
    public void fyberSendValidationEvent(TPNVideoValidationResult result) {
        sendValidationEvent(result);
    }

    @Override // com.fyber.mediation.chartboost.rv.IFyberChartboostRV
    public void fyberNotifyVideoStarted() {
        notifyVideoStarted();
    }

    @Override // com.fyber.mediation.chartboost.rv.IFyberChartboostRV
    public void fyberNotifyVideoLoadError() {
        sendValidationEvent(TPNVideoValidationResult.NoVideoAvailable);
    }

    @Override // com.fyber.mediation.chartboost.rv.IFyberChartboostRV
    public void fyberNotifyCloseEngagement() {
        notifyCloseEngagement();
    }

    @Override // com.fyber.mediation.chartboost.rv.IFyberChartboostRV
    public void fyberSetVideoPlayed() {
        setVideoPlayed();
    }
}
