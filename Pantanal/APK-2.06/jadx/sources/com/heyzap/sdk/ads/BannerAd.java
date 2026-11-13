package com.heyzap.sdk.ads;

import android.app.Activity;
import android.view.View;
import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.ads.banners.BannerAdListener;
import com.fyber.ads.banners.NetworkBannerSize;
import com.fyber.ads.banners.mediation.BannerWrapper;
import com.fyber.requesters.AdRequestCallback;
import com.fyber.requesters.BannerRequester;
import com.fyber.requesters.RequestError;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.internal.Constants;
import com.heyzap.internal.Logger;
import com.heyzap.internal.RetryManager;
import com.heyzap.wrapper.FetchLock;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class BannerAd {
    private static AtomicReference<BannerAd> instance = new AtomicReference<>();
    private static HeyzapAds.StaticBannerListener instanceListener = null;
    private final Activity activity;
    private HeyzapAds.BannerOptions bannerOptions;
    private int bannerPosition;
    private com.fyber.ads.banners.BannerAd latestBannerAd;
    private BannerCommand latestCommand;
    private final AtomicReference<Boolean> isShowing = new AtomicReference<>(false);
    private boolean started = false;
    private boolean failed = false;

    private interface BannerCommand {
        void run(com.fyber.ads.banners.BannerAd bannerAd);
    }

    private BannerAd(Activity activity, HeyzapAds.BannerOptions options) {
        this.bannerPosition = options.getPosition();
        this.bannerOptions = options;
        this.activity = activity;
        HeyzapAds.adaptersReadyFuture.addListener(new Runnable() { // from class: com.heyzap.sdk.ads.BannerAd.1
            @Override // java.lang.Runnable
            public void run() {
                BannerAd.this.executeRequest();
            }
        }, ExecutorPool.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeRequest() {
        final RetryManager.Schedule lockRetrySchedule = new RetryManager.ExponentialSchedule(2.0d, 2L, TimeUnit.SECONDS);
        RetryManager.Schedule failRetrySchedule = new RetryManager.StaticSchedule(20, TimeUnit.SECONDS, 10);
        new RetryManager(new RetryManager.RetryableTask() { // from class: com.heyzap.sdk.ads.BannerAd.2
            @Override // java.lang.Runnable
            public void run() {
                if (FetchLock.attemptLock(FetchLock.LockType.BANNER)) {
                    Logger.debug("BannerAd - interstitial not fetching, executing");
                    BannerRequester requester = BannerRequester.create(new AdRequestCallback() { // from class: com.heyzap.sdk.ads.BannerAd.2.1
                        @Override // com.fyber.requesters.AdRequestCallback
                        public void onAdAvailable(Ad ad) {
                            Logger.debug("BannerAd - onAdAvailable");
                            FetchLock.attemptUnlock(FetchLock.LockType.BANNER);
                            com.fyber.ads.banners.BannerAd bannerAd = (com.fyber.ads.banners.BannerAd) ad;
                            BannerAd.this.processOrQueueCommand(bannerAd, null);
                        }

                        @Override // com.fyber.requesters.AdRequestCallback
                        public void onAdNotAvailable(AdFormat adFormat) {
                            FetchLock.attemptUnlock(FetchLock.LockType.BANNER);
                            Logger.debug("BannerAd - onAdNotAvailable");
                            if (!retry()) {
                                BannerAd.this.failed = true;
                                if (BannerAd.instanceListener != null) {
                                    BannerAd.instanceListener.onAdError(new HeyzapAds.BannerErrorImpl(Constants.FetchFailureReason.NO_FILL, "not available"));
                                }
                            }
                        }

                        @Override // com.fyber.requesters.Callback
                        public void onRequestError(RequestError requestError) {
                            Logger.debug("BannerAd - onRequestError " + requestError.getDescription());
                            FetchLock.attemptUnlock(FetchLock.LockType.BANNER);
                            if (!retry()) {
                                BannerAd.this.failed = true;
                                if (BannerAd.instanceListener != null) {
                                    BannerAd.instanceListener.onAdError(new HeyzapAds.BannerErrorImpl(Constants.FetchFailureReason.UNKNOWN, String.valueOf(requestError)));
                                }
                            }
                        }
                    });
                    for (NetworkBannerSize networkBannerSize : BannerAd.this.bannerOptions.getNetworkBannerSizes(BannerAd.this.activity)) {
                        requester = requester.withNetworkSize(networkBannerSize);
                    }
                    requester.request(BannerAd.this.activity);
                    return;
                }
                Logger.debug("BannerAd - currently fetching a " + FetchLock.getActiveLock() + ", will retry fetch later");
                retry(lockRetrySchedule);
            }
        }, failRetrySchedule, ExecutorPool.getInstance()).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void processOrQueueCommand(com.fyber.ads.banners.BannerAd bannerAd, BannerCommand command) {
        try {
            if (bannerAd == null) {
                bannerAd = this.latestBannerAd;
            } else {
                this.latestBannerAd = bannerAd;
            }
            if (command == null) {
                command = this.latestCommand;
            } else {
                this.latestCommand = command;
            }
            if (bannerAd != null && this.latestCommand != null) {
                this.latestCommand = null;
                command.run(bannerAd);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void internalHide(final boolean destroy) {
        processOrQueueCommand(null, new BannerCommand() { // from class: com.heyzap.sdk.ads.BannerAd.3
            @Override // com.heyzap.sdk.ads.BannerAd.BannerCommand
            public void run(com.fyber.ads.banners.BannerAd bannerAd) {
                if (destroy) {
                    bannerAd.destroy();
                } else {
                    bannerAd.hide();
                }
                Logger.debug("BannerAd - internalHide - hidden, destroyed: " + destroy);
            }
        });
    }

    public void internalShow(final Activity activity, final int pos) {
        processOrQueueCommand(null, new BannerCommand() { // from class: com.heyzap.sdk.ads.BannerAd.4
            @Override // com.heyzap.sdk.ads.BannerAd.BannerCommand
            public void run(com.fyber.ads.banners.BannerAd bannerAd) {
                if (pos == 48) {
                    bannerAd.displayAtTop();
                } else {
                    bannerAd.displayAtBottom();
                }
                if (!BannerAd.this.started) {
                    bannerAd.withListener(new BannerAdListener() { // from class: com.heyzap.sdk.ads.BannerAd.4.1
                        @Override // com.fyber.ads.banners.BannerAdListener
                        public void onAdError(com.fyber.ads.banners.BannerAd bannerAd2, String s) {
                            if (BannerAd.instanceListener != null) {
                                BannerAd.instanceListener.onAdError(new HeyzapAds.BannerErrorImpl(Constants.FetchFailureReason.UNKNOWN, s));
                            }
                            BannerAd.this.failed = true;
                        }

                        @Override // com.fyber.ads.banners.BannerAdListener
                        public void onAdLoaded(com.fyber.ads.banners.BannerAd bannerAd2) {
                            if (BannerAd.instanceListener != null) {
                                BannerAd.instanceListener.onAdLoaded();
                            }
                        }

                        @Override // com.fyber.ads.banners.BannerAdListener
                        public void onAdClicked(com.fyber.ads.banners.BannerAd bannerAd2) {
                            if (BannerAd.instanceListener != null) {
                                BannerAd.instanceListener.onAdClicked();
                            }
                        }

                        @Override // com.fyber.ads.banners.BannerAdListener
                        public void onAdLeftApplication(com.fyber.ads.banners.BannerAd bannerAd2) {
                        }
                    });
                    bannerAd.start(activity);
                    BannerAd.this.started = true;
                }
                bannerAd.show();
                Logger.debug("BannerAd - internalShow - displayedAd");
            }
        });
    }

    public static View getCurrentBannerAdView() {
        BannerAd bannerAd = instance.get();
        if (bannerAd != null && bannerAd.latestBannerAd != null) {
            try {
                Field field = bannerAd.latestBannerAd.getClass().getDeclaredField("d");
                field.setAccessible(true);
                BannerWrapper wrapper = (BannerWrapper) field.get(bannerAd.latestBannerAd);
                if (wrapper != null) {
                    return wrapper.getView();
                }
            } catch (Exception e) {
                Logger.warn("Could not get banner view: " + e.getMessage());
            }
        }
        return null;
    }

    public static synchronized void display(Activity activity, int position) {
        display(activity, position, new HeyzapAds.BannerOptions());
    }

    public static synchronized void display(Activity activity, int position, String tag) {
        HeyzapAds.BannerOptions options = new HeyzapAds.BannerOptions();
        options.setTag(tag);
        display(activity, position, options);
    }

    public static synchronized void display(Activity activity, int position, HeyzapAds.BannerOptions options) {
        if (HeyzapAds.isTagDisabled(options.getTag()) && instanceListener != null) {
            instanceListener.onAdError(new HeyzapAds.BannerErrorImpl(Constants.FetchFailureReason.NO_FILL, "Tag disabled: " + options.getTag()));
        } else {
            options.setPosition(position);
            BannerAd banner = instance.get();
            if (banner == null || !banner.bannerOptions.equals(options) || banner.failed) {
                if (banner != null) {
                    banner.internalHide(true);
                }
                Logger.log("Creating new banner ad");
                banner = new BannerAd(activity, options);
                instance.set(banner);
            }
            banner.internalShow(activity, position);
        }
    }

    public static synchronized void hide() {
        BannerAd bannerAd = instance.get();
        if (bannerAd != null) {
            bannerAd.internalHide(false);
        }
    }

    public static synchronized void destroy() {
        BannerAd bannerAd = instance.get();
        if (bannerAd != null) {
            bannerAd.internalHide(true);
        }
        instance.set(null);
    }

    public static void setBannerListener(HeyzapAds.StaticBannerListener bannerListener) {
        instanceListener = bannerListener;
    }
}
