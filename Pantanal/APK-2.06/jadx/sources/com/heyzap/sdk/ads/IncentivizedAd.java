package com.heyzap.sdk.ads;

import android.app.Activity;
import android.content.Intent;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.FutureUtils;
import com.heyzap.internal.Constants;
import com.heyzap.internal.Utils;
import com.heyzap.wrapper.CachedAd;
import com.heyzap.wrapper.IncentivizedFetchWrapper;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class IncentivizedAd {
    private static HeyzapAds.OnIncentiveResultListener rewardListener = HeyzapAds.stubIncentiveListener;
    private static HeyzapAds.OnStatusListener statusListener = HeyzapAds.stubStatusListener;
    private static IncentivizedFetchWrapper incentivizedFetchWrapper = new IncentivizedFetchWrapper();

    private IncentivizedAd() {
    }

    static void start() {
        incentivizedFetchWrapper.onStart();
    }

    public static void fetch() {
        FutureUtils.wrapTimeout(incentivizedFetchWrapper.ensureFetchQueued(), ExecutorPool.getInstance(), 30L, TimeUnit.SECONDS).addListener(new Runnable() { // from class: com.heyzap.sdk.ads.IncentivizedAd.1
            @Override // java.lang.Runnable
            public void run() {
                if (IncentivizedAd.incentivizedFetchWrapper.getCachedAd() != null) {
                    IncentivizedAd.statusListener.onAvailable(Constants.DEFAULT_TAG);
                } else {
                    IncentivizedAd.statusListener.onFailedToFetch(Constants.DEFAULT_TAG);
                }
            }
        }, ExecutorPool.getInstance());
    }

    public static void fetch(String tag) {
        fetch();
    }

    public static Boolean isAvailable() {
        return Boolean.valueOf(incentivizedFetchWrapper.getCachedAd() != null);
    }

    public static void display(Activity activity) {
        display(activity, null);
    }

    static void onDisplayCompleted() {
        incentivizedFetchWrapper.onDisplayCompleted();
        if (!HeyzapAds.isFlagSet(1)) {
            fetch();
        }
    }

    public static void setOnStatusListener(HeyzapAds.OnStatusListener listener) {
        if (listener == null) {
            listener = HeyzapAds.stubStatusListener;
        }
        statusListener = listener;
    }

    public static void setOnIncentiveResultListener(HeyzapAds.OnIncentiveResultListener listener) {
        if (listener == null) {
            listener = HeyzapAds.stubIncentiveListener;
        }
        rewardListener = listener;
    }

    public static HeyzapAds.OnStatusListener getStatusListener() {
        return statusListener;
    }

    public static HeyzapAds.OnIncentiveResultListener getOnIncentiveResultListener() {
        return rewardListener;
    }

    public static Boolean isAvailable(String tag) {
        return isAvailable();
    }

    public static void display(Activity activity, String tag) {
        if (!Utils.isOnline(activity) || HeyzapAds.isTagDisabled(tag)) {
            statusListener.onFailedToShow(tag);
            return;
        }
        CachedAd<Intent> cachedAd = incentivizedFetchWrapper.consumeCachedAd();
        if (cachedAd == null) {
            statusListener.onFailedToShow(tag);
        } else {
            activity.startActivityForResult(cachedAd.ad, HeyzapAds.FYBER_INCENTIVIZED_REQUEST_CODE);
        }
    }
}
