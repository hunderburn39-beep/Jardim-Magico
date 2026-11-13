package com.heyzap.sdk.extensions.unity3d;

import android.provider.Settings;
import android.view.View;
import com.heyzap.internal.Logger;
import com.heyzap.internal.Utils;
import com.heyzap.sdk.ads.BannerAd;
import com.heyzap.sdk.ads.BannerAdView;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.IncentivizedAd;
import com.heyzap.sdk.ads.InterstitialAd;
import com.heyzap.sdk.ads.WrappedOfferWall;
import com.tapjoy.TapjoyConstants;
import com.unity3d.player.UnityPlayer;

/* JADX INFO: loaded from: classes.dex */
public class UnityHelper {
    private static final String BANNER_POSITION_BOTTOM = "bottom";
    private static final String BANNER_POSITION_TOP = "top";
    private static final String CALLBACK_AUDIO_FINISHED = "audio_finished";
    private static final String CALLBACK_AUDIO_STARTING = "audio_starting";
    private static final String CALLBACK_AVAILABLE = "available";
    private static final String CALLBACK_CLICK = "click";
    private static final String CALLBACK_ERROR = "error";
    private static final String CALLBACK_FAILED = "failed";
    private static final String CALLBACK_FETCH_FAILED = "fetch_failed";
    private static final String CALLBACK_HIDE = "hide";
    private static final String CALLBACK_INCENTIVIZED_COMPLETE = "incentivized_result_complete";
    private static final String CALLBACK_INCENTIVIZED_INCOMPLETE = "incentivized_result_incomplete";
    private static final String CALLBACK_LOADED = "loaded";
    private static final String CALLBACK_SHOW = "show";
    private static final String FRAMEWORK_NAME = "unity3d";
    private static final String LOCAL_BANNER_KLASS = "HZBannerAd";
    private static final String LOCAL_CALLBACK_METHOD = "SetCallback";
    private static final String LOCAL_HEYZAP_KLASS = "HeyzapAds";
    private static final String LOCAL_INCENTIVIZED_KLASS = "HZIncentivizedAd";
    private static final String LOCAL_INTERSTITIAL_KLASS = "HZInterstitialAd";
    private static final String LOCAL_NETWORK_CALLBACK_METHOD = "SetNetworkCallbackMessage";
    private static final String LOCAL_OFFER_WALL_KLASS = "HZOfferWall";
    private static final String LOCAL_VIDEO_KLASS = "HZVideoAd";

    private UnityHelper() {
    }

    public static void start(String publisherId, int flags) {
        try {
            HeyzapAds.framework = FRAMEWORK_NAME;
            HeyzapAds.start(publisherId, UnityPlayer.currentActivity, flags);
            HeyzapAds.slowClose(true);
            Logger.log("HeyzapAds started");
            InterstitialAd.setOnStatusListener(new UnityOnStatusListener(LOCAL_INTERSTITIAL_KLASS, LOCAL_CALLBACK_METHOD));
            IncentivizedAd.setOnStatusListener(new UnityOnStatusListener(LOCAL_INCENTIVIZED_KLASS, LOCAL_CALLBACK_METHOD));
            IncentivizedAd.setOnIncentiveResultListener(new UnityOnStatusListener(LOCAL_INCENTIVIZED_KLASS, LOCAL_CALLBACK_METHOD));
            WrappedOfferWall.setOnStatusListener(new UnityOnStatusListener(LOCAL_OFFER_WALL_KLASS, LOCAL_CALLBACK_METHOD));
            BannerAd.setBannerListener(new UnityOnStatusListener(LOCAL_BANNER_KLASS, LOCAL_CALLBACK_METHOD));
            HeyzapAds.setNetworkCallbackListener(new UnityNetworkCallbackListener(LOCAL_HEYZAP_KLASS, LOCAL_NETWORK_CALLBACK_METHOD));
        } catch (RuntimeException e) {
            Logger.error("Exception in start", e);
            throw e;
        }
    }

