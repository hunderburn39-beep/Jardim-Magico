package com.adcolony.sdk;

import android.app.Activity;
import android.os.Build;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class AdColonyPubServices {
    private static boolean a = false;

    public enum LogLevel {
        LOG_LEVEL_ERROR,
        LOG_LEVEL_WARN,
        LOG_LEVEL_INFO,
        LOG_LEVEL_DEBUG,
        LOG_LEVEL_VERBOSE
    }

    public enum NotificationType {
        NONE,
        TOAST,
        MODAL
    }

    public enum ServiceAvailability {
        SERVICE_UNKNOWN,
        SERVICE_UNAVAILABLE,
        SERVICE_CONNECTING,
        SERVICE_AVAILABLE,
        SERVICE_INVISIBLE,
        SERVICE_MAINTENANCE,
        SERVICE_DISABLED,
        SERVICE_BANNED
    }

    private static String a() {
        return "AdColonyPubServices";
    }

    public static void configure(Activity activity, Map<String, Object> initParams) {
        if (Build.VERSION.SDK_INT < bn.a) {
            bv.aN().a(bn.a.YVOLVER_ERROR_INSUFFICIENT_OS_VERSION, "Adcolony requires a higher OS Version in config", true, ServiceAvailability.SERVICE_UNAVAILABLE);
            return;
        }
        if (activity == null) {
            bv.aN().a(bn.a.YVOLVER_ERROR_CLIENT_EXCEPTION, "AdColony requires a valid activity in config. It cannot be null.", false, ServiceAvailability.SERVICE_UNAVAILABLE);
            return;
        }
        String appID = AdColony.getAppOptions().getAppID();
        if (appID == null) {
            appID = "";
        }
        if (initParams == null) {
            initParams = new HashMap<>();
            initParams.put("yv_apikey", appID);
        } else if (!initParams.keySet().contains("yv_apikey")) {
            initParams.put("yv_apikey", appID);
        }
        try {
            bv.aN().aq();
            bv.aN().b(activity);
            bv.aN().b(initParams);
            a = true;
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.configure");
        }
    }

    public static void addListener(AdColonyPubServicesCallbacks delegate) {
        try {
            bv.aN().a(delegate);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.addListener");
        }
    }

    public static void removeListener(AdColonyPubServicesCallbacks delegate) {
        try {
            bv.aN().b(delegate);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.removeListener");
        }
    }

    public static void onAdOpened() {
        if (a) {
            bv.aN().n();
        }
    }

    public static void onAdClosed() {
        if (a) {
            bv.aN().o();
        }
    }

    public static void grantRewardFromInAppPurchaseGoogle(String receipt, String signature, String locale, long priceMicro, int inGameCurrencyQuantityForProduct) {
        try {
            bv.aN().a(receipt, signature, locale, priceMicro, inGameCurrencyQuantityForProduct);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.grantRewardFromInAppPurchaseGoogle");
        }
    }

    public static void grantRewardFromInAppPurchaseAmazon(String sku, String purchaseToken, String userId, String price, int inGameCurrencyQuantityForProduct) {
        try {
            bv.aN().a(sku, purchaseToken, userId, price, inGameCurrencyQuantityForProduct);
        } catch (Exception e) {
            bv.aN().a(e, "grantRewardFromInAppPurchaseAmazon()");
        }
    }

    public static void setNotificationsAllowed(EnumSet<NotificationType> notificationMask) {
        try {
            bv.aN().a(notificationMask);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.setNotificationsAllowed");
        }
    }

    public static void showOverlay() {
        try {
            cl.b().a("", false, false);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.showOverlay");
        }
    }

    public static void closeOverlay() {
        try {
            cl.b().d();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.closeOverlay");
        }
    }

    public static boolean isOverlayVisible() {
        try {
            return cl.b().e();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.isOverlayVisible");
            return false;
        }
    }

    public static boolean isServiceAvailable() {
        try {
            return bv.aN().w();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.isServiceAvailable");
            return false;
        }
    }

    public static Map<String, Object> getExperiments() {
        try {
            return bv.aN().aa();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.getExperiments");
            return null;
        }
    }

    public static Object getExperimentValue(String key) {
        try {
            return bv.aN().r(key);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.getExperimentValue");
            return null;
        }
    }

    public static long getStatValue(String name) throws Exception {
        try {
            return bv.aN().o(name);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.getStatValue");
            return 0L;
        }
    }

    public static ArrayList<HashMap<String, Object>> getStats() {
        try {
            return bv.aN().L();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.getStats");
            return null;
        }
    }

    public static boolean setStat(String name, long value) {
        try {
            return bv.aN().a(name, value);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.setStat");
            return false;
        }
    }

    public static boolean incrementStat(String name, long value) {
        try {
            return bv.aN().b(name, value);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.incrementStat");
            return false;
        }
    }

    public static void refreshStats() {
        try {
            bv.aN().C();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.refreshStats");
        }
    }

    public static void markStartRound() {
        try {
            bv.aN().M();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.markStartRound");
        }
    }

    public static void markEndRound() {
        try {
            bv.aN().N();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.markEndRound");
        }
    }

    public static AdColonyPubServicesVIPInformation getVIPInformation() {
        try {
            return bv.aN().Z();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.getVIPInformation");
            return null;
        }
    }

    public static void registerForPushNotifications(String defaultSenderId) {
        try {
            bv.aN().s(defaultSenderId);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.registerForPushNotifications");
        }
    }

    public static void unregisterForPushNotifications() {
        try {
            bv.aN().ah();
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.unregisterForPushNotifications");
        }
    }

    static void a(String str) {
        try {
            bv.aN().i(str);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.setDeviceToken");
        }
    }

    static void b(String str) {
        try {
            bv.aN().j(str);
        } catch (Exception e) {
            bv.aN().a(e, "AdColonyPubServices.setAdvertisingId");
        }
    }
}
