package com.chartboost.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.impl.aa;
import com.chartboost.sdk.impl.af;
import com.chartboost.sdk.impl.ai;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.chartboost.sdk.impl.as;
import com.chartboost.sdk.impl.at;
import com.chartboost.sdk.impl.ay;
import com.chartboost.sdk.impl.y;
import com.fyber.mediation.chartboost.ChartboostMediationAdapter;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Chartboost {
    private Chartboost() {
    }

    public enum CBFramework {
        CBFrameworkUnity("Unity"),
        CBFrameworkCorona("Corona"),
        CBFrameworkAir("AIR"),
        CBFrameworkGameSalad("GameSalad"),
        CBFrameworkCordova("Cordova"),
        CBFrameworkCocoonJS("CocoonJS"),
        CBFrameworkCocos2dx("Cocos2dx"),
        CBFrameworkPrime31Unreal("Prime31Unreal"),
        CBFrameworkWeeby("Weeby"),
        CBFrameworkOther("Other");

        private final String a;

        CBFramework(String s) {
            this.a = s;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.a;
        }

        public boolean doesWrapperUseCustomShouldDisplayBehavior() {
            return this == CBFrameworkAir || this == CBFrameworkCocos2dx;
        }

        public boolean doesWrapperUseCustomBackgroundingBehavior() {
            return this == CBFrameworkAir;
        }
    }

    public enum CBMediation {
        CBMediationAdMarvel("AdMarvel"),
        CBMediationAdMob("AdMob"),
        CBMediationFuse("Fuse"),
        CBMediationFyber("Fyber"),
        CBMediationHeyZap("HeyZap"),
        CBMediationMoPub("MoPub"),
        CBMediationSupersonic("Supersonic"),
        CBMediationHyprMX("HyprMX"),
        CBMediationAerServ("AerServ"),
        CBMediationOther("Other");

        private final String a;

        CBMediation(String s) {
            this.a = s;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.a;
        }
    }

    public static void startWithAppId(final Activity activity, final String appId, final String appSignature) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.1
            @Override // java.lang.Runnable
            public void run() {
                JSONObject jSONObjectA;
                if (h.a() == null) {
                    synchronized (h.class) {
                        if (h.a() == null) {
                            if (activity == null) {
                                CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "Activity object is null. Please pass a valid activity object");
                                return;
                            }
                            if (!b.b(activity)) {
                                CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "Permissions not set correctly");
                                return;
                            }
                            if (!b.c(activity)) {
                                CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "CBImpression Activity not added in your manifest.xml");
                            }
                            if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appSignature)) {
                                CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "AppId or AppSignature is null. Please pass a valid id's");
                                return;
                            }
                            g gVarA = g.a();
                            af afVar = (af) gVarA.a(new at((as) gVarA.a(new as()), 4));
                            try {
                                aa.b();
                                afVar.a();
                                Context applicationContext = activity.getApplicationContext();
                                SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("cbPrefs", 0);
                                try {
                                    jSONObjectA = com.chartboost.sdk.impl.g.a().a(sharedPreferences.getString("config", "{}"));
                                } catch (Exception e) {
                                    CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "Unable to process config");
                                    e.printStackTrace();
                                    jSONObjectA = (JSONObject) gVarA.a(new JSONObject());
                                }
                                AtomicReference atomicReference = new AtomicReference(null);
                                if (!b.a(atomicReference, jSONObjectA, sharedPreferences)) {
                                    atomicReference.set(new com.chartboost.sdk.Model.c((JSONObject) gVarA.a(new JSONObject())));
                                }
                                com.chartboost.sdk.Libraries.g.a(applicationContext, (com.chartboost.sdk.Model.c) atomicReference.get());
                                ai aiVar = (ai) gVarA.a(new ai());
                                final ak akVar = (ak) gVarA.a(new ak(afVar, aiVar, (ay) gVarA.a(new ay(applicationContext, appId)), atomicReference, sharedPreferences));
                                com.chartboost.sdk.Tracking.a aVar = akVar.f;
                                com.chartboost.sdk.impl.b bVar = (com.chartboost.sdk.impl.b) gVarA.a(new com.chartboost.sdk.impl.b(aa.a(), atomicReference, aVar));
                                com.chartboost.sdk.impl.d dVar = (com.chartboost.sdk.impl.d) gVarA.a(new com.chartboost.sdk.impl.d(bVar, afVar, akVar, atomicReference, aVar));
                                final com.chartboost.sdk.impl.e eVar = (com.chartboost.sdk.impl.e) gVarA.a(new com.chartboost.sdk.impl.e(bVar, dVar, akVar, atomicReference));
                                final h hVar = new h(activity, appId, appSignature, bVar, dVar, afVar, eVar, aiVar, akVar, atomicReference, sharedPreferences, aVar);
                                h.a(hVar);
                                akVar.a.a();
                                dVar.c();
                                b.a(hVar, new b.a() { // from class: com.chartboost.sdk.Chartboost.1.1
                                    @Override // com.chartboost.sdk.b.a
                                    public void a() {
                                        aj ajVar = new aj("api/install", akVar);
                                        ajVar.a(com.chartboost.sdk.Libraries.f.a("status", com.chartboost.sdk.Libraries.a.b));
                                        ajVar.j();
                                        eVar.a();
                                        hVar.l = false;
                                    }
                                }, akVar, atomicReference, sharedPreferences);
                            } catch (Throwable th) {
                                CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "Unable to start threads", th);
                            }
                        }
                    }
                }
            }
        });
    }

    public static void onCreate(Activity activity) {
        h hVarA = h.a();
        if (hVarA != null && !hVarA.o()) {
            hVarA.a(activity);
        }
    }

    public static void onStart(Activity activity) {
        h hVarA = h.a();
        if (hVarA != null && !hVarA.o()) {
            hVarA.c(activity);
        }
    }

    public static void onResume(Activity activity) {
        h hVarA = h.a();
        if (hVarA != null && !hVarA.o()) {
            hVarA.e(activity);
        }
    }

    public static void onPause(Activity activity) {
        h hVarA = h.a();
        if (hVarA != null && !hVarA.o()) {
            hVarA.f(activity);
        }
    }

    public static void onStop(Activity activity) {
        h hVarA = h.a();
        if (hVarA != null && !hVarA.o()) {
            hVarA.g(activity);
        }
    }

    public static boolean onBackPressed() {
        h hVarA = h.a();
        if (hVarA == null) {
            return false;
        }
        return hVarA.e();
    }

    public static void onDestroy(Activity activity) {
        h hVarA = h.a();
        if (hVarA != null && !hVarA.o()) {
            hVarA.h(activity);
        }
    }

    public static boolean hasRewardedVideo(String location) {
        h hVarA = h.a();
        if (hVarA == null || !b.a()) {
            return false;
        }
        return hVarA.g.d(location);
    }

    public static void cacheRewardedVideo(final String location) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final com.chartboost.sdk.impl.i iVar = hVarA.g;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (com.chartboost.sdk.impl.g.a().a((CharSequence) location)) {
                            CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "cacheRewardedVideo location cannot be empty");
                            if (i.c != null) {
                                i.c.didFailToLoadRewardedVideo(location, CBError.CBImpressionError.INVALID_LOCATION);
                            }
                        } else {
                            iVar.b(location);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "cacheRewardedVideo Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.B) || (cVar.e && cVar.k)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadRewardedVideo(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    public static void showRewardedVideo(final String location) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final com.chartboost.sdk.impl.i iVar = hVarA.g;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (com.chartboost.sdk.impl.g.a().a((CharSequence) location)) {
                            CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "showRewardedVideo location cannot be empty");
                            if (i.c != null) {
                                i.c.didFailToLoadRewardedVideo(location, CBError.CBImpressionError.INVALID_LOCATION);
                            }
                        } else {
                            iVar.a(location);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "showRewardedVideo Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.B) || (cVar.e && cVar.k)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadRewardedVideo(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    public static boolean hasInterstitial(String location) {
        h hVarA = h.a();
        if (hVarA == null || !b.a()) {
            return false;
        }
        return hVarA.c.d(location);
    }

    public static void cacheInterstitial(final String location) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final com.chartboost.sdk.impl.h hVar = hVarA.c;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.17
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (com.chartboost.sdk.impl.g.a().a((CharSequence) location)) {
                            CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "cacheInterstitial location cannot be empty");
                            if (i.c != null) {
                                i.c.didFailToLoadInterstitial(location, CBError.CBImpressionError.INVALID_LOCATION);
                            }
                        } else {
                            hVar.b(location);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "cacheInterstitial Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.w) || (cVar.e && cVar.g)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadInterstitial(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    public static void showInterstitial(final String location) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final com.chartboost.sdk.impl.h hVar = hVarA.c;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.18
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (com.chartboost.sdk.impl.g.a().a((CharSequence) location)) {
                            CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "showInterstitial location cannot be empty");
                            if (i.c != null) {
                                i.c.didFailToLoadInterstitial(location, CBError.CBImpressionError.INVALID_LOCATION);
                            }
                        } else {
                            hVar.a(location);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "showInterstitial Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.w) || (cVar.e && cVar.g)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadInterstitial(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    @Deprecated
    public static void closeImpression() {
    }

    public static boolean hasMoreApps(String location) {
        h hVarA = h.a();
        if (hVarA == null || !b.a()) {
            return false;
        }
        return hVarA.d.d(location);
    }

    public static void cacheMoreApps(final String location) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final y yVar = hVarA.d;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (com.chartboost.sdk.impl.g.a().a((CharSequence) location)) {
                            CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "cacheMoreApps location cannot be empty");
                            if (i.c != null) {
                                i.c.didFailToLoadMoreApps(location, CBError.CBImpressionError.INVALID_LOCATION);
                            }
                        } else {
                            yVar.b(location);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "cacheMoreApps Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.z) || (cVar.e && cVar.i)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadMoreApps(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    public static void showMoreApps(final String location) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final y yVar = hVarA.d;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.20
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (com.chartboost.sdk.impl.g.a().a((CharSequence) location)) {
                            CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "showMoreApps location cannot be empty");
                            if (i.c != null) {
                                i.c.didFailToLoadMoreApps(location, CBError.CBImpressionError.INVALID_LOCATION);
                            }
                        } else {
                            yVar.a(location);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "showMoreApps Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.z) || (cVar.e && cVar.i)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadMoreApps(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    public static boolean isAnyViewVisible() {
        e eVarM;
        h hVarA = h.a();
        if (hVarA == null || (eVarM = hVarA.m()) == null) {
            return false;
        }
        return eVarM.c();
    }

    public static void setMediation(final CBMediation mediation, final String libraryVersion) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.21
            @Override // java.lang.Runnable
            public void run() {
                try {
                    i.i = mediation;
                    i.j = libraryVersion;
                    i.h = i.i + " " + i.j;
                } catch (Exception e) {
                    com.chartboost.sdk.Tracking.a.a(Chartboost.class, "setMediation Runnable.run", e);
                }
            }
        });
    }

    public static void setFramework(final CBFramework framework, final String version) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.22
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (framework == null) {
                        CBLogging.b(ChartboostMediationAdapter.ADAPTER_NAME, "Pass a valid CBFramework enum value");
                    } else {
                        i.d = framework;
                        i.e = version;
                        i.f = String.format("%s %s", framework, version);
                    }
                } catch (Exception e) {
                    com.chartboost.sdk.Tracking.a.a(Chartboost.class, "setFramework Runnable.run", e);
                }
            }
        });
    }

    @Deprecated
    public static void setFrameworkVersion(final String version) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    b.a(version);
                } catch (Exception e) {
                    com.chartboost.sdk.Tracking.a.a(Chartboost.class, "setFrameworkVersion Runnable.run", e);
                }
            }
        });
    }

    public static void setChartboostWrapperVersion(final String version) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    b.a(version);
                } catch (Exception e) {
                    com.chartboost.sdk.Tracking.a.a(Chartboost.class, "setChartboostWrapperVersion Runnable.run", e);
                }
            }
        });
    }

    public static String getCustomId() {
        return !b.c() ? "" : i.a;
    }

    public static void setCustomId(final String customID) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.4
            @Override // java.lang.Runnable
            public void run() {
                i.a = customID;
            }
        });
    }

    public static void setLoggingLevel(final CBLogging.Level lvl) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.5
            @Override // java.lang.Runnable
            public void run() {
                if (b.c()) {
                    CBLogging.a = lvl;
                }
            }
        });
    }

    public static CBLogging.Level getLoggingLevel() {
        b.c();
        return CBLogging.a;
    }

    public static a getDelegate() {
        return i.c;
    }

    public static void setDelegate(final ChartboostDelegate delegate) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.6
            @Override // java.lang.Runnable
            public void run() {
                i.c = delegate;
            }
        });
    }

    public static boolean getAutoCacheAds() {
        return i.s;
    }

    public static void setAutoCacheAds(final boolean autoCacheAds) {
        if (h.a() != null) {
            h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.7
                @Override // java.lang.Runnable
                public void run() {
                    i.s = autoCacheAds;
                }
            });
        }
    }

    public static void setShouldRequestInterstitialsInFirstSession(final boolean shouldRequest) {
        if (b.c()) {
            h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.8
                @Override // java.lang.Runnable
                public void run() {
                    i.t = shouldRequest;
                }
            });
        }
    }

    public static void setShouldDisplayLoadingViewForMoreApps(final boolean shouldDisplay) {
        if (b.c()) {
            h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.9
                @Override // java.lang.Runnable
                public void run() {
                    i.u = shouldDisplay;
                }
            });
        }
    }

    public static void setShouldPrefetchVideoContent(final boolean shouldPrefetch) {
        h hVarA = h.a();
        if (hVarA != null && b.a()) {
            final com.chartboost.sdk.impl.e eVar = hVarA.e;
            h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        i.v = shouldPrefetch;
                        if (shouldPrefetch && h.n()) {
                            eVar.a();
                        } else {
                            eVar.b();
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "setShouldPrefetchVideoContent Runnable.run", e);
                    }
                }
            });
        }
    }

    public static String getSDKVersion() {
        return "6.6.0";
    }

    public static void setShouldHideSystemUI(Boolean hide) {
        i.g = hide.booleanValue();
    }

    public static boolean isWebViewEnabled() {
        h hVarA = h.a();
        return hVarA == null || hVarA.h.get().u;
    }

    @TargetApi(14)
    public static void setActivityCallbacks(boolean enabled) {
        Activity activityJ;
        Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
        h hVarA = h.a();
        if (hVarA != null && (activityJ = hVarA.j()) != null && (activityLifecycleCallbacks = hVarA.n) != null) {
            if (!hVarA.m && enabled) {
                activityJ.getApplication().registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
                hVarA.m = true;
            } else if (hVarA.m && !enabled) {
                activityJ.getApplication().unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
                hVarA.m = false;
            }
        }
    }

    private static void showInterstitialAIR(final String location, final boolean show) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final com.chartboost.sdk.impl.h hVar = hVarA.c;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        hVar.b(location, show);
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "showInterstitialAIR Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.w) || (cVar.e && cVar.g)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadInterstitial(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    private static void showMoreAppsAIR(final String location, final boolean show) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final y yVar = hVarA.d;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        yVar.b(location, show);
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "showMoreAppsAIR Runnable.run", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.z) || (cVar.e && cVar.i)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadMoreApps(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    private static void showRewardedVideoAIR(final String location, final boolean show) {
        h hVarA = h.a();
        if (hVarA != null && b.a() && h.n()) {
            final com.chartboost.sdk.impl.i iVar = hVarA.g;
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.Chartboost.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iVar.b(location, show);
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "showRewardedVideoAIR", e);
                    }
                }
            };
            com.chartboost.sdk.Model.c cVar = hVarA.h.get();
            if ((cVar.u && cVar.B) || (cVar.e && cVar.k)) {
                h.a(runnable);
            } else {
                i.c.didFailToLoadRewardedVideo(location, CBError.CBImpressionError.END_POINT_DISABLED);
            }
        }
    }

    private static void forwardTouchEventsAIR(final boolean forward) {
        final h hVarA = h.a();
        if (hVarA != null) {
            h.a(new Runnable() { // from class: com.chartboost.sdk.Chartboost.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (hVarA.i != null) {
                            if (forward) {
                                hVarA.i.forwardTouchEvents(hVarA.j());
                            } else {
                                hVarA.i.forwardTouchEvents(null);
                            }
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(Chartboost.class, "forwardTouchEventsAIR", e);
                    }
                }
            });
        }
    }
}