    public static boolean onBackPressed() {
        try {
            return HeyzapAds.onBackPressed();
        } catch (RuntimeException e) {
            Logger.error("Exception in onBackPressed", e);
            throw e;
        }
    }

    public static boolean isNetworkInitialized(String network) {
        try {
            return HeyzapAds.isNetworkInitialized(network);
        } catch (RuntimeException e) {
            Logger.error("Exception in isNetworkInitialized", e);
            throw e;
        }
    }

    public static void showOfferWall() {
        WrappedOfferWall.display(UnityPlayer.currentActivity);
    }

    public static void showBanner(String position, String tag, String admobSize, String facebookSize, String inmobiSize) {
        HeyzapAds.CreativeSize admobCreativeSize;
        HeyzapAds.CreativeSize facebookCreativeSize;
        HeyzapAds.CreativeSize inmobiCreativeSize;
        byte b = 0;
        HeyzapAds.BannerOptions options = new HeyzapAds.BannerOptions();
        Logger.debug("Displaying banner from Unity with admob size " + admobSize + ", facebook size " + facebookSize + ", inmobi size " + inmobiSize + " at the " + position);
        switch (admobSize) {
            case "SMART_BANNER":
            case "SMART_BANNER_LANDSCAPE":
                admobCreativeSize = HeyzapAds.CreativeSize.SMART_BANNER;
                break;
            case "BANNER":
                admobCreativeSize = HeyzapAds.CreativeSize.BANNER;
                break;
            case "FULL_BANNER":
                admobCreativeSize = HeyzapAds.CreativeSize.FULL_BANNER;
                break;
            case "LARGE_BANNER":
                admobCreativeSize = HeyzapAds.CreativeSize.LARGE_BANNER;
                break;
            case "MEDIUM_RECTANGLE":
                admobCreativeSize = HeyzapAds.CreativeSize.MEDIUM_RECTANGLE;
                break;
            case "LEADERBOARD":
                admobCreativeSize = HeyzapAds.CreativeSize.LEADERBOARD;
                break;
            case "WIDE_SKYSCRAPER":
                admobCreativeSize = HeyzapAds.CreativeSize.WIDE_SKYSCRAPER;
                break;
            default:
                admobCreativeSize = HeyzapAds.CreativeSize.SMART_BANNER;
                break;
        }
        switch (facebookSize) {
            case "BANNER_320_50":
                facebookCreativeSize = HeyzapAds.CreativeSize.BANNER_320_50;
                break;
            case "BANNER_HEIGHT_50":
                facebookCreativeSize = HeyzapAds.CreativeSize.BANNER_HEIGHT_50;
                break;
            case "BANNER_HEIGHT_90":
                facebookCreativeSize = HeyzapAds.CreativeSize.BANNER_HEIGHT_90;
                break;
            case "BANNER_RECTANGLE_250":
                facebookCreativeSize = HeyzapAds.CreativeSize.BANNER_RECTANGLE_250;
                break;
            default:
                facebookCreativeSize = HeyzapAds.CreativeSize.BANNER_HEIGHT_50;
                break;
        }
        switch (inmobiSize.hashCode()) {
            case -625197933:
                b = !inmobiSize.equals("BANNER_120_600") ? (byte) -1 : (byte) 4;
                break;
            case -463226998:
                b = !inmobiSize.equals("BANNER_RECTANGLE_250") ? (byte) -1 : (byte) 3;
                break;
            case 337468494:
                b = !inmobiSize.equals("BANNER_1024_768") ? (byte) -1 : (byte) 7;
                break;
            case 1092547438:
                b = !inmobiSize.equals("BANNER_300_250") ? (byte) -1 : (byte) 2;
                break;
            case 1976753221:
                b = !inmobiSize.equals("BANNER_320_48") ? (byte) -1 : (byte) 1;
                break;
            case 1976753244:
                if (!inmobiSize.equals("BANNER_320_50")) {
                    b = -1;
                }
                break;
            case 2009314838:
                b = !inmobiSize.equals("BANNER_468_60") ? (byte) -1 : (byte) 5;
                break;
            case 2091508300:
                b = !inmobiSize.equals("BANNER_728_90") ? (byte) -1 : (byte) 6;
                break;
            default:
                b = -1;
                break;
        }
        switch (b) {
            case 0:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_320_50;
                break;
            case 1:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_320_48;
                break;
            case 2:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_300_250;
                break;
            case 3:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_RECTANGLE_250;
                break;
            case 4:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_120_600;
                break;
            case 5:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_468_60;
                break;
            case 6:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_728_90;
                break;
            case 7:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_1024_768;
                break;
            default:
                inmobiCreativeSize = HeyzapAds.CreativeSize.BANNER_320_50;
                break;
        }
        options.setAdmobBannerSize(admobCreativeSize);
        options.setFacebookBannerSize(facebookCreativeSize);
        options.setInmobiBannerSize(inmobiCreativeSize);
        options.setTag(tag);
        BannerAd.display(UnityPlayer.currentActivity, BANNER_POSITION_TOP.equals(position) ? 48 : 80, options);
    }

