package com.heyzap.sdk.ads;

import android.app.Activity;
import android.content.Intent;
import com.fyber.ads.interstitials.InterstitialAdCloseReason;
import com.fyber.ads.interstitials.InterstitialAdListener;
import com.google.android.gms.drive.DriveFile;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.FutureUtils;
import com.heyzap.internal.Constants;
import com.heyzap.internal.Logger;
import com.heyzap.internal.Utils;
import com.heyzap.wrapper.CachedAd;
import com.heyzap.wrapper.InterstitialFetchWrapper;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class InterstitialAd {
    private static HeyzapAds.OnStatusListener statusListener = HeyzapAds.stubStatusListener;
    private static InterstitialFetchWrapper interstitialFetchWrapper = new InterstitialFetchWrapper();

    private InterstitialAd() {
    }

    static void start() {
        interstitialFetchWrapper.onStart();
        if (!HeyzapAds.isFlagSet(1)) {
            fetch();
        }
    }

    public static void fetch() {
        FutureUtils.wrapTimeout(interstitialFetchWrapper.ensureFetchQueued(), ExecutorPool.getInstance(), 30L, TimeUnit.SECONDS).addListener(new Runnable() { // from class: com.heyzap.sdk.ads.InterstitialAd.1
            @Override // java.lang.Runnable
            public void run() {
                if (InterstitialAd.interstitialFetchWrapper.getCachedAd() != null) {
                    InterstitialAd.statusListener.onAvailable(Constants.DEFAULT_TAG);
                } else {
                    InterstitialAd.statusListener.onFailedToFetch(Constants.DEFAULT_TAG);
                }
            }
        }, ExecutorPool.getInstance());
    }

    public static void fetch(String tag) {
        fetch();
    }

    public static Boolean isAvailable() {
        return Boolean.valueOf(interstitialFetchWrapper.getCachedAd() != null);
    }

    public static void display(Activity activity) {
        display(activity, null);
    }

    public static void setOnStatusListener(HeyzapAds.OnStatusListener listener) {
        if (listener == null) {
            listener = HeyzapAds.stubStatusListener;
        }
        statusListener = listener;
    }

    public static HeyzapAds.OnStatusListener getStatusListener() {
        return statusListener;
    }

    public static void onDisplayCompleted() {
        interstitialFetchWrapper.onDisplayCompleted();
        if (!HeyzapAds.isFlagSet(1)) {
            fetch();
        }
    }

    public static Boolean isAvailable(String tag) {
        return isAvailable();
    }

    public static void display(Activity activity, final String tag) {
        if (!Utils.isOnline(activity) || HeyzapAds.isTagDisabled(tag)) {
            statusListener.onFailedToShow(tag);
            return;
        }
        CachedAd<com.fyber.ads.interstitials.InterstitialAd> cachedAd = interstitialFetchWrapper.consumeCachedAd();
        if (cachedAd == null) {
            statusListener.onFailedToShow(tag);
            return;
        }
        cachedAd.ad.withListener(new InterstitialAdListener() { // from class: com.heyzap.sdk.ads.InterstitialAd.2
            public boolean shown = false;

            @Override // com.fyber.ads.interstitials.InterstitialAdListener
            public void onAdError(com.fyber.ads.interstitials.InterstitialAd interstitialAd, String s) {
                Logger.error(s);
                if (this.shown) {
                    InterstitialAd.getStatusListener().onHide(tag);
                    InterstitialAd.getStatusListener().onAudioFinished();
                } else {
                    InterstitialAd.getStatusListener().onFailedToShow(tag);
                }
                InterstitialAd.onDisplayCompleted();
            }

            @Override // com.fyber.ads.interstitials.InterstitialAdListener
            public void onAdClosed(com.fyber.ads.interstitials.InterstitialAd interstitialAd, InterstitialAdCloseReason interstitialAdCloseReason) {
                InterstitialAd.getStatusListener().onHide(tag);
                InterstitialAd.getStatusListener().onAudioFinished();
                InterstitialAd.onDisplayCompleted();
            }

            @Override // com.fyber.ads.interstitials.InterstitialAdListener
            public void onAdShown(com.fyber.ads.interstitials.InterstitialAd interstitialAd) {
                this.shown = true;
                InterstitialAd.getStatusListener().onShow(tag);
                InterstitialAd.getStatusListener().onAudioStarted();
            }

            @Override // com.fyber.ads.interstitials.InterstitialAdListener
            public void onAdClicked(com.fyber.ads.interstitials.InterstitialAd interstitialAd) {
                InterstitialAd.getStatusListener().onClick(tag);
            }
        });
        if (cachedAd.ad.canStart()) {
            Intent i = new Intent(activity, (Class<?>) HeyzapInterstitialActivity.class);
            i.addFlags(DriveFile.MODE_READ_ONLY);
            activity.startActivity(i);
            return;
        }
        statusListener.onFailedToShow(tag);
    }
}
