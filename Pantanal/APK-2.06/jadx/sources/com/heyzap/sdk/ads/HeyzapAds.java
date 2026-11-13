package com.heyzap.sdk.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.fyber.Fyber;
import com.fyber.ads.banners.BannerSize;
import com.fyber.ads.banners.NetworkBannerSize;
import com.fyber.mediation.MediationAdapterStarter;
import com.fyber.mediation.MediationConfigProvider;
import com.fyber.mediation.adcolony.AdColonyMediationAdapter;
import com.heyzap.cache.FileCache;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.FutureHandler;
import com.heyzap.common.concurrency.FutureUtils;
import com.heyzap.common.concurrency.SettableFuture;
import com.heyzap.internal.Analytics;
import com.heyzap.internal.Constants;
import com.heyzap.internal.DevLogger;
import com.heyzap.internal.DeveloperErrorMessages;
import com.heyzap.internal.Logger;
import com.heyzap.internal.Utils;
import com.heyzap.wrapper.credentials.Credentials;
import com.heyzap.wrapper.credentials.CredentialsLocator;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class HeyzapAds {
    public static final int AMAZON = 4;
    public static final int CHILD_DIRECTED_ADVERTISING = 64;
    public static final int DISABLE_AUTOMATIC_FETCH = 1;
    public static final int DISABLE_MEDIATION = 8;
    public static final int FYBER_INCENTIVIZED_REQUEST_CODE = 8214;
    public static final int FYBER_INTERSTITIAL_REQUEST_CODE = 8213;
    public static final int INSTALL_TRACKING_ONLY = 2;
    public static final int NATIVE_ADS_ONLY = 32;
    public static final int NONE = 0;
    public static Context applicationContext = null;
    public static Handler handler = null;
    public static final int minimumSdkVersion = 9;
    public static String framework = null;
    public static String mediator = null;
    public static final AdsConfig config = new AdsConfig();
    private static boolean intentionallyDisabled = false;
    private static boolean thirdPartyVerboseLogging = false;
    public static SettableFuture<Void> adaptersReadyFuture = SettableFuture.create();
    private static Set<String> disabledTags = new HashSet();
    static AtomicReference<SettableFuture> initializationFuture = new AtomicReference<>();
    public static final OnStatusListener stubStatusListener = new OnStatusListener() { // from class: com.heyzap.sdk.ads.HeyzapAds.3
        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onShow(String tag) {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onClick(String tag) {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onHide(String tag) {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onFailedToShow(String tag) {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onAvailable(String tag) {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onFailedToFetch(String tag) {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onAudioStarted() {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
        public void onAudioFinished() {
        }
    };
    public static final OnIncentiveResultListener stubIncentiveListener = new OnIncentiveResultListener() { // from class: com.heyzap.sdk.ads.HeyzapAds.4
        @Override // com.heyzap.sdk.ads.HeyzapAds.OnIncentiveResultListener
        public void onComplete(String tag) {
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.OnIncentiveResultListener
        public void onIncomplete(String tag) {
        }
    };

    public static class AdsConfig {
        public int flags;
        public String publisherId;
        public Long startTime;
        public String store = "google";
    }

    public interface BannerError {
        Constants.FetchFailureReason getErrorCode();

        String getErrorMessage();
    }

    public interface BannerListener {
        void onAdClicked(BannerAdView bannerAdView);

        void onAdError(BannerAdView bannerAdView, BannerError bannerError);

        void onAdLoaded(BannerAdView bannerAdView);
    }

    public interface NativeError {
        Constants.FetchFailureReason getErrorCode();

        String getErrorMessage();
    }

    public static final class Network {
        public static final String ADCOLONY = "adcolony";
        public static final String ADMOB = "admob";
        public static final String APPLOVIN = "applovin";
        public static final String CHARTBOOST = "chartboost";
        public static final String FACEBOOK = "facebook";
        public static final String HEYZAP = "heyzap";
        public static final String HYPRMX = "hyprmx";
        public static final String IAD = "iad";
        public static final String UNITYADS = "unityads";
        public static final String VUNGLE = "vungle";
    }

    public interface NetworkCallbackListener {
        void onNetworkCallback(String str, String str2);
    }

    public interface OnIncentiveResultListener {
        void onComplete(String str);

        void onIncomplete(String str);
    }

    public interface OnStatusListener {
        void onAudioFinished();

        void onAudioStarted();

        void onAvailable(String str);

        void onClick(String str);

        void onFailedToFetch(String str);

        void onFailedToShow(String str);

        void onHide(String str);

        void onShow(String str);
    }

    public interface StaticBannerListener {
        void onAdClicked();

        void onAdError(BannerError bannerError);

        void onAdLoaded();
    }

    public static void addTestDevice(Context context, String addDeviceHash) {
        String admobDeviceHash = Utils.md5Hex(Settings.Secure.getString(context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID)).toUpperCase();
        String facebookDeviceHash = context.getSharedPreferences("FBAdPrefs", 0).getString("deviceIdHash", null);
        Log.d(Analytics.LOG_TAG, "addTestDevice - provided: " + addDeviceHash + " real: " + admobDeviceHash);
        if (admobDeviceHash.equals(addDeviceHash) || (facebookDeviceHash != null && facebookDeviceHash.equals(addDeviceHash))) {
            Log.d(Analytics.LOG_TAG, "addTestDevice - setting device as test, adding admob device " + addDeviceHash + " and facebook device " + facebookDeviceHash);
            JSONArray deviceArray = new JSONArray();
            deviceArray.put(admobDeviceHash);
            MediationConfigProvider.addRuntimeConfigs(Network.ADMOB, "addTestDevice", deviceArray);
            if (facebookDeviceHash != null) {
                MediationConfigProvider.addRuntimeConfigs("facebookaudiencenetwork", "testDeviceHash", facebookDeviceHash);
            }
        }
    }

    public static void start(String publisherId, Activity activity, int flags, OnStatusListener listener) {
        internalStart(publisherId, activity, flags, listener);
    }

    public static void start(String publisherId, Activity activity, int flags) {
        start(publisherId, activity, flags, null);
    }

    public static void start(String publisherId, Activity activity) {
        start(publisherId, activity, 0, null);
    }

    private static void internalStart(String publisherId, final Activity activity, int flags, OnStatusListener listener) {
        if (initializationFuture.compareAndSet(null, SettableFuture.create())) {
            DevLogger.debug("HeyzapAds starting");
            try {
                config.flags = flags;
                if ((flags & 4) == 4 || Utils.isAmazon()) {
                    config.store = "amazon";
                    MediationConfigProvider.addRuntimeConfigs(Network.ADCOLONY, AdColonyMediationAdapter.CLIENT_OPTIONS, "version:1.1,store:amazon");
                    DevLogger.info("App is running in Amazon AppStore mode. Amazon version of app will provide credentials and configuration.");
                }
                applicationContext = activity.getApplication();
                MediationAdapterStarter.init();
                new CredentialsLocator(activity.getApplication()).locate().addHandler(new FutureHandler<Credentials, Object>() { // from class: com.heyzap.sdk.ads.HeyzapAds.1
                    @Override // com.heyzap.common.concurrency.FutureHandler
                    public Object handle(Credentials credentials, Exception exception) {
                        Fyber.with(credentials.appId, activity).withSecurityToken(credentials.securityToken).start().notifyUserOnCompletion(false).notifyUserOnReward(false);
                        Set unused = HeyzapAds.disabledTags = credentials.disabledTags;
                        FutureUtils.bind(FutureUtils.addDelay(MediationAdapterStarter.adaptersInitializedFuture, ExecutorPool.getInstance(), 6L, TimeUnit.SECONDS), HeyzapAds.adaptersReadyFuture, ExecutorPool.getInstance());
                        return null;
                    }
                }, ExecutorPool.getInstance());
                Logger.init(activity.getApplicationContext());
                FileCache.cleanupOldCache(applicationContext);
                InterstitialAd.start();
                IncentivizedAd.start();
                initializationFuture.get().set(true);
            } catch (RuntimeException e) {
                DevLogger.error(String.format(DeveloperErrorMessages.HEYZAP_RUNTIME_EXCEPTION, e.getMessage()));
                intentionallyDisabled = true;
                initializationFuture.set(null);
                throw e;
            }
        }
    }

    public static void startTestActivity(final Activity activity) {
        if (!hasStarted()) {
            DevLogger.error(DeveloperErrorMessages.HEYZAP_NOT_STARTED);
            activity.runOnUiThread(new Runnable() { // from class: com.heyzap.sdk.ads.HeyzapAds.2
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(activity, "You must call HeyzapAds.start() first", 1).show();
                }
            });
        } else if (!Utils.activityExistsInPackage(activity, MediationTestActivity.class)) {
            DevLogger.error(DeveloperErrorMessages.TEST_ACTIVITY_NOT_PRESENT);
        } else {
            activity.startActivity(new Intent(activity, (Class<?>) MediationTestActivity.class));
        }
    }

    public static void setNetworkCallbackListener(NetworkCallbackListener listener) {
    }

    public static boolean isNetworkInitialized(String network) {
        return false;
    }

    public static boolean hasStarted() {
        SettableFuture future;
        return (intentionallyDisabled || (future = initializationFuture.get()) == null || !future.isDone()) ? false : true;
    }

    public static boolean assertStarted() {
        if (!hasStarted()) {
            if (intentionallyDisabled) {
                return false;
            }
            DevLogger.warn(DeveloperErrorMessages.HEYZAP_NOT_STARTED);
            return false;
        }
        return true;
    }

    public static void changeActivity(Activity activity) {
    }

    public static void setFlags(int flags) {
    }

    public static void setBundleId(String bundleId) {
        if (initializationFuture.get() != null && !bundleId.equals(Utils.packageName) && !"unknown".equals(Utils.packageName)) {
            DevLogger.error("HeyzapAds.setBundleId() cannot be called after HeyzapAds.start()");
            throw new RuntimeException("HeyzapAds.setBundleId() cannot be called after HeyzapAds.start()");
        }
        Utils.packageName = bundleId;
    }

    public static String getVersion() {
        return "10.2.0";
    }

    public static void slowClose(Boolean slowClose) {
    }

    public static void setThirdPartyVerboseLogging(boolean in) {
        thirdPartyVerboseLogging = in;
    }

    public static boolean isThirdPartyVerboseLogging() {
        return thirdPartyVerboseLogging;
    }

    public static JSONObject getRemoteData() {
        return new JSONObject();
    }

    public static boolean onBackPressed() {
        return false;
    }

    public static void setLocation(Location location) {
    }

    public static void onPurchaseComplete(String name, String id, int usdPriceCents) {
        if (!assertStarted()) {
        }
    }

    @Deprecated
    public static void setOnStatusListener(OnStatusListener listener) {
    }

    @Deprecated
    public static void setOnIncentiveResultListener(OnIncentiveResultListener listener) {
    }

    public static void changeServer(String url) {
    }

    public static int getFlags() {
        return config.flags;
    }

    public static boolean isFlagSet(int flag) {
        return (config.flags & flag) > 0;
    }

    public static boolean isTagDisabled(String tag) {
        return (tag == null || tag.trim().isEmpty()) ? disabledTags.contains(Constants.DEFAULT_TAG) : disabledTags.contains(tag);
    }

    public static void shutdown() {
        intentionallyDisabled = true;
    }

    private HeyzapAds() {
    }

    public static class BannerErrorImpl implements BannerError {
        Constants.FetchFailureReason errorCode;
        String errorMessage;

        public BannerErrorImpl(Constants.FetchFailureReason code, String message) {
            this.errorCode = code;
            this.errorMessage = message;
            Logger.error(this.errorMessage);
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.BannerError
        public String getErrorMessage() {
            return this.errorMessage;
        }

        @Override // com.heyzap.sdk.ads.HeyzapAds.BannerError
        public Constants.FetchFailureReason getErrorCode() {
            return this.errorCode;
        }
    }

    public static class BannerOptions {
        private String tag;
        private CreativeSize facebookBannerSize = CreativeSize.BANNER_320_50;
        private CreativeSize admobBannerSize = CreativeSize.SMART_BANNER;
        private CreativeSize inmobiBannerSize = CreativeSize.BANNER_320_50;
        private CreativeSize genericBannerSize = CreativeSize.SMART_BANNER;
        private CreativeSize containerViewSize = null;
        private int position = 80;

        public List<NetworkBannerSize> getNetworkBannerSizes(Activity activity) {
            List<NetworkBannerSize> networkBannerSizes = new ArrayList<>();
            if (getAdmobBannerSize() != null) {
                networkBannerSizes.add(new NetworkBannerSize("AdMob", getAdmobBannerSize().toFyberSize()));
            }
            if (getInmobiBannerSize() != null) {
                networkBannerSizes.add(new NetworkBannerSize("Inmobi", getInmobiBannerSize().toFyberSize()));
            }
            CreativeSize facebookSize = getFacebookBannerSize();
            if (facebookSize != null) {
                if (facebookSize == CreativeSize.SMART_BANNER) {
                    if (Utils.isTablet(activity)) {
                        networkBannerSizes.add(new NetworkBannerSize("FacebookAudienceNetwork", BannerSize.Builder.newBuilder().withHeight(90).build()));
                    } else {
                        networkBannerSizes.add(new NetworkBannerSize("FacebookAudienceNetwork", BannerSize.Builder.newBuilder().withHeight(50).build()));
                    }
                } else {
                    networkBannerSizes.add(new NetworkBannerSize("FacebookAudienceNetwork", facebookSize.toFyberSize()));
                }
            }
            return networkBannerSizes;
        }

        public CreativeSize getFacebookBannerSize() {
            return this.facebookBannerSize;
        }

        public CreativeSize getAdmobBannerSize() {
            return this.admobBannerSize;
        }

        public CreativeSize getInmobiBannerSize() {
            return this.inmobiBannerSize;
        }

        public CreativeSize getGenericBannerSize() {
            return this.genericBannerSize;
        }

        public BannerOptions setFacebookBannerSize(CreativeSize facebookBannerSize) {
            this.facebookBannerSize = facebookBannerSize;
            return this;
        }

        public BannerOptions setAdmobBannerSize(CreativeSize admobBannerSize) {
            this.admobBannerSize = admobBannerSize;
            return this;
        }

        public BannerOptions setInmobiBannerSize(CreativeSize inmobiBannerSize) {
            this.inmobiBannerSize = inmobiBannerSize;
            return this;
        }

        public BannerOptions setGenericBannerSize(CreativeSize genericBannerSize) {
            this.genericBannerSize = genericBannerSize;
            return this;
        }

        protected BannerOptions setContainerViewSize(CreativeSize containerViewSize) {
            this.containerViewSize = containerViewSize;
            return this;
        }

        public CreativeSize getContainerViewSize() {
            return this.containerViewSize;
        }

        protected void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return this.position;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return (this.tag == null || this.tag.trim().isEmpty()) ? Constants.DEFAULT_TAG : this.tag;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BannerOptions that = (BannerOptions) o;
            if (this.admobBannerSize == null ? that.admobBannerSize != null : !this.admobBannerSize.equals(that.admobBannerSize)) {
                return false;
            }
            if (this.facebookBannerSize == null ? that.facebookBannerSize != null : !this.facebookBannerSize.equals(that.facebookBannerSize)) {
                return false;
            }
            if (this.genericBannerSize == null ? that.genericBannerSize != null : !this.genericBannerSize.equals(that.genericBannerSize)) {
                return false;
            }
            return this.position == that.position;
        }

        public int hashCode() {
            int result = this.facebookBannerSize != null ? this.facebookBannerSize.hashCode() : 0;
            return (result * 31) + (this.admobBannerSize != null ? this.admobBannerSize.hashCode() : 0);
        }
    }

    @Deprecated
    public static class BannerAdSize extends CreativeSize {
        public BannerAdSize(int width, int height) {
            super(width, height);
        }
    }

    public static class CreativeSize {
        public static final int AUTO_HEIGHT = -2;
        public static final int FULL_WIDTH = -1;
        private int height;
        private int width;
        public static final CreativeSize BANNER = new CreativeSize(320, 50);
        public static final CreativeSize FULL_BANNER = new CreativeSize(468, 60);
        public static final CreativeSize LARGE_BANNER = new CreativeSize(320, 100);
        public static final CreativeSize MEDIUM_RECTANGLE = new CreativeSize(300, 250);
        public static final CreativeSize LEADERBOARD = new CreativeSize(728, 90);
        public static final CreativeSize SMART_BANNER = new CreativeSize(-1, -2);
        public static final CreativeSize WIDE_SKYSCRAPER = new CreativeSize(160, 600);
        public static final CreativeSize BANNER_320_50 = new CreativeSize(320, 50);
        public static final CreativeSize BANNER_HEIGHT_50 = new CreativeSize(320, 50);
        public static final CreativeSize BANNER_HEIGHT_90 = new CreativeSize(320, 90);
        public static final CreativeSize BANNER_RECTANGLE_250 = new CreativeSize(300, 250);
        public static final CreativeSize BANNER_320_48 = new CreativeSize(320, 48);
        public static final CreativeSize BANNER_300_250 = new CreativeSize(300, 250);
        public static final CreativeSize BANNER_120_600 = new CreativeSize(120, 600);
        public static final CreativeSize BANNER_468_60 = new CreativeSize(468, 60);
        public static final CreativeSize BANNER_728_90 = new CreativeSize(728, 90);
        public static final CreativeSize BANNER_1024_768 = new CreativeSize(1024, 768);
        public static final CreativeSize FULLSCREEN = new CreativeSize(-1, -1);

        public CreativeSize(int width, int height) {
            this.width = 320;
            this.height = 50;
            this.width = width;
            this.height = height;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof CreativeSize)) {
                return false;
            }
            CreativeSize otherAdSize = (CreativeSize) other;
            return this.width == otherAdSize.width && this.height == otherAdSize.height;
        }

        public String toString() {
            return String.format("<CreativeSize %dx%d>", Integer.valueOf(this.width), Integer.valueOf(this.height));
        }

        public BannerSize toFyberSize() {
            return (this.height == -2 && this.width == -1) ? BannerSize.SMART_SIZE : BannerSize.Builder.newBuilder().withHeight(this.height).withWidth(this.width).build();
        }
    }
}