    public static void hideBanner() {
        BannerAd.hide();
    }

    public static void destroyBanner() {
        BannerAd.destroy();
    }

    public static String getBannerDimensions() {
        try {
            View currentBannerAdView = BannerAd.getCurrentBannerAdView();
            if (currentBannerAdView == null || currentBannerAdView.getParent() == null) {
                return "";
            }
            return currentBannerAdView.getLeft() + " " + currentBannerAdView.getTop() + " " + currentBannerAdView.getWidth() + " " + currentBannerAdView.getHeight();
        } catch (RuntimeException e) {
            Logger.error("Exception in getBannerDimensions", e);
            throw e;
        }
    }

    public static void showNetworkActivity() {
        HeyzapAds.startTestActivity(UnityPlayer.currentActivity);
    }

    public static void showInterstitial() {
        showInterstitial(null);
    }

    public static void showInterstitial(String tag) {
        try {
            InterstitialAd.display(UnityPlayer.currentActivity, tag);
        } catch (RuntimeException e) {
            Logger.error("Exception in showInterstitial", e);
            throw e;
        }
    }

    public static boolean isInterstitialAvailable() {
        try {
            return InterstitialAd.isAvailable().booleanValue();
        } catch (RuntimeException e) {
            Logger.error("Exception in isInterstitialAvailable", e);
            throw e;
        }
    }

    public static void fetchInterstitial() {
        try {
            InterstitialAd.fetch();
        } catch (RuntimeException e) {
            Logger.error("Exception in fetchInterstitial", e);
            throw e;
        }
    }

    public static void showIncentivized() {
        showIncentivized(null);
    }

    public static void showIncentivized(String tag) {
        try {
            IncentivizedAd.display(UnityPlayer.currentActivity, tag);
        } catch (RuntimeException e) {
            Logger.error("Exception in showIncentivized", e);
            throw e;
        }
    }

    public static boolean isIncentivizedAvailable() {
        try {
            return IncentivizedAd.isAvailable().booleanValue();
        } catch (RuntimeException e) {
            Logger.error("Exception in isIncentivizedAvailable", e);
            throw e;
        }
    }

    public static void fetchIncentivized() {
        try {
            IncentivizedAd.fetch();
        } catch (RuntimeException e) {
            Logger.error("Exception in fetchIncentivized", e);
            throw e;
        }
    }

    public static void showDebugLogs() {
        try {
            Logger.setDebugLogging(true);
            HeyzapAds.setThirdPartyVerboseLogging(true);
        } catch (RuntimeException e) {
            Logger.error("Exception in showDebugLogs", e);
            throw e;
        }
    }

    public static void hideDebugLogs() {
        try {
            Logger.setDebugLogging(false);
            HeyzapAds.setThirdPartyVerboseLogging(false);
        } catch (RuntimeException e) {
            Logger.error("Exception in hideDebugLogs", e);
            throw e;
        }
    }

