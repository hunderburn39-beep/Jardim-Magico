package com.fyber.mediation.adcolony.rv;

import android.app.Activity;
import android.content.Context;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter;
import com.fyber.ads.videos.mediation.TPNVideoValidationResult;
import com.fyber.mediation.adcolony.AdColonyMediationAdapter;
import com.fyber.utils.FyberLogger;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyVideoMediationAdapter extends RewardedVideoMediationAdapter<AdColonyMediationAdapter> implements AdColonyRewardListener {
    private static String TAG = AdColonyVideoMediationAdapter.class.getSimpleName();
    private AdColonyInterstitial mAdColonyVideoInterstitial;
    AdColonyInterstitialListener mListener;
    private Boolean mShouldShowConfirmationDialog;
    private List<String> mVideoZoneIds;
    private List<String> mZoneBlacklist;

    public AdColonyVideoMediationAdapter(AdColonyMediationAdapter adapter, List<String> pZoneIdsVideo, boolean shouldShowConfirmationDialog) {
        super(adapter);
        this.mListener = new AdColonyInterstitialListener() { // from class: com.fyber.mediation.adcolony.rv.AdColonyVideoMediationAdapter.1
            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
                AdColonyVideoMediationAdapter.this.sendValidationEvent(TPNVideoValidationResult.Success);
                AdColonyVideoMediationAdapter.this.mAdColonyVideoInterstitial = adColonyInterstitial;
            }

            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onRequestNotFilled(AdColonyZone zone) {
                AdColonyVideoMediationAdapter.this.sendValidationEvent(TPNVideoValidationResult.NoVideoAvailable);
                AdColonyVideoMediationAdapter.this.mAdColonyVideoInterstitial = null;
                AdColonyVideoMediationAdapter.this.mZoneBlacklist.add(zone.getZoneID());
            }

            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onOpened(AdColonyInterstitial ad) {
                FyberLogger.d(AdColonyVideoMediationAdapter.TAG, "VIDEO STARTED");
                AdColonyVideoMediationAdapter.this.notifyVideoStarted();
            }

            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onClosed(AdColonyInterstitial ad) {
                FyberLogger.d(AdColonyVideoMediationAdapter.TAG, "VIDEO CLOSED");
                AdColonyVideoMediationAdapter.this.notifyCloseEngagement();
                AdColonyVideoMediationAdapter.this.mAdColonyVideoInterstitial = null;
            }
        };
        AdColony.setRewardListener(this);
        this.mVideoZoneIds = pZoneIdsVideo;
        this.mZoneBlacklist = new ArrayList();
        this.mShouldShowConfirmationDialog = Boolean.valueOf(shouldShowConfirmationDialog);
    }

    @Override // com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter
    public void videosAvailable(Context context) {
        initRewardedVideo();
    }

    @Override // com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter
    public void startVideo(Activity parentActivity) {
        if (this.mAdColonyVideoInterstitial != null) {
            this.mAdColonyVideoInterstitial.show();
        } else {
            notifyVideoError();
        }
    }

    @Override // com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter
    public void startPrecaching() {
    }

    private void initRewardedVideo() {
        if (this.mZoneBlacklist.size() == this.mVideoZoneIds.size()) {
            this.mZoneBlacklist.clear();
        }
        String tZoneId = null;
        for (String mVideoZoneId : this.mVideoZoneIds) {
            if (!this.mZoneBlacklist.contains(mVideoZoneId)) {
                tZoneId = mVideoZoneId;
                break;
            }
        }
        FyberLogger.w(getClass().getSimpleName(), "Got zone id for rewarded video: " + tZoneId);
        AdColonyAdOptions options = new AdColonyAdOptions();
        if (this.mShouldShowConfirmationDialog.booleanValue()) {
            options.enableConfirmationDialog(true).enableResultsDialog(true);
        }
        AdColony.requestInterstitial(tZoneId, this.mListener, options);
    }

    @Override // com.adcolony.sdk.AdColonyRewardListener
    public void onReward(AdColonyReward adColonyReward) {
        if (adColonyReward.success()) {
            setVideoPlayed();
        }
        FyberLogger.d(TAG, "Reward callback from AdColony:\nsuccess: " + adColonyReward.success() + " amount: " + adColonyReward.getRewardAmount());
    }
}
