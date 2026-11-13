package com.fyber.mediation.chartboost;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.fyber.Fyber;
import com.fyber.ads.banners.mediation.BannerMediationAdapter;
import com.fyber.mediation.MediationAdapter;
import com.fyber.mediation.annotations.AdapterDefinition;
import com.fyber.mediation.chartboost.interstitial.ChartboostInterstitialMediationAdapter;
import com.fyber.mediation.chartboost.rv.ChartboostVideoMediationAdapter;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@AdapterDefinition(apiVersion = 5, name = ChartboostMediationAdapter.ADAPTER_NAME, sdkFeatures = {"banners", "blended"}, version = ChartboostMediationAdapter.ADAPTER_VERSION)
public class ChartboostMediationAdapter extends MediationAdapter {
    public static final String ADAPTER_NAME = "Chartboost";
    public static final String ADAPTER_VERSION = "6.6.0-r1";
    public static final String APP_ID_KEY = "AppId";
    public static final String APP_SIGNATURE_KEY = "AppSignature";
    public static final String AUTOCACHE_ENABLED_KEY = "AutocacheEnabled";
    public static final String INT_CACHE_KEY = "CacheInterstitials";
    public static final String LOCATION_INTERSTITIAL_DEFAULT = "fyber_interstitial";
    public static final String LOCATION_REWARDED_VIDEO_DEFAULT = "fyber_rewarded_video";
    public static final String LOG_LEVEL_KEY = "LogLevel";
    public static final String RV_CACHE_KEY = "CacheRewardedVideo";
    public static final String TAG = ChartboostMediationAdapter.class.getSimpleName();
    private boolean mCacheInterstitials;
    private boolean mCacheRewardedVideo;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ChartboostInterstitialMediationAdapter mInterstitialMediationAdapter;
    private ChartboostVideoMediationAdapter mVideoMediationAdapter;