    public static void addTestDevice(String testDevice) {
        try {
            HeyzapAds.addTestDevice(UnityPlayer.currentActivity, testDevice);
        } catch (RuntimeException e) {
            Logger.error("Exception in addTestDevice", e);
            throw e;
        }
    }

    public static void forceTestDevice() {
        try {
            String testDevice = Utils.md5Hex(Settings.Secure.getString(UnityPlayer.currentActivity.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID)).toUpperCase();
            HeyzapAds.addTestDevice(UnityPlayer.currentActivity, testDevice);
        } catch (RuntimeException e) {
            Logger.error("Exception in forceTestDevice", e);
            throw e;
        }
    }

    public static class UnityNetworkCallbackListener implements HeyzapAds.NetworkCallbackListener {
        private String klassName;
        private String methodName;

        public UnityNetworkCallbackListener(String klass, String method) {
            this.klassName = null;
            this.methodName = null;
            this.klassName = klass;
            this.methodName = method;
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.NetworkCallbackListener
        public void onNetworkCallback(String network, String callback) {
            String unityMessage = String.format("%s,%s", network, callback);
            Logger.format("(UNITY) Sending network callback message to %s: %s", this.klassName, unityMessage);
            UnityPlayer.UnitySendMessage(this.klassName, this.methodName, unityMessage);
        }
    }

    public static class UnityOnStatusListener implements HeyzapAds.OnStatusListener, HeyzapAds.OnIncentiveResultListener, HeyzapAds.BannerListener, HeyzapAds.StaticBannerListener {
        private String klassName;
        private String methodName;

        public UnityOnStatusListener(String klass, String method) {
            this.klassName = null;
            this.methodName = null;
            this.klassName = klass;
            this.methodName = method;
        }

        private void sendMessage(String message) {
            String unityMessage = String.format("%s", message);
            Logger.format("(UNITY) Sending message to %s: %s", this.klassName, unityMessage);
            UnityPlayer.UnitySendMessage(this.klassName, this.methodName, unityMessage);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onShow(String tag) {
            sendMessage(UnityHelper.CALLBACK_SHOW);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onClick(String tag) {
            sendMessage(UnityHelper.CALLBACK_CLICK);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onHide(String tag) {
            sendMessage(UnityHelper.CALLBACK_HIDE);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onFailedToShow(String tag) {
            sendMessage(UnityHelper.CALLBACK_FAILED);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onAvailable(String tag) {
            sendMessage(UnityHelper.CALLBACK_AVAILABLE);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onFailedToFetch(String tag) {
            sendMessage(UnityHelper.CALLBACK_FETCH_FAILED);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onAudioStarted() {
            sendMessage(UnityHelper.CALLBACK_AUDIO_STARTING);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onAudioFinished() {
            sendMessage(UnityHelper.CALLBACK_AUDIO_FINISHED);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnIncentiveResultListener
        public void onComplete(String tag) {
            sendMessage(UnityHelper.CALLBACK_INCENTIVIZED_COMPLETE);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnIncentiveResultListener
        public void onIncomplete(String tag) {
            sendMessage(UnityHelper.CALLBACK_INCENTIVIZED_INCOMPLETE);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.BannerListener
        public void onAdError(BannerAdView bannerView, HeyzapAds.BannerError error) {
            sendMessage("error");
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.BannerListener
        public void onAdLoaded(BannerAdView bannerView) {
            sendMessage(UnityHelper.CALLBACK_LOADED);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.BannerListener
        public void onAdClicked(BannerAdView bannerView) {
            sendMessage(UnityHelper.CALLBACK_CLICK);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.StaticBannerListener
        public void onAdError(HeyzapAds.BannerError error) {
            sendMessage("error");
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.StaticBannerListener
        public void onAdLoaded() {
            sendMessage(UnityHelper.CALLBACK_LOADED);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.StaticBannerListener
        public void onAdClicked() {
            sendMessage(UnityHelper.CALLBACK_CLICK);
        }
    }
}
