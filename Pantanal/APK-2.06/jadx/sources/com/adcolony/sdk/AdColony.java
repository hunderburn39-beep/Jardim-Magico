package com.adcolony.sdk;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColony {
    static ExecutorService a = Executors.newSingleThreadExecutor();

    public static boolean configure(@NonNull Activity activity, @NonNull String app_id, @NonNull String... zone_ids) {
        return configure(activity, null, app_id, zone_ids);
    }

    public static boolean configure(@NonNull Activity activity, AdColonyAppOptions options, @NonNull String app_id, @NonNull String... zone_ids) {
        if (af.a(0, null)) {
            u.e.b("Cannot configure AdColony; configuration mechanism requires 5 seconds between attempts.");
            return false;
        }
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        if (options == null) {
            options = new AdColonyAppOptions();
        }
        if (a.m != null && a.m.v != null && a.m.v.d != null && !s.d(a.m.v.d, "reconfigurable")) {
            if (a.m.v.a != null && !a.m.v.a.equals(app_id)) {
                u.e.b("Ignoring call to AdColony.configure, as the app id does not match what was used during the initial configuration.");
                return false;
            }
            if (a.m.v.b != null && a(zone_ids, a.m.v.b)) {
                u.e.b("Ignoring call to AdColony.configure, as the same zone ids were used during the previous configuration.");
                return false;
            }
        }
        options.a(app_id);
        options.a(zone_ids);
        a(activity, options);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str = simpleDateFormat.format(new Date(jCurrentTimeMillis));
        boolean z = true;
        for (int i = 0; i < zone_ids.length; i++) {
            if (zone_ids[i] != null && !zone_ids[i].equals("")) {
                z = false;
            }
        }
        if (app_id.equals("") || z) {
            u.g.b("AdColony.configure() called with an empty app or zone id String.");
            return false;
        }
        a.n = true;
        if (Build.VERSION.SDK_INT < 14) {
            u.e.b("The minimum API level for the AdColony SDK is 14.");
            a.a(activity, options, true);
        } else {
            a.a(activity, options, false);
        }
        u.f.b("Configure: Total Time (ms): " + (System.currentTimeMillis() - jCurrentTimeMillis) + " and started at " + str);
        return true;
    }

    static boolean a(String[] strArr, String[] strArr2) {
        if (strArr == null || strArr2 == null || strArr.length != strArr2.length) {
            return false;
        }
        Arrays.sort(strArr);
        Arrays.sort(strArr2);
        return Arrays.equals(strArr, strArr2);
    }

    public static AdColonyZone getZone(@NonNull String zone_id) {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.getZone() as AdColony has not yet been configured.");
            return null;
        }
        if (a.m.z.containsKey(zone_id)) {
            return a.m.z.get(zone_id);
        }
        AdColonyZone adColonyZone = new AdColonyZone(zone_id);
        a.m.z.put(zone_id, adColonyZone);
        return adColonyZone;
    }

    public static boolean notifyIAPComplete(@NonNull String product_id, @NonNull String trans_id) {
        return notifyIAPComplete(product_id, trans_id, null, -1.0d);
    }

    public static boolean notifyIAPComplete(@NonNull final String product_id, @NonNull final String trans_id, final String currency_code, @FloatRange(from = 0.0d) final double price) {
        if (!a.n) {
            u.e.b("Ignoring call to notifyIAPComplete as AdColony has not yet been configured.");
            return false;
        }
        if (!au.d(product_id) || !au.d(trans_id)) {
            u.e.b("Ignoring call to notifyIAPComplete as one of the passed Strings is greater than 128 characters.");
            return false;
        }
        if (currency_code != null && currency_code.length() > 3) {
            u.e.b("You are trying to report an IAP event with a currency String containing more than 3 characters.");
        }
        a.execute(new Runnable() { // from class: com.adcolony.sdk.AdColony.1
            @Override // java.lang.Runnable
            public void run() {
                AdColony.a();
                JSONObject jSONObjectA = s.a();
                if (price >= 0.0d) {
                    s.a(jSONObjectA, "price", price);
                }
                if (currency_code != null && currency_code.length() <= 3) {
                    s.a(jSONObjectA, "currency_code", currency_code);
                }
                s.a(jSONObjectA, "product_id", product_id);
                s.a(jSONObjectA, "transaction_id", trans_id);
                new z("AdColony.on_iap_report", 1, jSONObjectA).b();
            }
        });
        return true;
    }

    static boolean a(String str, ay ayVar, int i, int i2) {
        return true;
    }

    public static boolean requestNativeAdView(@NonNull String zone_id, @NonNull AdColonyNativeAdViewListener listener, @NonNull AdColonyAdSize size) {
        return requestNativeAdView(zone_id, listener, size, null);
    }

    public static boolean requestNativeAdView(@NonNull final String zone_id, @NonNull final AdColonyNativeAdViewListener listener, @NonNull final AdColonyAdSize size, final AdColonyAdOptions options) {
        if (!a.n) {
            u.e.b("Ignoring call to requestNativeAdView as AdColony has not yet been configured.");
            a(listener, zone_id);
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("zone_id", zone_id);
        if (af.a(1, bundle)) {
            a(listener, zone_id);
            return false;
        }
        try {
            a.execute(new Runnable() { // from class: com.adcolony.sdk.AdColony.2
                @Override // java.lang.Runnable
                public void run() {
                    if (a.m.E || a.m.F) {
                        AdColony.b();
                        AdColony.a(listener, zone_id);
                    }
                    if (!AdColony.a() && a.l != null) {
                        AdColony.a(listener, zone_id);
                    }
                    if (a.m.z.get(zone_id) == null) {
                        new AdColonyZone(zone_id);
                        u.b.b("Zone info for " + zone_id + " doesn't exist in hashmap");
                    }
                    a.m.g.a(zone_id, listener, size, options);
                }
            });
            return true;
        } catch (RejectedExecutionException e) {
            a(listener, zone_id);
            return false;
        }
    }

    public static boolean setAppOptions(@NonNull AdColonyAppOptions options) {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.setAppOptions() as AdColony has not yet been configured.");
            return false;
        }
        a.m.v = options;
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "options", options.d);
        new z("Options.set_options", 1, jSONObjectA).b();
        return true;
    }

    public static AdColonyAppOptions getAppOptions() {
        if (!a.n || a.m == null || a.m.v == null) {
            return null;
        }
        return a.m.v;
    }

    public static boolean setRewardListener(@NonNull AdColonyRewardListener listener) {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.setRewardListener() as AdColony has not yet been configured.");
            return false;
        }
        a.m.t = listener;
        return true;
    }

    public static boolean removeRewardListener() {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.removeRewardListener() as AdColony has not yet been configured.");
            return false;
        }
        a.m.t = null;
        return true;
    }

    public static String getSDKVersion() {
        if (!a.n) {
            return "";
        }
        j jVar = a.m.m;
        return "3.0.4";
    }

    public static AdColonyRewardListener getRewardListener() {
        if (!a.n || a.m == null) {
            return null;
        }
        return a.m.t;
    }

    public static boolean addCustomMessageListener(@NonNull AdColonyCustomMessageListener listener, final String type) {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.addCustomMessageListener as AdColony has not yet been configured.");
            return false;
        }
        if (!au.d(type)) {
            u.e.b("Ignoring call to AdColony.addCustomMessageListener.");
            return false;
        }
        try {
            a.m.u.put(type, listener);
            a.execute(new Runnable() { // from class: com.adcolony.sdk.AdColony.3
                @Override // java.lang.Runnable
                public void run() {
                    AdColony.a();
                    JSONObject jSONObjectA = s.a();
                    s.a(jSONObjectA, "type", type);
                    new z("CustomMessage.register", 1, jSONObjectA).b();
                }
            });
            return true;
        } catch (RejectedExecutionException e) {
            return false;
        }
    }

    public static AdColonyCustomMessageListener getCustomMessageListener(@NonNull String type) {
        if (!a.n || a.m == null) {
            return null;
        }
        return a.m.u.get(type);
    }

    public static boolean removeCustomMessageListener(@NonNull String type) {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.removeCustomMessageListener as AdColony has not yet been configured.");
            return false;
        }
        a.m.u.remove(type);
        return true;
    }

    public static boolean clearCustomMessageListeners() {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.clearCustomMessageListeners as AdColony has not yet been configured.");
            return false;
        }
        a.m.u.clear();
        return true;
    }

    public static boolean requestInterstitial(@NonNull String zone_id, @NonNull AdColonyInterstitialListener listener) {
        return requestInterstitial(zone_id, listener, null);
    }

    public static boolean requestInterstitial(@NonNull final String zone_id, @NonNull final AdColonyInterstitialListener listener, final AdColonyAdOptions options) {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.requestInterstitial as AdColony has not yet been configured.");
            listener.onRequestNotFilled(new AdColonyZone(zone_id));
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("zone_id", zone_id);
        if (af.a(1, bundle)) {
            AdColonyZone adColonyZone = a.m.z.get(zone_id);
            if (adColonyZone == null) {
                adColonyZone = new AdColonyZone(zone_id);
                u.b.b("Zone info for " + zone_id + " doesn't exist in hashmap");
            }
            listener.onRequestNotFilled(adColonyZone);
            return false;
        }
        try {
            a.execute(new Runnable() { // from class: com.adcolony.sdk.AdColony.4
                @Override // java.lang.Runnable
                public void run() {
                    if (a.m.E || a.m.F) {
                        AdColony.b();
                        AdColony.a(listener, zone_id);
                    }
                    if (!AdColony.a() && a.l != null) {
                        AdColony.a(listener, zone_id);
                    }
                    final AdColonyZone adColonyZone2 = a.m.z.get(zone_id);
                    if (adColonyZone2 == null) {
                        adColonyZone2 = new AdColonyZone(zone_id);
                        u.b.b("Zone info for " + zone_id + " doesn't exist in hashmap");
                    }
                    if (adColonyZone2.getZoneType() != 2) {
                        a.m.g.a(zone_id, listener, options);
                    } else if (a.l != null) {
                        a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.AdColony.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                listener.onRequestNotFilled(adColonyZone2);
                            }
                        });
                    }
                }
            });
            return true;
        } catch (RejectedExecutionException e) {
            a(listener, zone_id);
            return false;
        }
    }

    static boolean a() {
        au.b bVar = new au.b(15.0d);
        while (!a.m.G && !bVar.b()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
            }
        }
        return a.m.G;
    }

    static boolean a(final AdColonyInterstitialListener adColonyInterstitialListener, final String str) {
        if (adColonyInterstitialListener != null && a.l != null) {
            a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.AdColony.5
                @Override // java.lang.Runnable
                public void run() {
                    AdColonyZone adColonyZone = a.m == null ? null : a.m.z.get(str);
                    if (adColonyZone == null) {
                        adColonyZone = new AdColonyZone(str);
                    }
                    adColonyInterstitialListener.onRequestNotFilled(adColonyZone);
                }
            });
            return false;
        }
        return false;
    }

    static boolean a(final AdColonyNativeAdViewListener adColonyNativeAdViewListener, final String str) {
        if (adColonyNativeAdViewListener != null && a.l != null) {
            a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.AdColony.6
                @Override // java.lang.Runnable
                public void run() {
                    AdColonyZone adColonyZone = a.m == null ? null : a.m.z.get(str);
                    if (adColonyZone == null) {
                        adColonyZone = new AdColonyZone(str);
                    }
                    adColonyNativeAdViewListener.onRequestNotFilled(adColonyZone);
                }
            });
            return false;
        }
        return false;
    }

    static void a(Activity activity, AdColonyAppOptions adColonyAppOptions) {
        if (adColonyAppOptions != null && activity != null) {
            String strA = a(activity);
            HashMap map = new HashMap();
            map.put("sessionId", "unknown");
            map.put("packageName", strA);
            map.put("appId", "" + adColonyAppOptions.a);
            map.put("zoneIds", adColonyAppOptions.c.toString());
            map.put("controllerVersion", "unknown");
            map.put("apiLevel", Integer.valueOf(Build.VERSION.SDK_INT));
            map.put("manufacturer", Build.MANUFACTURER);
            map.put("model", Build.MODEL);
            map.put("osVersion", Build.VERSION.RELEASE);
            w.a(map);
        }
    }

    static String a(Activity activity) {
        try {
            return activity.getApplicationContext().getPackageName();
        } catch (Exception e) {
            return "unknown";
        }
    }

    static void b() {
        u.g.b("The AdColony API is not available while AdColony is disabled.");
    }

    static boolean c() {
        if (!a.n) {
            u.e.b("Ignoring call to AdColony.isTablet() as AdColony has not yet been configured.");
            return false;
        }
        if (a.l == null) {
            return false;
        }
        DisplayMetrics displayMetrics = a.l.getResources().getDisplayMetrics();
        float f = displayMetrics.widthPixels / displayMetrics.xdpi;
        float f2 = displayMetrics.heightPixels / displayMetrics.ydpi;
        return Math.sqrt((double) ((f2 * f2) + (f * f))) >= 6.0d;
    }

    static String d() {
        return (a.m == null || a.m.m == null || a.m.m.a == null) ? "" : a.m.m.b();
    }

    static String e() {
        return (a.m == null || a.m.m == null) ? "" : a.m.m.a();
    }
}