    @Override // com.fyber.mediation.MediationAdapter
    public boolean startAdapter(final Activity activity, final Map<String, Object> configs) {
        FyberLogger.i(TAG, "Starting Chartboost adapter.");
        final String appId = (String) getConfiguration(configs, APP_ID_KEY, String.class);
        final String appSignature = (String) getConfiguration(configs, APP_SIGNATURE_KEY, String.class);
        if (StringUtils.nullOrEmpty(appId)) {
            FyberLogger.w(TAG, "Chartboost App ID is missing. Adapter won't start.");
            return false;
        }
        if (StringUtils.nullOrEmpty(appSignature)) {
            FyberLogger.w(TAG, "Chartboost App Signature is missing. Adapter won't start.");
            return false;
        }
        this.mCacheRewardedVideo = ((Boolean) getConfiguration(configs, RV_CACHE_KEY, true, Boolean.class)).booleanValue();
        this.mCacheInterstitials = ((Boolean) getConfiguration(configs, INT_CACHE_KEY, true, Boolean.class)).booleanValue();
        this.mHandler.post(new Runnable() { // from class: com.fyber.mediation.chartboost.ChartboostMediationAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                CBLogging.Level lvl;
                Chartboost.startWithAppId(activity, appId, appSignature);
                Chartboost.onCreate(activity);
                Chartboost.setAutoCacheAds(ChartboostMediationAdapter.this.mCacheInterstitials || ChartboostMediationAdapter.this.mCacheRewardedVideo);
                ChartboostMediationAdapter.this.mInterstitialMediationAdapter = new ChartboostInterstitialMediationAdapter(ChartboostMediationAdapter.this);
                ChartboostMediationAdapter.this.mVideoMediationAdapter = new ChartboostVideoMediationAdapter(ChartboostMediationAdapter.this);
                Chartboost.setDelegate(ChartboostMediationAdapter.this.new FyberChartboostDelegate());
                Chartboost.onStart(activity);
                Chartboost.onResume(activity);
                Chartboost.setMediation(Chartboost.CBMediation.CBMediationFyber, Fyber.RELEASE_VERSION_STRING);
                String logLevel = (String) MediationAdapter.getConfiguration(configs, ChartboostMediationAdapter.LOG_LEVEL_KEY, String.class);
                try {
                    lvl = CBLogging.Level.valueOf(logLevel);
                } catch (IllegalArgumentException | NullPointerException e) {
                    FyberLogger.i(ChartboostMediationAdapter.TAG, "No or not proper value has been passed to 'LogLevel' setting key, setting ALL by default.");
                    lvl = CBLogging.Level.ALL;
                }
                Chartboost.setLoggingLevel(lvl);
                Chartboost.setCustomId(ChartboostMediationAdapter.this.getUserId());
            }
        });
        return true;
    }

    private void enableActivityCallbacks() {
    }

    @Override // com.fyber.mediation.MediationAdapter
    public String getName() {
        return ADAPTER_NAME;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public String getVersion() {
        return ADAPTER_VERSION;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public ChartboostVideoMediationAdapter getVideoMediationAdapter() {
        return this.mVideoMediationAdapter;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public ChartboostInterstitialMediationAdapter getInterstitialMediationAdapter() {
        return this.mInterstitialMediationAdapter;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public BannerMediationAdapter<ChartboostMediationAdapter> getBannerMediationAdapter() {
        return null;
    }

    public boolean shouldCacheInterstitials() {
        return this.mCacheInterstitials;
    }

    public boolean shouldCacheRewardedVideo() {
        return this.mCacheRewardedVideo;
    }

    @Override // com.fyber.mediation.MediationAdapter
    protected Set<?> getListeners() {
        return null;
    }

    public class FyberChartboostDelegate extends ChartboostDelegate {
        private final String TAG = "FyberChartboostDelegate";

        public FyberChartboostDelegate() {
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didInitialize() {
            logMessage("CB did initialize, delaying call for ads for 1500 ms...");
            ChartboostMediationAdapter.this.mHandler.postDelayed(new Runnable() { // from class: com.fyber.mediation.chartboost.ChartboostMediationAdapter.FyberChartboostDelegate.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ChartboostMediationAdapter.this.mVideoMediationAdapter != null && ChartboostMediationAdapter.this.shouldCacheRewardedVideo()) {
                        FyberChartboostDelegate.this.logMessage("Precaching rewarded video...");
                        ChartboostMediationAdapter.this.mVideoMediationAdapter.checkForVideos();
                    }
                    if (ChartboostMediationAdapter.this.mInterstitialMediationAdapter != null && ChartboostMediationAdapter.this.shouldCacheInterstitials()) {
                        FyberChartboostDelegate.this.logMessage("Precaching interstitial ad...");
                        ChartboostMediationAdapter.this.mInterstitialMediationAdapter.checkForInterstitial();
                    }
                }
            }, 1500L);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public boolean shouldRequestInterstitial(String location) {
            boolean isShouldRequest = super.shouldRequestInterstitial(location);
            logMessage("Should we request for an interstitial? " + isShouldRequest);
            return isShouldRequest;
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public boolean shouldDisplayInterstitial(String location) {
            boolean isShouldDisplay = super.shouldRequestInterstitial(location);
            logMessage("Should we display an interstitial? " + isShouldDisplay);
            return isShouldDisplay;
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didCacheInterstitial(String location) {
            super.didCacheInterstitial(location);
            logMessage("Interstitial has been cached.");
            ChartboostMediationAdapter.this.mInterstitialMediationAdapter.fyberSetAdAvailable();
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error) {
            logMessage("Interstitial load failed.");
            super.didFailToLoadInterstitial(location, error);
            if (error.equals(CBError.CBImpressionError.NO_AD_FOUND)) {
                FyberLogger.w("FyberChartboostDelegate", "Location: " + location);
                FyberLogger.w("FyberChartboostDelegate", "Error: " + error);
                ChartboostMediationAdapter.this.mInterstitialMediationAdapter.fyberSetAdUnavailable();
            } else {
                FyberLogger.e("FyberChartboostDelegate", "Location: " + location);
                FyberLogger.e("FyberChartboostDelegate", "Error: " + error);
                ChartboostMediationAdapter.this.mInterstitialMediationAdapter.fyberSetAdError(error.toString());
            }
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didDismissInterstitial(String location) {
            logMessage("Interstitial has been dismissed.");
            super.didDismissInterstitial(location);
            ChartboostMediationAdapter.this.mInterstitialMediationAdapter.fyberInterstitialClosed();
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didCloseInterstitial(String location) {
            logMessage("Interstitial has been closed.");
            super.didCloseInterstitial(location);
            ChartboostMediationAdapter.this.mInterstitialMediationAdapter.fyberInterstitialClosed();
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didClickInterstitial(String location) {
            logMessage("Interstitial has been clicked.");
            super.didClickInterstitial(location);
            ChartboostMediationAdapter.this.mInterstitialMediationAdapter.fyberInterstitialClicked();
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didDisplayInterstitial(String location) {
            logMessage("Interstitial has been displayed.");
            super.didDisplayInterstitial(location);
            ChartboostMediationAdapter.this.mInterstitialMediationAdapter.fyberInterstitialShown();
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didFailToRecordClick(String uri, CBError.CBClickError error) {
            logMessage("Click failed to be recorded.");
            FyberLogger.e("FyberChartboostDelegate", "URI: " + uri);
            FyberLogger.e("FyberChartboostDelegate", "Error: " + error);
            super.didFailToRecordClick(uri, error);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public boolean shouldDisplayRewardedVideo(String location) {
            boolean isShouldDisplay = super.shouldRequestInterstitial(location);
            logMessage("Should we display RV? " + isShouldDisplay);
            return isShouldDisplay;
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didCacheRewardedVideo(String location) {
            logMessage("RV has been cached");
            super.didCacheRewardedVideo(location);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didFailToLoadRewardedVideo(String location, CBError.CBImpressionError error) {
            logMessage("RV failed to be loaded.");
            FyberLogger.e("FyberChartboostDelegate", "Location: " + location);
            FyberLogger.e("FyberChartboostDelegate", "Error: " + error);
            ChartboostMediationAdapter.this.mVideoMediationAdapter.fyberNotifyVideoLoadError();
            super.didFailToLoadRewardedVideo(location, error);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didDismissRewardedVideo(String location) {
            logMessage("RV has been dismissed - let's DO NOT notify as closed");
            super.didDismissRewardedVideo(location);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didCloseRewardedVideo(String location) {
            logMessage("RV has been closed");
            ChartboostMediationAdapter.this.mVideoMediationAdapter.fyberNotifyCloseEngagement();
            super.didCloseRewardedVideo(location);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didClickRewardedVideo(String location) {
            logMessage("RV has been clicked");
            super.didClickRewardedVideo(location);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didCompleteRewardedVideo(String location, int reward) {
            logMessage("RV has been completed");
            super.didCompleteRewardedVideo(location, reward);
            ChartboostMediationAdapter.this.mVideoMediationAdapter.fyberSetVideoPlayed();
            Chartboost.cacheRewardedVideo(ChartboostMediationAdapter.LOCATION_REWARDED_VIDEO_DEFAULT);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void didDisplayRewardedVideo(String location) {
            logMessage("RV has just been shown");
            super.didDisplayRewardedVideo(location);
        }

        @Override // com.chartboost.sdk.ChartboostDelegate, com.chartboost.sdk.a
        public void willDisplayVideo(String location) {
            logMessage("RV is about to be displayed");
            super.willDisplayVideo(location);
            ChartboostMediationAdapter.this.mVideoMediationAdapter.fyberNotifyVideoStarted();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void logMessage(String msg) {
            FyberLogger.w("FyberChartboostDelegate", msg);
        }
    }
}
