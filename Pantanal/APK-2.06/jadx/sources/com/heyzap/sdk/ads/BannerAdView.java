package com.heyzap.sdk.ads;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.fyber.ads.banners.BannerAdListener;
import com.fyber.ads.banners.NetworkBannerSize;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.internal.Constants;
import com.heyzap.internal.Logger;
import com.heyzap.internal.RetryManager;
import com.heyzap.wrapper.FetchLock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class BannerAdView extends FrameLayout {
    private static final int DELAY_BETWEEN_BANNERS = 5000;
    private static final String XML_ATTRIBUTE_LOAD_AD_ON_CREATE = "loadAdOnCreate";
    private static final String XML_ATTRIBUTE_ON_CLICK = "onClick";
    private static final String XML_ATTRIBUTE_ON_ERROR = "onError";
    private static final String XML_ATTRIBUTE_ON_LOAD = "onLoad";
    private static final String XML_ATTRIBUTE_TAG = "tag";
    private static long lastDestruction;
    private AtomicReference<Object> activeLaunchRef;
    private Activity activity;
    private String activityOnClickedMethod;
    private String activityOnErrorMethod;
    private String activityOnLoadedMethod;
    private String adTag;
    private HeyzapAds.BannerListener bannerListener;
    private HeyzapAds.BannerOptions bannerOptions;
    private com.fyber.ads.banners.BannerAdView fyberBannerAdView;
    private Boolean loadOnAttached;

    public BannerAdView(Context activity) {
        super(activity);
        this.bannerListener = null;
        this.bannerOptions = new HeyzapAds.BannerOptions();
        this.adTag = null;
        this.loadOnAttached = false;
        this.activityOnLoadedMethod = null;
        this.activityOnErrorMethod = null;
        this.activityOnClickedMethod = null;
        this.activeLaunchRef = new AtomicReference<>();
        init(activity);
    }

    public BannerAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bannerListener = null;
        this.bannerOptions = new HeyzapAds.BannerOptions();
        this.adTag = null;
        this.loadOnAttached = false;
        this.activityOnLoadedMethod = null;
        this.activityOnErrorMethod = null;
        this.activityOnClickedMethod = null;
        this.activeLaunchRef = new AtomicReference<>();
        init(context);
        initAttrs(attrs);
    }

    public BannerAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bannerListener = null;
        this.bannerOptions = new HeyzapAds.BannerOptions();
        this.adTag = null;
        this.loadOnAttached = false;
        this.activityOnLoadedMethod = null;
        this.activityOnErrorMethod = null;
        this.activityOnClickedMethod = null;
        this.activeLaunchRef = new AtomicReference<>();
        init(context);
        initAttrs(attrs);
    }

    private void init(Context activity) {
        if (!(activity instanceof Activity)) {
            throw new RuntimeException("BannerAdView must be passed an Activity");
        }
        this.activity = (Activity) activity;
        this.adTag = Constants.DEFAULT_TAG;
    }

    private void initAttrs(AttributeSet attrs) {
        this.activityOnLoadedMethod = attrs.getAttributeValue(null, XML_ATTRIBUTE_ON_LOAD);
        this.activityOnErrorMethod = attrs.getAttributeValue(null, XML_ATTRIBUTE_ON_ERROR);
        this.activityOnClickedMethod = attrs.getAttributeValue(null, XML_ATTRIBUTE_ON_CLICK);
        String loadAdOnCreate = attrs.getAttributeValue(null, XML_ATTRIBUTE_LOAD_AD_ON_CREATE);
        if (loadAdOnCreate != null && loadAdOnCreate.toLowerCase(Locale.US).equals("true")) {
            this.adTag = attrs.getAttributeValue(null, XML_ATTRIBUTE_TAG);
            this.loadOnAttached = true;
        }
    }

    public void load(String placement) {
        if (placement != null) {
            this.adTag = placement;
        }
        load();
    }

    public void load() {
        if (HeyzapAds.isTagDisabled(this.adTag)) {
            Logger.debug("BannerAdView - tag " + this.adTag + " is disabled, aborting load");
            if (this.bannerListener != null) {
                this.bannerListener.onAdError(this, new HeyzapAds.BannerErrorImpl(Constants.FetchFailureReason.NO_FILL, "Tag disabled: " + this.adTag));
                return;
            }
            return;
        }
        final Object launchToken = new Object();
        if (!this.activeLaunchRef.compareAndSet(null, launchToken)) {
            Logger.debug("BannerAdView - Not ready to load. Perhaps BannerAdView is currently loading");
            return;
        }
        final RetryManager.Schedule lockSchedule = new RetryManager.ExponentialSchedule(2.0d, 2L, TimeUnit.SECONDS);
        final RetryManager.Schedule awaitDestroySchedule = new RetryManager.StaticSchedule(DELAY_BETWEEN_BANNERS, TimeUnit.MILLISECONDS, -1);
        HeyzapAds.adaptersReadyFuture.addListener(new Runnable() { // from class: com.heyzap.sdk.ads.BannerAdView.1
            @Override // java.lang.Runnable
            public void run() {
                if (!FetchLock.isLocked(FetchLock.LockType.BANNER)) {
                    new RetryManager(new RetryManager.UIThreadRetryableTask() { // from class: com.heyzap.sdk.ads.BannerAdView.1.1
                        @Override // com.heyzap.internal.RetryManager.UIThreadRetryableTask
                        public void runOnUiThread() {
                            if (BannerAdView.this.activeLaunchRef.get() == launchToken) {
                                if (BannerAdView.this.waitingForDestroyToComplete()) {
                                    Logger.debug("BannerAdView waiting for previous destroy to process.");
                                    retry(awaitDestroySchedule);
                                    return;
                                } else if (FetchLock.attemptLock(FetchLock.LockType.BANNER)) {
                                    BannerAdView.this.createAndLoadFyberBanner(this, launchToken);
                                    return;
                                } else {
                                    Logger.debug("BannerAdView - Banner fetch lock failed, currently held by " + FetchLock.getActiveLock() + ". Will retry later.");
                                    retry(lockSchedule);
                                    return;
                                }
                            }
                            Logger.debug("BannerAdView was destroyed. Cancelling load.");
                        }
                    }, new RetryManager.StaticSchedule(20, TimeUnit.SECONDS, 10), ExecutorPool.getInstance()).start();
                }
            }
        }, ExecutorPool.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean waitingForDestroyToComplete() {
        return System.currentTimeMillis() - lastDestruction <= 5000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void createAndLoadFyberBanner(final RetryManager.RetryableTask retryableTask, Object launchToken) {
        if (this.activeLaunchRef.get() != launchToken) {
            Logger.debug("BannerAdView was destroyed. Aborting load.");
            FetchLock.attemptUnlock(FetchLock.LockType.BANNER);
        } else {
            Logger.debug("BannerAdView - Creating and loading new banner");
            this.fyberBannerAdView = new com.fyber.ads.banners.BannerAdView(this.activity);
            if (this.adTag != null) {
                this.fyberBannerAdView.withPlacementId(this.adTag);
            }
            for (NetworkBannerSize networkBannerSize : this.bannerOptions.getNetworkBannerSizes(this.activity)) {
                this.fyberBannerAdView.withNetworkSize(networkBannerSize);
            }
            this.fyberBannerAdView.withListener(new BannerAdListener() { // from class: com.heyzap.sdk.ads.BannerAdView.2
                @Override // com.fyber.ads.banners.BannerAdListener
                public void onAdError(com.fyber.ads.banners.BannerAd bannerAd, String error) {
                    Logger.log("BannerAdView - onAdError: " + error);
                    FetchLock.attemptUnlock(FetchLock.LockType.BANNER);
                    if (!retryableTask.retry()) {
                        BannerAdView.this.dispatchOnAdError(bannerAd, error);
                    }
                }

                @Override // com.fyber.ads.banners.BannerAdListener
                public void onAdLoaded(com.fyber.ads.banners.BannerAd bannerAd) {
                    Logger.log("BannerAdView - onAdLoaded");
                    FetchLock.attemptUnlock(FetchLock.LockType.BANNER);
                    BannerAdView.this.dispatchOnAdLoaded(bannerAd);
                }

                @Override // com.fyber.ads.banners.BannerAdListener
                public void onAdClicked(com.fyber.ads.banners.BannerAd bannerAd) {
                    BannerAdView.this.dispatchOnAdClicked(bannerAd);
                }

                @Override // com.fyber.ads.banners.BannerAdListener
                public void onAdLeftApplication(com.fyber.ads.banners.BannerAd bannerAd) {
                }
            });
            removeAllViews();
            addView(this.fyberBannerAdView);
            this.fyberBannerAdView.load();
        }
    }

    public void setAdTag(String tag) {
        this.adTag = tag;
    }

    public String getAdTag() {
        return this.adTag;
    }

    public void setBannerListener(HeyzapAds.BannerListener bannerListener) {
        this.bannerListener = bannerListener;
    }

    public synchronized void destroy() {
        this.activeLaunchRef.set(null);
        com.fyber.ads.banners.BannerAdView localFyberBannerAdView = this.fyberBannerAdView;
        if (localFyberBannerAdView != null) {
            lastDestruction = System.currentTimeMillis();
            localFyberBannerAdView.destroy();
            this.fyberBannerAdView = null;
        }
    }

    protected void dispatchOnAdClicked(com.fyber.ads.banners.BannerAd bannerAd) {
        this.activity.runOnUiThread(new Runnable() { // from class: com.heyzap.sdk.ads.BannerAdView.3
            @Override // java.lang.Runnable
            public void run() {
                if (BannerAdView.this.bannerListener != null) {
                    BannerAdView.this.bannerListener.onAdClicked(BannerAdView.this);
                    if (BannerAdView.this.bannerListener == BannerAdView.this.activity) {
                        return;
                    }
                }
                if (BannerAdView.this.activityOnClickedMethod != null) {
                    try {
                        Method method = BannerAdView.this.activity.getClass().getMethod(BannerAdView.this.activityOnClickedMethod, BannerAdView.class);
                        method.invoke(BannerAdView.this.activity, BannerAdView.this);
                    } catch (IllegalAccessException e) {
                        Logger.trace((Throwable) e);
                    } catch (IllegalArgumentException e2) {
                        Logger.trace((Throwable) e2);
                    } catch (NoSuchMethodException e3) {
                        Logger.trace((Throwable) e3);
                    } catch (InvocationTargetException e4) {
                        Logger.trace((Throwable) e4);
                    }
                }
            }
        });
    }

    protected void dispatchOnAdLoaded(com.fyber.ads.banners.BannerAd bannerAd) {
        this.activity.runOnUiThread(new Runnable() { // from class: com.heyzap.sdk.ads.BannerAdView.4
            @Override // java.lang.Runnable
            public void run() {
                if (BannerAdView.this.bannerListener != null) {
                    BannerAdView.this.bannerListener.onAdLoaded(BannerAdView.this);
                    if (BannerAdView.this.bannerListener == BannerAdView.this.activity) {
                        return;
                    }
                }
                if (BannerAdView.this.activityOnLoadedMethod != null) {
                    try {
                        Method method = BannerAdView.this.activity.getClass().getMethod(BannerAdView.this.activityOnLoadedMethod, BannerAdView.class);
                        method.invoke(BannerAdView.this.activity, BannerAdView.this);
                    } catch (IllegalAccessException e) {
                        Logger.trace((Throwable) e);
                    } catch (IllegalArgumentException e2) {
                        Logger.trace((Throwable) e2);
                    } catch (NoSuchMethodException e3) {
                        Logger.trace((Throwable) e3);
                    } catch (InvocationTargetException e4) {
                        Logger.trace((Throwable) e4);
                    }
                }
            }
        });
    }

    protected void dispatchOnAdError(final com.fyber.ads.banners.BannerAd bannerAd, final String error) {
        this.activity.runOnUiThread(new Runnable() { // from class: com.heyzap.sdk.ads.BannerAdView.5
            @Override // java.lang.Runnable
            public void run() {
                if (BannerAdView.this.bannerListener != null) {
                    BannerAdView.this.bannerListener.onAdError(BannerAdView.this, new HeyzapAds.BannerErrorImpl(Constants.FetchFailureReason.UNKNOWN, error));
                    if (BannerAdView.this.bannerListener == BannerAdView.this.activity) {
                        return;
                    }
                }
                if (BannerAdView.this.activityOnErrorMethod != null) {
                    try {
                        Method method = BannerAdView.this.activity.getClass().getMethod(BannerAdView.this.activityOnErrorMethod, BannerAdView.class, HeyzapAds.BannerError.class);
                        method.invoke(BannerAdView.this.activity, bannerAd, error);
                    } catch (IllegalAccessException e) {
                        Logger.trace((Throwable) e);
                    } catch (IllegalArgumentException e2) {
                        Logger.trace((Throwable) e2);
                    } catch (NoSuchMethodException e3) {
                        Logger.trace((Throwable) e3);
                    } catch (InvocationTargetException e4) {
                        Logger.trace((Throwable) e4);
                    }
                }
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.loadOnAttached.booleanValue()) {
            load();
        }
    }

    protected void setLoadOnAttachedToWindow(Boolean loadOnAttachedToWindow) {
        this.loadOnAttached = loadOnAttachedToWindow;
    }

    public HeyzapAds.BannerOptions getBannerOptions() {
        return this.bannerOptions;
    }

    protected void setBannerOptions(HeyzapAds.BannerOptions options) {
        this.bannerOptions = options;
    }
}
